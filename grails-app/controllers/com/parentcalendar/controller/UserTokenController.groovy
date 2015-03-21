package com.parentcalendar.controller

import com.parentcalendar.services.security.UserTokenDataService
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired

class UserTokenController extends BaseController {

  private static final log = LogFactory.getLog(this)

  @Autowired
  UserTokenDataService service

  /**
   * Returns the token for the given userId (creating if one doesn't exist).
   */
  def token() {

      /*
    def payload = request.JSON

    if (!request.JSON || !request.JSON?.userId) {
      response.setStatus(500)
      log.debug "Invalid payload."
      return
    }

    User user = userDataService.find(User.class, payload.userId)

    if (!user) {
      response.setStatus(404)
      log.debug "User not found attempting to call token(), id: $payload.userId"
      return
    }

    // User established, check for token.
    UserToken token
    try {
      token = service.findBy(UserToken.class, "user.id", Long.valueOf(payload.userId))
    } catch (Exception ex) {
      log.error ex.getMessage(), ex
      response.setStatus(500)
      return gson.toJson(new DataException("Exception during user token processing."))
    }

    // No token - generate a new token and return it.
    if (!token) {
      try {
        token = service.createAndPersistToken(user)
      } catch (Exception ex) {
        log.error ex.getMessage(), ex
        response.setStatus(500)
        return gson.toJson(new DataException("Exception during token creation."))
      }
    } else {
      // Expired token?
      if (service.isExpired(token)) {
        log.debug "User token expired, $user.username"
        //service.deleteToken(token)
        //token = service.createAndPersistToken(user)
      }
    }

    response.setStatus(200)
    render gson.toJson(token)

    */
      null
  }

  def refresh() {

  }

     /*
  def show(Long id) {

    if (null == id) {
      response.setStatus(404)
      return
    }

    User data = service.find(UserToken.class, id)
    if (null == data) {
      response.setStatus(404)
      return
    }

    response.setStatus(200)
    render gson.toJson(data)
  }

  def create() {

    UserToken data
    try {
      data = new UserToken(
        issued: new Date(),

      )
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
  */
}
