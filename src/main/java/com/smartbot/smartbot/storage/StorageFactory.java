package com.smartbot.smartbot.storage;

import com.smartbot.smartbot.storage.impl.amazon.AmazonStorageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Factory to create Storage instance based on vendor
public class StorageFactory {
  private static final Logger LOGGER = LoggerFactory.getLogger(StorageFactory.class);

  private static StorageFactory instance = null;

  private StorageFactory() {

  }

  //get the only object available
  public static StorageFactory getInstance() {
    LOGGER.info("Getting Storage factory instance");
    if (instance == null) {
      LOGGER.debug("Storage factory instance not found");
      synchronized (StorageFactory.class) {
        if (instance == null) {
          LOGGER.debug("Creating storage factory instance");
          instance = new StorageFactory();
        }
      }
    }
    return instance;
  }

  //Get object of CloudStorage of respective Cloud Vendor
  public Storage getStorage(StorageVendor vendor, String username, String password, String folder) {
    if (vendor == StorageVendor.AMAZON) {
      LOGGER.debug("Creating Amazon Storage instance");
      return new AmazonStorageImpl(username, password, folder);
    }
    return null;
  }
}