package com.smartbot.smartbot.utils;

import java.util.HashMap;
import java.util.Map;

public class Utils {

  public static Map<String, Object> createBooleanResponseObject(boolean value) {
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("Success", value);
    return result;
  }
}