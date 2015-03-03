package com.parentcalendar.exception


class TokenExpirationException {

  String message

  public TokenExpirationException(String message) {
    this.message = message
  }

  String getMessage() {
    message
  }
}
