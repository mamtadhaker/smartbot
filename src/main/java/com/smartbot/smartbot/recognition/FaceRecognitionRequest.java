package com.smartbot.smartbot.recognition;

import org.springframework.web.multipart.MultipartFile;

public class FaceRecognitionRequest {

  private MultipartFile image;

  private String collectionId;

  public MultipartFile getImage() {
    return image;
  }

  public void setImage(MultipartFile image) {
    this.image = image;
  }

  public String getCollectionId() {
    return collectionId;
  }

  public void setCollectionId(String collectionId) {
    this.collectionId = collectionId;
  }

  public FaceRecognitionRequest(MultipartFile image, String collectionId) {
    this.image = image;
    this.collectionId = collectionId;
  }
}