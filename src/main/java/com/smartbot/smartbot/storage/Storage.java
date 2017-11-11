package com.smartbot.smartbot.storage;

import java.util.List;

public interface Storage {

  //Store object on specified vendor
  public StorageResponse storeObject(StorageRequest requestObject) throws StorageException;

  //Delete object from specified vendor
  public void deleteObject(StorageRequest requestObject) throws StorageException;

  public List<String> getObjects();
}