package com.smartbot.smartbot.recognition;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

public class FaceRecognitionResponse {

  @Value("false")
  private boolean isMatchFound;

  private Map<String, Double> recognisedUsersMap;

  public Map<String, Double> getRecognisedUsersMap() {
    return recognisedUsersMap;
  }

  public void setRecognisedUsersMap(Map<String, Double> recognisedUserMap) {
    this.recognisedUsersMap = recognisedUserMap;
    if (!(null == recognisedUserMap) && recognisedUserMap.size() > 0) {
      isMatchFound = true;
    }
  }

  public FaceRecognitionResponse(Map<String, Double> recognisedUserMap) {
    setRecognisedUsersMap(recognisedUserMap);
  }
}