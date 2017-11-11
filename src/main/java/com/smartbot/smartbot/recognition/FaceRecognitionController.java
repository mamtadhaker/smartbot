package com.smartbot.smartbot.recognition;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.smartbot.smartbot.user.UserController;
import com.smartbot.smartbot.utils.Utils;

@RestController
@Validated
public class FaceRecognitionController {

  @Autowired
  private FaceRecognitionService faceRecognitionService;

  private final static Logger logger = LoggerFactory.getLogger(UserController.class);

  @RequestMapping(value = "/facerecognition/refresh", method = RequestMethod.POST)
  Map<String, Object> verifyUser() {

    logger.info("Request received to refresh face recognition collection");
    faceRecognitionService.refreshCollection();
    logger.info("Refresh face recognition completed");
    return Utils.createBooleanResponseObject(true);

  }
}