package com.parentcalendar.controller

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.parentcalendar.domain.common.GenericResponse
import com.parentcalendar.domain.common.Persistable
import com.parentcalendar.domain.enums.Constants
import com.parentcalendar.domain.enums.RequestScope
import com.parentcalendar.domain.security.UserToken
import com.parentcalendar.exception.DataException
import com.parentcalendar.exception.InvalidPayloadException
import com.parentcalendar.services.db.BaseDataService
import com.parentcalendar.services.exclusion.EntityExclusionStrategy
import com.parentcalendar.services.orm.UserTokenDataService
import org.apache.commons.logging.LogFactory
import org.h2.util.StringUtils
import org.springframework.beans.factory.annotation.Autowired

class BaseController {

    private static final log = LogFactory.getLog(this)

    @Autowired
    UserTokenDataService tokenDataService

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

    public <T> Object findAllByType(T type, BaseDataService service) {

        List<T> list
        try {
            list = service.findAll(type, requestScope, requestUserId, userId)
        } catch (Exception ex) {
            def msg = "Could not execute findAll query, type $type: " + ex.getCause()
            log.error msg, ex
            response.setStatus(500)
            return new DataException(msg)
        }
        response.setStatus(200)
        list
    }

    public <T> Object findOne(T type, Long id, BaseDataService service) {

        Object obj

        if (null == id) {
            response.setStatus(404)
            return new GenericResponse("Object id cannot be null.")
        }

        try {
            obj = service.find(type, id, requestScope)
        } catch (Exception ex) {
            response.setStatus(500)

            return new DataException("Exception finding single entity with id [$id]: " + ex.getMessage(), ex)
        }

        if (null == obj) {
            response.setStatus(404)
            return new GenericResponse("Not found.")
        }

        response.setStatus(200)
        obj
    }

    public <T> Object findBy(T type, BaseDataService service, String col, Object val) {

        Object obj

        if (!col) {
            response.setStatus(404)
            return new GenericResponse("Column name cannot be null.")
        }

        if (!val) {
            response.setStatus(404)
            return new GenericResponse("Column value cannot be null.")
        }

        try {
            obj = service.findBy(type, col, val)
        } catch (Exception ex) {
            response.setStatus(500)

            return new DataException("Exception finding single entity with column [$col] and value [$val]: " + ex.getMessage(), ex)
        }

        if (null == obj) {
            response.setStatus(404)
            return new GenericResponse("Not found.")
        }

        response.setStatus(200)
        obj
    }

    public Object create(Persistable p, BaseDataService service) {

        // Reset ID attribute if passed.
        if (p && p.id) {
            p.id = null
        }

        Object data
        try {
            data = service.create(p)
        } catch (Exception ex) {
            def msg = "Could not persist object: $p" + ex.getCause()
            log.error msg, ex
            response.setStatus(500)
            return new DataException(msg)
        }

        response.setStatus(201)
        data
    }

    public Object update(Persistable p, BaseDataService service) {

        Object data
        try {
            data = service.update(p)
        } catch (Exception ex) {
            def msg = "Could not update object: $p" + ex.getCause()
            log.error msg, ex
            response.setStatus(500)
            return new DataException(msg)
        }

        response.setStatus(200)
        data
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

        def scope = RequestScope.SCOPE_REQUESTOR

        // TODO: Validate user-specific data ID
        if (!StringUtils.isNullOrEmpty(request.getHeader(Constants.X_AUTH_USER_ID.value))) {
            userId = Long.parseLong(request.getHeader(Constants.X_AUTH_USER_ID.value))
            scope = RequestScope.SCOPE_USER
        }

        if (scope != RequestScope.SCOPE_USER
                && !StringUtils.isNullOrEmpty(request.getHeader(Constants.X_AUTH_ALL_USERS.value))
                && Boolean.parseBoolean(request.getHeader(Constants.X_AUTH_ALL_USERS.value))) {
            scope = RequestScope.SCOPE_GLOBAL
        }

        scope
    }
}
