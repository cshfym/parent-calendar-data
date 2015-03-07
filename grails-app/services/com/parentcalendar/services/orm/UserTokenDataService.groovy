package com.parentcalendar.services.orm

import com.parentcalendar.domain.User
import com.parentcalendar.domain.security.UserToken
import com.parentcalendar.services.db.BaseDataService
import grails.transaction.Transactional
import org.apache.commons.lang.RandomStringUtils
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@Transactional
class UserTokenDataService extends BaseDataService {

  private static final log = LogFactory.getLog(this)

  def grailsApplication

  @Autowired
  UserDataService userDataService

  public UserToken getToken(String token) {

    def decoded = new String(token.decodeBase64())
    def tokenSplit = decoded.split("\\|")

    // Validate parameters.
    if (tokenSplit && tokenSplit.size() != 3) {
      log.error "Incorrect auth token format."
      return null
    }

    def jSessionId = tokenSplit[1]

    // Validate User ID parameter.
    User user
    try {
      user = userDataService.find(User.class, Long.parseLong(tokenSplit[0]))
    } catch (NumberFormatException ex) {
      log.error "Incorrect auth token format - invalid user ID parameter."
      return null
    }

    // Validate shared application token parameter.
    if (tokenSplit[2] != grailsApplication.config.authentication.token.toString()) {
      log.error "Incorrect auth token - token mismatch."
      return null
    }

    // Retrieve prior token for user, if available.
    def userToken = super.findBy(UserToken.class, "user.id", user.id)

    // If no prior user token, or different session ID, create one.
    if (!userToken) {
      return createAndPersistToken(user, jSessionId)
    }

    // If prior token was expired, force a re-login.
    if (isExpired(userToken) || (userToken.sessionId != jSessionId)) {
      deleteToken(userToken)
      return createAndPersistToken(user, jSessionId)
    }

    userToken
  }

  /* TODO: This method could be more robust in generating a random token. */
  public UserToken createAndPersistToken(User user, String jSessionId) {

    def token = new UserToken(
      user: user,
      sessionId: jSessionId,
      token: grailsApplication.config.authentication.token,
      issued: new Date())

    super.create(token)
  }

  public void deleteToken(UserToken token) {
    super.delete(token)
  }

  public boolean isExpired(UserToken token) {

    int expirationMinutes = Integer.valueOf(grailsApplication.config.authentication.expiration)

    if (Calendar.getInstance().timeInMillis > (token.getIssued().time + (1000 * 60 * expirationMinutes))) {
        return true
    }

    false
  }

  /*
  private String getTokenString(int len = 20) {
    RandomStringUtils.random(len, (("a".."z") + ("0".."9")).join().toCharArray())
  }
  */
}
