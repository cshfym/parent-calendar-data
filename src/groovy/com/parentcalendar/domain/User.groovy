package com.parentcalendar.domain

import javax.persistence.Entity
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table


@Entity
@Table(name = "user")
class User extends Persistable {

  @Id
  @GeneratedValue
  @Column(name = "id")
  Long id

  @Column(name = "active")
  Boolean active

  @Column(name = "email")
  String email

  @Override
  public String toString() {
    "User [ id: $id, active: $active, email: $email ]"
  }
}
