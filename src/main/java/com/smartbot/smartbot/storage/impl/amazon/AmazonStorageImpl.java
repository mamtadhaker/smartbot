package com.smartbot.smartbot.storage.impl.amazon;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.smartbot.smartbot.exception.SmartbotRunTimeException;
import com.smartbot.smartbot.storage.Storage;
import com.smartbot.smartbot.storage.StorageException;
import com.smartbot.smartbot.storage.StorageRequest;
import com.smartbot.smartbot.storage.StorageResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.io.Files;

public class AmazonStorageImpl implements Storage {

  private static final Logger LOGGER = LoggerFactory.getLogger(AmazonStorageImpl.class);

  private String s3BucketName;
  private String s3AccessId;
  private String s3AccessKey;

  public AmazonStorageImpl(String s3accessId, String s3AccessKey, String bucketName) {
    this.s3AccessId = s3accessId;
    this.s3AccessKey = s3AccessKey;
    this.s3BucketName = bucketName;
  }

  @Override
  public StorageResponse storeObject(StorageRequest request) {
    if (null != request.getFile()) {
      return storeFileObject(request);
    } else if (null != request.getMultipartFile()) {
      return storeMultipartObject(request);
    } else
      return null;
  }

  private StorageResponse storeMultipartObject(StorageRequest request) {
    try {
      return storeObject(request.getMultipartFile().getBytes(), request.getKey());
    } catch (IOException e) {
      LOGGER.error("Failed to process Cloud Storage Request on Amazon cloud.");
      e.printStackTrace();
      throw new StorageException("Failed to process Storage Request on Amazon cloud");
    }
  }

  private StorageResponse storeFileObject(StorageRequest request) {

    try {
      return storeObject(Files.toByteArray(request.getFile()), request.getKey());
    } catch (IOException e) {
      LOGGER.error("Failed to process Cloud Storage Request on Amazon cloud.");
      e.printStackTrace();
      throw new StorageException("Failed to process Cloud Storage Request on Amazon Cloud");
    }
  }

  public StorageResponse storeObject(byte[] content, String key) {
    LOGGER.debug("Getting Amazon s3 Client");
    StorageResponse response = null;
    AmazonS3 s3Client = getS3Client();
    ObjectMetadata fileMetadata = new ObjectMetadata();

    try {

      fileMetadata.setContentLength(content.length);
      InputStream fileInputStream = new ByteArrayInputStream(content);

      //Storing the file to CDN
      LOGGER.debug("Uploading a new object to amazon s3 with key :{} ", key);

      PutObjectRequest putRequest = new PutObjectRequest(s3BucketName, key, fileInputStream, fileMetadata);

      //Creating header for server side encryption
      ObjectMetadata objectMetadata = new ObjectMetadata();
      objectMetadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);
      //Adding header to put request
      putRequest.setMetadata(objectMetadata);
      PutObjectResult result = s3Client.putObject(putRequest);

      if (result != null) {
        response = new StorageResponse(key);

      }

      LOGGER.debug("Uploaded object to Amazon s3 for key : {} to bucket {} ", key, s3BucketName);
      return response;
    } catch (AmazonClientException ase) {
      LOGGER.error("Error while uploading object to Amazon s3 : {} ", ase.getMessage());
      throw new StorageException("Error while uploading object to Amazon S3 : " + ase.getMessage());
    }
  }

  @Override
  public void deleteObject(StorageRequest request) throws StorageException {
    try {
      AmazonS3 s3Client = getS3Client();
      LOGGER.debug("Deleting file from amazon with key : {}", request.getKey());
      s3Client.deleteObject(s3BucketName, request.getKey());
      LOGGER.debug("Deleted file from amazon with key : {}", request.getKey());
    } catch (Exception e) {
      e.printStackTrace();
      throw new StorageException("Failed to delete file with key" + request.getKey());
    }
  }

  //Prepares s3 client object
  private AmazonS3 getS3Client() {
    LOGGER.debug("Creating Amazon S3 Client");
    AWSCredentials credentials = new BasicAWSCredentials(s3AccessId, s3AccessKey);
    return new AmazonS3Client(credentials);
  }

  @Override
  public List<String> getObjects() {
    try {
      AmazonS3 s3Client = getS3Client();

      ObjectListing listing = s3Client.listObjects(s3BucketName);
      List<String> objectList = new ArrayList<String>(listing.getObjectSummaries().size());
      for (S3ObjectSummary object : listing.getObjectSummaries()) {
        objectList.add(object.getKey());
      }
      return objectList;
    } catch (Exception e) {
      e.printStackTrace();
      throw new SmartbotRunTimeException("Failed to get objects from bucket " + s3BucketName);
    }
  }

}