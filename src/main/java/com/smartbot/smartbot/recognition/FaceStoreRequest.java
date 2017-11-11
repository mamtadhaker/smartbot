package com.smartbot.smartbot.recognition;

import org.springframework.web.multipart.MultipartFile;

//Store a new face in collection
public class FaceStoreRequest {

  private MultipartFile image;

  private String filename;

  public MultipartFile getImage() {
    return image;
  }

  public void setImage(MultipartFile image) {
    this.image = image;
  }

  public String getFileName() {
    return filename;
  }

  public void setFileName(String filename) {
    this.filename = filename;
  }

  public FaceStoreRequest(MultipartFile image, String collectionId, String filename) {
    this.image = image;
    this.filename = filename;
  }
}