package filters

import com.parentcalendar.domain.security.UserToken
import com.parentcalendar.services.orm.UserTokenDataService
import org.springframework.beans.factory.annotation.Autowired

class SecurityFilters {

  @Autowired
  UserTokenDataService tokenService

  def filters = {

   all(controller: '*', action: '*') {

      before = {

        def isGlobal = request.getHeader("X-Auth-Global-Data")

        // No auth header.
        if (!request.getHeader("Authorization")) {
          response.setStatus(401)
          return false
        }

        // User token - authorization header format ID|JSESSIONID|TOKEN
        UserToken userToken = tokenService.getToken(request.getHeader("Authorization").toString())

        if (!userToken) {
          response.setStatus(401)
          return false
        }

        return true
      }

      after = { Map model ->  }

      afterView = { Exception e -> }
    }
  }
}
