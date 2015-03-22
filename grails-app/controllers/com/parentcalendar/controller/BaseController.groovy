package com.parentcalendar.controller

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.parentcalendar.domain.auth.User
import com.parentcalendar.domain.enums.Constants
import com.parentcalendar.domain.enums.RequestScope
import com.parentcalendar.exception.InvalidPayloadException
import com.parentcalendar.services.exclusion.EntityExclusionStrategy
import com.parentcalendar.services.security.UserTokenDataService
import org.apache.commons.logging.LogFactory
import org.h2.util.StringUtils
import org.springframework.beans.factory.annotation.Autowired

class BaseController {

    private static final log = LogFactory.getLog(this)

    @Autowired
    UserTokenDataService tokenDataService

    // TODO Get from config.
    public static final Gson gson = new GsonBuilder()
        .setExclusionStrategies(new EntityExclusionStrategy())
        .setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz")
        .serializeNulls()
        .create()

    public Long requestUserId // User from which request originated

    public Long userId // User-specific data ID

    public RequestScope requestScope

    def beforeInterceptor = {

      requestUserId = tokenDataService.extractUserIdFromHeader(request.getHeader("Authorization").toString())

      // Get request scope
      requestScope = getRequestScope()

      // TODO Validate userToken comes cleanly.
    }

    def afterInterceptor = {
        //
    }

    public boolean validateRequestPayload(Object payload) {

        if (!payload) {
            def msg = "Payload cannot be null or empty."
            log.error msg
            response.setStatus(500)
            render gson.toJson(new InvalidPayloadException(msg))
            return false
        }
        true
    }

    protected RequestScope getRequestScope() {

        def scope = RequestScope.USER // Default

        if (StringUtils.isNullOrEmpty(request.getHeader(Constants.X_AUTH_USER_ID.value))) {
            scope = RequestScope.GLOBAL
        } else {
            userId = Long.parseLong(request.getHeader(Constants.X_AUTH_USER_ID.value) as String)
        }

        scope
    }

    protected User getRequestUser() {
        User.findById(userId)
    }
}
