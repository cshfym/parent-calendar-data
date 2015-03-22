package com.parentcalendar.controller

import com.google.gson.JsonSyntaxException
import com.parentcalendar.domain.Calendar
import com.parentcalendar.domain.common.GenericResponse
import com.parentcalendar.domain.enums.RequestScope
import com.parentcalendar.exception.DataException
import com.parentcalendar.exception.InvalidPayloadException
import grails.converters.JSON

class CalendarController extends BaseController {

    def findAll() {

        def calendars

        if (requestScope == RequestScope.USER) {
            calendars = Calendar.findAllByUser(requestUser)
        } else {
            calendars = Calendar.list()
        }

        response.setStatus(200)
        render calendars as JSON
    }

    def show(Long id) {
        render Calendar.find { id == id } as JSON
    }

    def create() {

        if (!super.validateRequestPayload(request?.JSON)) {
            return
        }

        Calendar data
        try {
            data = new Calendar(request.JSON)
        } catch (JsonSyntaxException ex) {
            def msg = "Could not parse class from payload $request.JSON: " + ex.getCause()
            log.error msg, ex
            response.setStatus(500)
            render gson.toJson(new InvalidPayloadException(msg))
            return
        }

        data.createDate = new Date()
        data.updateDate = null

        // render gson.toJson(super.create(data, service))
        // render gson.toJson(data.save(flush: true), Calendar.class)
        data.save(flush: true)
        render data as JSON
    }

    def update() {

        if (!super.validateRequestPayload(request?.JSON)) {
            return
        }

        Calendar data
        try {
            data = new Calendar(request?.JSON)
        } catch (JsonSyntaxException ex) {
            def msg = "Could not parse class from payload: $payload, " + ex.getCause()
            log.error msg, ex
            response.setStatus(500)
            render gson.toJson(new InvalidPayloadException(msg))
            return
        }

        data.updateDate = new Date()

        // render gson.toJson(super.update(data, service))
        // render gson.toJson(data.save(flush: true), Calendar.class)
        data.save(flush: true)
        render data as JSON
    }

    def delete(Long id) {

        // Calendar data = service.find(Calendar.class, id)
        def data = Calendar.find { id == id }

        if (!data) {
            def msg = "Entity not found with ID of $id"
            log.info msg
            response.setStatus(200)
            render gson.toJson(new GenericResponse(msg))
            return
        }

        try {
            // service.delete(data)
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
