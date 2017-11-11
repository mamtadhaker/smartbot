package com.smartbot.smartbot.recognition;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.CreateCollectionRequest;
import com.amazonaws.services.rekognition.model.CreateCollectionResult;
import com.amazonaws.services.rekognition.model.DeleteCollectionRequest;
import com.amazonaws.services.rekognition.model.DeleteCollectionResult;
import com.amazonaws.services.rekognition.model.FaceMatch;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.IndexFacesRequest;
import com.amazonaws.services.rekognition.model.IndexFacesResult;
import com.amazonaws.services.rekognition.model.ListCollectionsRequest;
import com.amazonaws.services.rekognition.model.ListCollectionsResult;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.rekognition.model.SearchFacesByImageRequest;
import com.amazonaws.services.rekognition.model.SearchFacesByImageResult;
import com.smartbot.smartbot.exception.ErrorCode;
import com.smartbot.smartbot.exception.SmartbotRunTimeException;
import com.smartbot.smartbot.storage.StorageRequest;
import com.smartbot.smartbot.storage.StorageService;
import com.smartbot.smartbot.utils.Constants;

@Service
public class FaceRecognitionServiceAmazonImpl implements FaceRecognitionService {

  @Value("${aws.s3.url}")
  private String s3Url;

  @Value("${aws.s3.bucket.name}")
  private String bucketName;

  @Value("${aws.accessKeyId}")
  private String rekognitionAccessId;

  @Value("${aws.accessKeySecret}")
  private String rekognitionAccessKey;

  @Value("${aws.s3.bucket.users}")
  private String rekognitionCollectionId;

  @Value("${aws.rekognition.url}")
  private String rekognitionEndpoint;

  @Value("${aws.rekognition.availability.zone}")
  private String rekognitionAvailablityZone;

  @Value("${aws.rekognition.match.limit}")
  private int numberOfMatches;

  @Value("${aws.rekognition.confidence.min}")
  private float minMatchConfidence;

  @Autowired
  private StorageService storageService;

  Logger logger = LoggerFactory.getLogger(FaceRecognitionServiceAmazonImpl.class);

  @Override
  public FaceRecognitionResponse searchImage(FaceRecognitionRequest request) {
    logger.debug("searching image {} in collection", request.getImage().getName());
    Image image = null;
    try {
      //convert image to recognition image format
      if (null != request.getImage() && null != request.getImage().getBytes()) {
        image = new Image().withBytes(ByteBuffer.wrap(request.getImage().getBytes()));
      }
      //empty image found. cannot proceed further
      else
        throw new IOException("Empty image " + request.getImage().getName());
    } catch (IOException e) {
      logger.error("exception occured while parsing image{}", request.getImage().getName());
      throw new SmartbotRunTimeException(ErrorCode.INVALID_REQUEST, e.getMessage());
    }

    //get amazon rekognition
    AmazonRekognition amazonRecognition = getAmazonRecognitionClient();

    //check if collection exists
    //if it does not, create collection and all faces from bucket to it
    getRekognitionCollection(amazonRecognition);

    //5. Search collection for faces similar to the larget face in the image.
    SearchFacesByImageResult searchFacesByImageResult = searchFacesByImage(rekognitionCollectionId, image,
        minMatchConfidence, numberOfMatches, amazonRecognition);

    //create and return response
    Map<String, Double> recognisedUsersMap = null;

    if (null != searchFacesByImageResult.getFaceMatches() && searchFacesByImageResult.getFaceMatches().size() > 0) {

      recognisedUsersMap = new LinkedHashMap<String, Double>();

      logger.info(searchFacesByImageResult.getFaceMatches().size() + " matches found");
      for (FaceMatch faceMatch : searchFacesByImageResult.getFaceMatches()) {
        recognisedUsersMap.put(faceMatch.getFace().getExternalImageId(),
            faceMatch.getFace().getConfidence().doubleValue());
      }
    }
    return new FaceRecognitionResponse(recognisedUsersMap);
  }

  private SearchFacesByImageResult searchFacesByImage(String collectionId, Image image, float threshold, int maxFaces,
      AmazonRekognition amazonRecognition) {
    SearchFacesByImageRequest searchFacesByImageRequest = new SearchFacesByImageRequest().withCollectionId(collectionId)
        .withImage(image).withFaceMatchThreshold(threshold).withMaxFaces(maxFaces);
    return amazonRecognition.searchFacesByImage(searchFacesByImageRequest);
  }

