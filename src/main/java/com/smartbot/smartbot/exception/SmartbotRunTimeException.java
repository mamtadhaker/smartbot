package com.smartbot.smartbot.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmartbotRunTimeException extends RuntimeException {

  private static final Logger logger = LoggerFactory.getLogger(SmartbotRunTimeException.class);

  private ErrorCode errorCode;

  private String keyCode;

  public SmartbotRunTimeException(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }

  public SmartbotRunTimeException(String keyCode) {
    this.keyCode = keyCode;
  }

  public SmartbotRunTimeException(ErrorCode errorCode, String message, Throwable cause) {
    super(message, cause);
    this.errorCode = errorCode;
  }

  public SmartbotRunTimeException(ErrorCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public SmartbotRunTimeException(ErrorCode errorCode, Throwable cause) {
    super(cause);
    this.errorCode = errorCode;
  }

  protected SmartbotRunTimeException(ErrorCode errorCode, String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }

  public String getKeyCode() {
    return keyCode;
  }
}