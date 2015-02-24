package com.parentcalendar.controller

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.parentcalendar.domain.GenericResponse
import com.parentcalendar.domain.User
import com.parentcalendar.exception.DataException
import com.parentcalendar.exception.InvalidPayloadException
import com.parentcalendar.services.orm.UserDataService
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired

class UserController extends BaseController {

  private static final log = LogFactory.getLog(this)

  @Autowired
  UserDataService service

    Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

  def findAll() {
      render gson.toJson(super.findAllByType(User.class, service))
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

  // Path: /user [POST]
  def create() {

    def payload = request.JSON

    if (!payload) {
      def msg = "Payload JSON cannot be null or empty."
      log.error msg
      response.setStatus(500)
      render gson.toJson(new InvalidPayloadException(msg))
      return
    }

    User data
    try {
      data = new User(payload)
    } catch (JsonSyntaxException ex) {
      def msg = "Could not parse class from payload $payload: " + ex.getCause()
      log.error msg, ex
      response.setStatus(500)
      render gson.toJson(new InvalidPayloadException(msg))
      return
    }

    // Reset ID attribute if passed.
    if (data && data.id) {
      data.id = null
    }

    try {
      data = service.create(data)
    } catch (Exception ex) {
      def msg = "Could not persist object: $data" + ex.getCause()
      log.error msg, ex
      response.setStatus(500)
      render gson.toJson(new DataException(msg))
      return
    }

    response.setStatus(201)
    render gson.toJson(data)
  }


  // Path: /user [PUT]
  def update() {

    def payload = request.JSON

    User data
    try {
      data = new User(payload)
    } catch (JsonSyntaxException ex) {
      def msg = "Could not parse class from payload: $payload, " + ex.getCause()
      log.error msg, ex
      response.setStatus(500)
      render gson.toJson(new InvalidPayloadException(msg))
      return
    }

    try {
      data = service.update(data)    }
    catch (Exception ex) {
      def msg = "Could not update object: $data," + ex.getMessage()
      log.error msg, ex
      response.setStatus(500)
      render gson.toJson(new DataException(msg))
      return
    }

    response.setStatus(200)
    render gson.toJson(data)
  }

  // Path: /user/{id} [DELETE]
  def delete(Long id) {

    User data = service.find(User.class, id)

    if (!data) {
      def msg = "Entity not found with ID of $id"
      log.error msg
      response.setStatus(200)
      render gson.toJson(new GenericResponse(msg))
      return
    }

    try {
      service.delete(data)
    } catch (Exception ex) {
      def msg = "Could not delete object: $data," + ex.getCause()
      log.error msg, ex
      response.setStatus(500)
      render gson.toJson(new DataException(msg))
      return
    }

    response.setStatus(200)
    render gson.toJson(new GenericResponse("Deleted $data"))
  }
}
