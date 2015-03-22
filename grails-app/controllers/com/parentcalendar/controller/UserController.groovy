package com.parentcalendar.controller

import com.google.gson.JsonSyntaxException
import com.parentcalendar.domain.auth.User
import com.parentcalendar.domain.common.GenericResponse
import com.parentcalendar.exception.DataException
import com.parentcalendar.exception.InvalidPayloadException
import grails.converters.JSON
import org.apache.commons.logging.LogFactory

class UserController extends BaseController {

    private static final log = LogFactory.getLog(this)

    def findAll() {
        render User.findAll() as JSON
    }

    def show(Long id) {

        if (null == id) {
            response.setStatus(404)
            return
        }

        User data = User.find { id == id }

        if (null == data) {
            response.setStatus(404)
            return
        }

        response.setStatus(200)
        render data as JSON
    }

    def create() {

        def payload = request.JSON

        if (!payload) {
            def msg = "Payload JSON cannot be null or empty."
            log.error msg
            response.setStatus(500)
            render gson.toJson(new InvalidPayloadException(msg))
            return
        }

        User data
        try {
            data = new User(payload)
        } catch (JsonSyntaxException ex) {
            def msg = "Could not parse class from payload $payload: " + ex.getCause()
            log.error msg, ex
            response.setStatus(500)
            render gson.toJson(new InvalidPayloadException(msg))
            return
        }

        // Reset ID attribute if passed.
        if (data && data.id) {
            data.id = null
        }

        try {
            data.save(flush: true)
        } catch (Exception ex) {
            def msg = "Could not persist object: $data" + ex.getCause()
            log.error msg, ex
            response.setStatus(500)
            render gson.toJson(new DataException(msg))
            return
        }

        response.setStatus(201)
        render data as JSON
    }

    def update() {

        def payload = request.JSON

        User data
        try {
            data = new User(payload)
        } catch (JsonSyntaxException ex) {
            def msg = "Could not parse class from payload: $payload, " + ex.getCause()
            log.error msg, ex
            response.setStatus(500)
            render gson.toJson(new InvalidPayloadException(msg))
            return
        }

        try {
            data.save(flush: true)
        }
        catch (Exception ex) {
            def msg = "Could not update object: $data," + ex.getMessage()
            log.error msg, ex
            response.setStatus(500)
            render gson.toJson(new DataException(msg))
            return
        }

        response.setStatus(200)
        render data as JSON
    }

    def delete(Long id) {

        def data = User.find { id == id }

        if (!data) {
            def msg = "Entity not found with ID of $id"
            log.error msg
            response.setStatus(200)
            render gson.toJson(new GenericResponse(msg))
            return
        }

        try {
           data.delete(flush: true)
        } catch (Exception ex) {
            def msg = "Could not delete object: $data," + ex.getCause()
            log.error msg, ex
            response.setStatus(500)
            render gson.toJson(new DataException(msg))
            return
        }

        response.setStatus(200)
        render gson.toJson(new GenericResponse("Deleted $data"))
    }
}
