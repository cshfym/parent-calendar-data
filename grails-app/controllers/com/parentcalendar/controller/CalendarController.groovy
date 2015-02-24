package com.parentcalendar.controller

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.parentcalendar.domain.Calendar
import com.parentcalendar.domain.GenericResponse
import com.parentcalendar.domain.User
import com.parentcalendar.exception.DataException
import com.parentcalendar.exception.InvalidPayloadException
import com.parentcalendar.services.orm.CalendarDataService
import org.springframework.beans.factory.annotation.Autowired

class CalendarController extends BaseController {

    @Autowired
    CalendarDataService service

    // TODO: Spring injection, or move the format to somewhere common.
    Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

    def findAll() {
        render gson.toJson(super.findAllByType(Calendar.class, service))
    }

    def show(Long id) {
        render gson.toJson(super.findOne(Calendar.class, id, service))
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

        render gson.toJson(super.create(data, service))
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

        render gson.toJson(super.update(data, service))
    }

    def delete(Long id) {

        Calendar data = service.find(Calendar.class, id)

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
