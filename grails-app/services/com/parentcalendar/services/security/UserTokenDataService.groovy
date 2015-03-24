package com.parentcalendar.services.security

import com.parentcalendar.domain.auth.User
import com.parentcalendar.domain.auth.UserToken
import grails.transaction.Transactional
import org.apache.commons.logging.LogFactory
import org.springframework.stereotype.Component

@Component
@Transactional
class UserTokenDataService  {

    private static final log = LogFactory.getLog(this)

    def grailsApplication

    public boolean validateToken(String token) {

        def tokenSplit = extractTokenComponents(token)

        // Validate parameters.
        if (tokenSplit && tokenSplit.size() != 3) {
            log.error "Incorrect auth token format."
            return false
        }

        def jSessionId = tokenSplit[1]

        if (tokenSplit[2] != grailsApplication.config.authentication.token.toString()) {
            return false
        }

        // Validate shared application token parameter.
        if (tokenSplit[2] != grailsApplication.config.authentication.token.toString()) {
            log.error "Incorrect auth token - token mismatch."
            return false
        }

        // Validate User ID parameter.
        User user
        try {
            user = User.findById(Long.parseLong(tokenSplit[0]))
        } catch (NumberFormatException ex) {
            log.error "Incorrect auth token format - invalid user ID parameter."
            return false
        }

        // Validate token itself.
        def existingToken = UserToken.findByUserAndToken(user, token)
        if (!existingToken) {
            return false
        }

        // Validate token has not expired.
        if (isExpired(existingToken.issued)) {
            log.error "Token expiration session for user $user.username"
            return false
        }

        true
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

    /**
     * Determine whether the given issued time has expired.
     * @param issued
     * @return
     */
    public boolean isExpired(def issued) {

        int expirationMinutes = Integer.valueOf(grailsApplication.config.authentication.expiration as String)
        if (Calendar.getInstance().timeInMillis > (issued.time + (1000 * 60 * expirationMinutes))) {
            return true
        }
        false
    }

    private String[] extractTokenComponents(def token) {

        def decoded = new String(token.decodeBase64())
        decoded.split("\\|")
    }

}
