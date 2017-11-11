package com.smartbot.smartbot.recognition;

import org.springframework.stereotype.Service;

@Service
public interface FaceRecognitionService {

  /**Search image and return list of matched user Ids with match percentage
   * @param request
   * @return FaceRecognitionResponse 
   */
  public FaceRecognitionResponse searchImage(FaceRecognitionRequest request);

  /**Save image in the image collection mentioned in the request
   * @param request
   */
  public void saveImage(FaceStoreRequest request);

  /**Create collection or refresh (delete and create a new one if alreadt exists)
   * 
   */
  public void refreshCollection();

}