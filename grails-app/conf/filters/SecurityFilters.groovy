package filters

import com.parentcalendar.services.security.UserTokenDataService
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

                // No valid token.
                if (!tokenService.validateToken(request.getHeader("Authorization").toString())) {
                    response.setStatus(401)
                    // TODO Logging
                    return false
                }
            }

            after = { Map model ->  }

            afterView = { Exception e -> }
        }
    }
}
