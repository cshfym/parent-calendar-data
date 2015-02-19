package com.parentcalendar.controller

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.parentcalendar.domain.User
import com.parentcalendar.exception.DataException
import com.parentcalendar.exception.InvalidPayloadException
import com.parentcalendar.services.db.UserDataService
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired

class UserController {

  private static final log = LogFactory.getLog(this)


  // TODO: Externalize parent-calendar-data security token.

  @Autowired
  UserDataService service

  @Autowired
  Gson gson

  // Path: /user [GET]
  def findAll() {

    List<User> list = []

    try {
      list = service.findAll(User.class)
    } catch (Exception ex) {
      response.setStatus(500)
      def msg = "Could not execute findAll query: " + ex.getCause()
      log.error msg, ex
      render gson.toJson(new DataException(msg))
      return
    }

    response.setStatus(200)
    render gson.toJson(list)
  }

  // Path: /user/{id} [GET]
  def show(Long id) {

    if (null == id) {
      response.setStatus(404)
      return
    }

    User data = service.find(User.class, id)
    if (null == data) {
      response.setStatus(404)
      return
    }

    response.setStatus(200)
    render gson.toJson(data)
  }

  // Path: /user [PUT]
  def create(String payload) {

    User data
    try {
      data = gson.fromJson(payload, User.class)
    } catch (JsonSyntaxException ex) {
      response.setStatus(500)
      render gson.toJson(new InvalidPayloadException("Could not parse class from payload $payload: " + ex.getCause()))
      return
    }

    // Reset ID attribute if passed.
    if (data && data.id) {
      data.id = null
    }

    try {
      data = service.create(data)
    } catch (Exception ex) {
      response.setStatus(500)
      render gson.toJson(new DataException("Could not persist object: $data" + ex.getCause()))
      return
    }

    response.setStatus(201)
    render gson.toJson(data)
  }


  // Path: /user [POST]
  def update(String payload) {

    User data
    try {
      data = gson.fromJson(payload, User.class)
    } catch (JsonSyntaxException ex) {
      response.setStatus(500)
      render gson.toJson(new InvalidPayloadException("Could not parse class from payload: $payload, " + ex.getCause()))
      return
    }

    try {
      data = service.update(data)
    } catch (Exception ex) {
      response.setStatus(500)
      render gson.toJson(new DataException("Could not update object: $data," + ex.getCause()))
      return
    }

    response.setStatus(200)
    render gson.toJson(data)
  }

  // Path: /user/{id} [DELETE]
  def delete(Long id) {

    User data = service.find(User.class, id)

    if (!data) {
      response.setStatus(200)
      return
    }

    try {
      service.delete(data)
    } catch (Exception ex) {
      response.setStatus(500)
      render gson.toJson(new DataException("Could not delete object: $data," + ex.getCause()))
      return
    }

    response.setStatus(200)
    return
  }
}
