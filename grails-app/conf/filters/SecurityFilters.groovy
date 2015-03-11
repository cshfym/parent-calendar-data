package filters

import com.parentcalendar.domain.enums.Constants
import com.parentcalendar.services.orm.UserTokenDataService
import org.springframework.beans.factory.annotation.Autowired

class SecurityFilters {

  @Autowired
  UserTokenDataService tokenService

  def filters = {

   all(controller: '*', action: '*') {

      before = {

        // No auth header.
        if (!request.getHeader("Authorization")) {
          response.setStatus(401)
          // TODO Logging
          return false
        }

        // Check for generic no-auth method.
        if (null != request.getHeader(Constants.X_AUTH_NO_AUTH.value)
                && Boolean.parseBoolean(request.getHeader(Constants.X_AUTH_NO_AUTH.value as String))
                && request.getHeader("Authorization") == grailsApplication.config.authentication.token as String) {
            return true
        }

        // No-auth is not enabled; enforce user authorization token.
        if (!tokenService.validateToken(request.getHeader("Authorization").toString())) {
            response.setStatus(401)
            // TODO Logging
            return false
        }

        true
      }

      after = { Map model ->  }

      afterView = { Exception e -> }
    }
  }
}
