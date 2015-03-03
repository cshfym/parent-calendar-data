package com.parentcalendar.services.orm

import com.parentcalendar.domain.User
import com.parentcalendar.domain.security.UserToken
import com.parentcalendar.services.db.BaseDataService
import grails.transaction.Transactional
import org.apache.commons.lang.RandomStringUtils
import org.springframework.stereotype.Component

@Component
@Transactional
class UserTokenDataService extends BaseDataService {

  def grailsApplication

  public UserToken createAndPersistToken(User user) {

    def token = new UserToken(
      user: user,
      token: getTokenString(),
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

  private String getTokenString(int len = 20) {
    RandomStringUtils.random(len, (("a".."z") + ("0".."9")).join().toCharArray())
  }
}
