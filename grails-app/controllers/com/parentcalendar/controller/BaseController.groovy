package com.parentcalendar.controller

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.parentcalendar.domain.common.GenericResponse
import com.parentcalendar.domain.common.Persistable
import com.parentcalendar.exception.DataException
import com.parentcalendar.exception.InvalidPayloadException
import com.parentcalendar.services.db.BaseDataService
import com.parentcalendar.services.exclusion.EntityExclusionStrategy
import org.apache.commons.logging.LogFactory

class BaseController {

    private static final log = LogFactory.getLog(this)

    public static final Gson gson = new GsonBuilder()
        .setExclusionStrategies(new EntityExclusionStrategy())
        .setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz")
        .serializeNulls()
        .create()


    public <T> Object findAllByType(T type, BaseDataService service) {

        List<T> list = []
        try {
            list = service.findAll(type)
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
            obj = service.find(type, id)
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
}
