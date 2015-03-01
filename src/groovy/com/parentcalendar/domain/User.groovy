package com.parentcalendar.domain

import com.parentcalendar.domain.common.Persistable

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
  @Column (name = "id")
  Long id

  @Column (name = "version")
  Long version

  @Column (name = "account_expired")
  Boolean expired

  @Column (name = "account_locked")
  Boolean locked

  @Column (name = "password_expired")
  Boolean passwordExpired

  @Column (name = "enabled")
  Boolean enabled

  @Column (name = "email")
  String email

  @Column (name = "username")
  String username

  @Column (name = "password")
  String password

  @Override
  public String toString() {
    "User [ id: $id, enabled: $enabled, email: $email ]"
  }
}
