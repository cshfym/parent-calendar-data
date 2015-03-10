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

  public boolean validateToken(String token) {

    def tokenSplit = extractTokenComponents(token)

    // Validate parameters.
    if (tokenSplit && tokenSplit.size() != 3) {
      log.error "Incorrect auth token format."
      return false
    }

    def jSessionId = tokenSplit[1]

    // Validate User ID parameter.
    User user
    try {
      user = userDataService.find(User.class, Long.parseLong(tokenSplit[0]))
    } catch (NumberFormatException ex) {
      log.error "Incorrect auth token format - invalid user ID parameter."
      return false
    }

    // Validate shared application token parameter.
    if (tokenSplit[2] != grailsApplication.config.authentication.token.toString()) {
      log.error "Incorrect auth token - token mismatch."
      return false
    }

    // Retrieve prior token for user, if available.
    def userToken = super.findBy(UserToken.class, "user.id", user.id)

    // If no prior user token, or different session ID, create one.
    if (!userToken) {
      return false
    }

    // If prior token was expired, force a re-login.
    if (isExpired(userToken.issued) || (userToken.sessionId != jSessionId)) {
      return false
    }

    true
  }

  public boolean isExpired(def issued) {

    int expirationMinutes = Integer.valueOf(grailsApplication.config.authentication.expiration)
    if (Calendar.getInstance().timeInMillis > (issued.time + (1000 * 60 * expirationMinutes))) {
        return true
    }
    false
  }

    /**
     * Extract the user ID from the header value. Method assumes validation on the token components was previously completed.
     * @param value
     * @return
     */
  public Long extractUserIdFromHeader(String token) {
      def tokenSplit = extractTokenComponents(token)
      Long.parseLong(tokenSplit[0])
  }

  private String[] extractTokenComponents(def token) {

      def decoded = new String(token.decodeBase64())
      decoded.split("\\|")
  }
  /*
  private String getTokenString(int len = 20) {
    RandomStringUtils.random(len, (("a".."z") + ("0".."9")).join().toCharArray())
  }
  */
}
