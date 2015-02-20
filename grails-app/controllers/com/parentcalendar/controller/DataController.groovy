package com.parentcalendar.controller

import com.google.gson.Gson
import com.parentcalendar.domain.GenericResponse
import com.parentcalendar.domain.TestData
import com.parentcalendar.domain.User
import com.parentcalendar.exception.InvalidPayloadException
import com.parentcalendar.services.mq.MQService
import com.parentcalendar.services.redis.RedisService
import com.parentcalendar.services.db.GenericDataService
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired

/* Grails Controllers:
HTTP Method	URI	Controller Action
GET	/books	index
GET	/books/create	create
POST	/books	save
GET	/books/${id}	show
GET	/books/${id}/edit	edit
PUT	/books/${id}	update
DELETE	/books/${id}	delete

The 'create' and 'edit' actions are already required if you plan to implement an HTML interface for the REST resource.
They are there in order to render appropriate HTML forms to create and edit a resource.
If this is not a requirement they can be discarded.
*/

public class DataController {

  private static final log = LogFactory.getLog(this)

  @Autowired
  MQService mqService

  @Autowired
  RedisService redisService

  @Autowired
  Gson gson

  def index() {
    response.setStatus(404)
    return
  }

  def getCache() {

    log.info("Getting cache at cache/123")

    response.setStatus(200)
    def obj = redisService.getCache("cache/123", TestData.class)

    if (!obj) {
      response.setStatus(404)
      render gson.toJson(new GenericResponse(response: "Not Found"))
      return
    }

    response.setStatus(200)
    render gson.toJson(obj)
  }

  def setCache() {
    User data = new User(id: request.JSON)
    log.info("Setting cache at cache/123: " + data.toString())
    redisService.setCache("cache/123", data, User.TTL)
    response.setStatus(200)

    render gson.toJson(new GenericResponse(response: "Success"))
  }

}
