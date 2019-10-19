package com.prototype.cellusage.exception;

public class UsageReportException extends RuntimeException {
  public UsageReportException(String message) {
    super(message);
  }

  public UsageReportException(String message, Exception exception) {
    super(message, exception);
  }
}
