package com.parentcalendar.domain.enums


public enum RequestScope {

  SCOPE_GLOBAL(1),
  SCOPE_REQUESTOR(2),
  SCOPE_USER(3)

  int value

  public RequestScope(int value) {
    this.value = value
  }
}