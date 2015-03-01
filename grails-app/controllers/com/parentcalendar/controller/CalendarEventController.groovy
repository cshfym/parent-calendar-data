package com.parentcalendar.controller

import com.google.gson.JsonSyntaxException
import com.parentcalendar.domain.CalendarEvent
import com.parentcalendar.domain.common.GenericResponse
import com.parentcalendar.exception.DataException
import com.parentcalendar.exception.InvalidPayloadException
import com.parentcalendar.services.orm.CalendarEventDataService
import org.springframework.beans.factory.annotation.Autowired

class CalendarEventController extends BaseController {

    @Autowired
    CalendarEventDataService service

    @Override
    def show(Long id) {
        render gson.toJson(super.findOne(CalendarEvent.class, id, service))
    }

    def create() {

        if (!super.validateRequestPayload(request?.JSON)) {
            return
        }

        CalendarEvent data
        try {
            data = new CalendarEvent(request.JSON)
        } catch (JsonSyntaxException ex) {
            def msg = "Could not parse class from payload $request.JSON: " + ex.getCause()
            log.error msg, ex
            response.setStatus(500)
            render gson.toJson(new InvalidPayloadException(msg))
            return
        }

        data.createDate = new Date()
        data.updateDate = null

        render gson.toJson(super.create(data, service))
    }

    def update() {

        if (!super.validateRequestPayload(request?.JSON)) {
            return
        }

        CalendarEvent data
        try {
            data = new CalendarEvent(request?.JSON)
        } catch (JsonSyntaxException ex) {
            def msg = "Could not parse class from payload: $payload, " + ex.getCause()
            log.error msg, ex
            response.setStatus(500)
            render gson.toJson(new InvalidPayloadException(msg))
            return
        }

        data.updateDate = new Date()

        render gson.toJson(super.update(data, service))
    }

    def delete(Long id) {

        CalendarEvent data = service.find(CalendarEvent.class, id)

        if (!data) {
            def msg = "Entity not found with ID of $id"
            log.error msg
            response.setStatus(200)
            render gson.toJson(new GenericResponse(msg))
            return
        }

        try {
            service.delete(data)
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
