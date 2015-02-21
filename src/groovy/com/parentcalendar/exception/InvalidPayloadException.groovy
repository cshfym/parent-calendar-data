package com.parentcalendar.exception

class InvalidPayloadException {

  String error

  public InvalidPayloadException(String message) {
    this.error = message
  }

  def getError() {
    error
  }
}
