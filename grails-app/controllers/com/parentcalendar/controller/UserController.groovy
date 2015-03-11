package com.parentcalendar.controller

import com.google.gson.JsonSyntaxException
import com.parentcalendar.domain.common.GenericResponse
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

  def findAll() {
      render gson.toJson(super.findAllByType(User.class, service))
  }

  def findBy(String column, Object value) {
      render gson.toJson(super.findBy(User.class, service, column, value))
  }
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
