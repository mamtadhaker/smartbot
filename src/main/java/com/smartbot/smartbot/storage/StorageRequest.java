package com.smartbot.smartbot.storage;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public class StorageRequest {

  private String key;
  private MultipartFile multipartFile;
  private File file;

  public StorageRequest(String key, File file) {
    this.setKey(key);
    this.setFile(file);
  }

  public StorageRequest(String key, MultipartFile multipartFile) {
    this.key = key;
    this.multipartFile = multipartFile;
  }

  public String getKey() {
    return this.key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setMultipartFile(MultipartFile multipartFile) {
    this.multipartFile = multipartFile;
  }

  public MultipartFile getMultipartFile() {
    return multipartFile;
  }

  public File getFile() {
    return file;
  }

  private void setFile(File file) {
    this.file = file;
  }
}