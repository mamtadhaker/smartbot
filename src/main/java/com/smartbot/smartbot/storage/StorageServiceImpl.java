package com.smartbot.smartbot.storage;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.smartbot.smartbot.utils.Constants;

@Service
public class StorageServiceImpl implements StorageService {
  private static final Logger LOGGER = LoggerFactory.getLogger(StorageServiceImpl.class);

  private StorageFactory getStorageFactory() {
    return StorageFactory.getInstance();
  }

  @Value("${aws.accessKeyId}")
  private String s3AccessId;

  @Value("${aws.accessKeySecret}")
  private String s3AccessKey;

  @Value("${aws.s3.bucket.name}")
  private String bucketName;

  @Override
  public StorageResponse store(StorageRequest request) {
    LOGGER.debug("Getting Storage instance for vendor : {} ", Constants.DEFAULT_CLOUD_STORAGE_TYPE);
    Storage storage = getStorageFactory().getStorage(StorageVendor.valueOf(Constants.DEFAULT_CLOUD_STORAGE_TYPE),
        s3AccessId, s3AccessKey, bucketName);

    return storage.storeObject(request);
  }

  @Override
  public void delete(StorageRequest request) {
    LOGGER.debug("Getting Storage instance for vendor : {} ", Constants.DEFAULT_CLOUD_STORAGE_TYPE);
    Storage storage = getStorageFactory().getStorage(StorageVendor.valueOf(Constants.DEFAULT_CLOUD_STORAGE_TYPE),
        s3AccessId, s3AccessKey, bucketName);
    storage.deleteObject(request);

  }

  @Override
  public List<String> getObjects() {
    Storage storage = getStorageFactory().getStorage(StorageVendor.valueOf(Constants.DEFAULT_CLOUD_STORAGE_TYPE),
        s3AccessId, s3AccessKey, bucketName);

    return storage.getObjects();
  }

}