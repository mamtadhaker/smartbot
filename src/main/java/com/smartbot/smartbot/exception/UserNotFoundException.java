package com.smartbot.smartbot.exception;

public class UserNotFoundException extends SmartbotRunTimeException {

  private static final long serialVersionUID = 1L;
  private String field;

  public UserNotFoundException(ErrorCode code, String message) {
    super(code, message);
  }

  public UserNotFoundException(ErrorCode code, String message, String field) {
    super(code, message);
    this.field = field;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }
}