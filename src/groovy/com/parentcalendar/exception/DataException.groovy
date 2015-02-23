package com.parentcalendar.exception


class DataException extends Throwable {

  String message

  public DataException(String message) {
    this.message = message
  }

  @Override
  String getMessage() {
    message
  }
}
