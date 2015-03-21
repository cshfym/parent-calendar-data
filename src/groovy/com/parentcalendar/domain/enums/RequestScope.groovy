package com.parentcalendar.domain.enums


public enum RequestScope {

  GLOBAL(1),
  USER(2)

  int value

  public RequestScope(int value) {
    this.value = value
  }
}