  /**Save image in the image collection mentioned in the request
   * @param request
   */
  @Override
  public void saveImage(FaceStoreRequest request) {
    logger.debug("Storing image to S3 bucket");
    storageService.store(new StorageRequest(request.getFileName(), request.getImage()));

    logger.debug("Storing image to rekognition collection");
    AmazonRekognition amazonRekognition = getAmazonRecognitionClient();
    indexFaces(rekognitionCollectionId, amazonRekognition, request.getFileName(), bucketName);

    logger.info("Image indexed successfully");
  }

  private AmazonRekognitionClient getAmazonRecognitionClient() {
    AWSCredentials credentials;
    try {
      credentials = new AWSCredentials() {

        @Override
        public String getAWSSecretKey() {
          return rekognitionAccessKey;
        }

        @Override
        public String getAWSAccessKeyId() {
          return rekognitionAccessId;
        }
      };
    } catch (Exception e) {
      throw new AmazonClientException("Unable to connect to Amazon", e);
    }

    AmazonRekognitionClient amazonRecognition = new AmazonRekognitionClient(credentials);
    amazonRecognition.setSignerRegionOverride(rekognitionAvailablityZone);
    amazonRecognition.setEndpoint(rekognitionEndpoint);

    return amazonRecognition;

  }

  private void getRekognitionCollection(AmazonRekognition amazonRekognition) {
    //check if collection exists
    List<String> collections = listRekognitionCollections(amazonRekognition);

    if (!collections.contains(rekognitionCollectionId)) {

      logger.debug("Collection {}, not found, creating new one", rekognitionCollectionId);
      //create collection
      createRekognitionCollection(rekognitionCollectionId, amazonRekognition);

      //get all images from bucket
      logger.debug("Getting list of objects to add to collection");
      List<String> facesList = storageService.getObjects();

      // add images to collection
      for (String face : facesList) {
        indexFaces(rekognitionCollectionId, amazonRekognition, face, bucketName);

      }
      logger.debug("New Collection created");
    }
  }

  private IndexFacesResult indexFaces(String collectionId, AmazonRekognition amazonRekognition, String name,
      String bucketName) {
    //When indexing images, make external Id as the string before "_" sign
    //This should be the uuid of the user whose image is being indexed
    IndexFacesRequest req = new IndexFacesRequest().withImage(getImageUtil(bucketName, name))
        .withCollectionId(collectionId)
        .withExternalImageId(String.valueOf(name.subSequence(0, name.indexOf(Constants.UNDERSCORE_SEPERATOR))));

    return amazonRekognition.indexFaces(req);
  }

  private Image getImageUtil(String bucket, String key) {
    return new Image().withS3Object(new S3Object().withBucket(bucket).withName(key));
  }

  private CreateCollectionResult createRekognitionCollection(String collectionId, AmazonRekognition amazonRekognition) {
    //Create a new rekognition collection
    CreateCollectionRequest request = new CreateCollectionRequest().withCollectionId(collectionId);
    return amazonRekognition.createCollection(request);
  }

  private List<String> listRekognitionCollections(AmazonRekognition amazonRekognition) {
    // List all available collection
    ListCollectionsResult collectionsRequest = amazonRekognition.listCollections(new ListCollectionsRequest());
    return collectionsRequest.getCollectionIds();
  }

  /**Create collection or refresh (delete and create a new one if alreadt exists)
   * 
   */
  @Override
  public void refreshCollection() {

    logger.info("Refreshing rekognition collection");
    try {
      //get amazon rekognition
      logger.debug("Getting list of objects to add to collection");
      AmazonRekognition amazonRekognition = getAmazonRecognitionClient();

      //check if collection exists
      List<String> collections = listRekognitionCollections(amazonRekognition);

      //If collection exists, delete it
      if (collections.contains(rekognitionCollectionId)) {
        //delete collection if already exists
        logger.debug("Collection exists, deleting it");
        deleteCollection(rekognitionCollectionId, amazonRekognition);
      }

      //Create new collection
      logger.debug("Creating collection {}", rekognitionCollectionId);
      createRekognitionCollection(rekognitionCollectionId, amazonRekognition);

      //get all images from bucket
      logger.debug("Getting list of objects to add to collection");
      List<String> facesList = storageService.getObjects();

      //add images to collection
      logger.debug("Adding faces to collection");
      for (String face : facesList) {
        indexFaces(rekognitionCollectionId, amazonRekognition, face, bucketName);
      }
      logger.info("New Collection created");
    } catch (Exception e) {
      e.printStackTrace();
      throw new SmartbotRunTimeException(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
    }

  }

  private static DeleteCollectionResult deleteCollection(String collectionId, AmazonRekognition amazonRekognition) {
    DeleteCollectionRequest request = new DeleteCollectionRequest().withCollectionId(collectionId);
    return amazonRekognition.deleteCollection(request);
  }
}