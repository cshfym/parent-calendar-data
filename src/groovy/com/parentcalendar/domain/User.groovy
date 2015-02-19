package com.parentcalendar.domain


class User extends Persistable {

  Boolean active
  String email

  @Override
  public String toString() {
    "User [ id: $id, active: $active, email: $email ]"
  }
}
