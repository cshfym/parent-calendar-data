package com.parentcalendar.exception


class DataException {

  String message

  public DataException(String message) {
    this.message = message
  }

  def getMessage() {
    message
  }
}
