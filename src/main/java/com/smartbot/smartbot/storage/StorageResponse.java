package com.smartbot.smartbot.storage;

public class StorageResponse {

  private String key;
  private String url;

  public StorageResponse() {
  }

  public StorageResponse(String key) {
    this.key = key;
    //this.url = url;
  }

  public String getKey() {
    return this.key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

}