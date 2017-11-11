package com.smartbot.smartbot.storage;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface StorageService {

  //Store file object on local/cloud storage
  public StorageResponse store(StorageRequest requestObject);

  //delete file object from local/cloud storage
  public void delete(StorageRequest requestObject);

  //get all items
  public List<String> getObjects();

}