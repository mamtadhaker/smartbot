package com.smartbot.smartbot.exception;

public enum ErrorCode {

  UndefinedError(EndUserMessages.Something_Unexpected_Happened, "", ErrorType.business), INVALID_REQUEST(
      EndUserMessages.INVALID_REQUEST, "", ErrorType.INVALID_REQUEST), INTERNAL_SERVER_ERROR(
          EndUserMessages.INTERNAL_SERVER_ERROR, "", ErrorType.INTERNAL_SERVER_ERROR), USER_NOT_FOUND(
              EndUserMessages.USER_NOT_FOUND, "", ErrorType.USER_NOT_FOUND);
  private String endUserMessage;
  private String loggedMessage;
  private ErrorType errorType;

  private ErrorCode(String endUserMessage, String loggedMessage, ErrorType errorType) {
    this.endUserMessage = endUserMessage;
    this.loggedMessage = loggedMessage;
    this.errorType = errorType;
  }

  private ErrorCode(String endUserMessage, ErrorType errorType) {
    this.endUserMessage = endUserMessage;
    this.errorType = errorType;
  }

  private static class EndUserMessages {

    static {
      Something_Unexpected_Happened = "Something unexpected happened on the server. Please try again.";
      INVALID_REQUEST = "Invalid Request.";
      INTERNAL_SERVER_ERROR = "Something unexpected happened on the server";
      USER_NOT_FOUND = "User not found";
    }

    private static final String Something_Unexpected_Happened;
    private static final String INVALID_REQUEST;
    private static final String INTERNAL_SERVER_ERROR;
    private static final String USER_NOT_FOUND;
  }

  interface LoggedMessages {
    public static final String UrlNotFound = "User has entered invalid url.";
    public static final String UnAuthorisedAccess = "User tried to access the unauthorised resource.";
    public static final String UndefinedError = "Undefine error occurred.";
  }

  public ErrorType getErrorType() {
    return errorType;
  }

  public String getEndUserMessage() {
    return endUserMessage;
  }

  public String getLoggedMessage() {
    return loggedMessage;
  }

}