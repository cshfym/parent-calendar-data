package com.parentcalendar.domain.enums


public enum RequestScope {

  SCOPE_GLOBAL(1),
  SCOPE_USER(2)

  int value

  public RequestScope(int value) {
    this.value = value
  }
}