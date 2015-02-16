package com.parentcalendar.controller

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.parentcalendar.domain.TestData
import com.parentcalendar.exception.InvalidPayloadException
import com.parentcalendar.services.mq.MQService
import com.parentcalendar.services.utility.StringCompressor
import com.parentcalendar.services.db.GenericDataService
import com.parentcalendar.services.mongo.MongoService
import com.parentcalendar.services.security.ParentCalendarSecurityService
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

    @Autowired
    ParentCalendarSecurityService securityService

    @Autowired
    GenericDataService dataService

    @Autowired
    MongoService mongoService

    @Autowired
    MQService mqService

    @Autowired
    Gson gson

    def index() {
      response.setStatus(404)
      return
    }

    def push() {
        /*
      MQService mqService = new MQService("54a78bd2a65b22000900007b", "xwvYCsVgSmla3CCe7JIhXf4YNs0")
      mqService.pushMessage("Test Message", "test-queue")
      response.setStatus(200)
      */
    }

  /**
   * Fetches TestData.
   * @path: /data/$id
   * @param id
   * @return
   */
    def show(String id) {

      if (null == id) {
        response.setStatus(404)
        return
      }

      // Retrieve TestData object by ID.
      TestData data = dataService.find(TestData.class, Long.valueOf(id))
      if (null == data) {
        response.setStatus(404)
        return
      }

      // Convert compressed payload back to Base64-encoded string.
      // String base64Payload = StringCompressor.decompress(data.payload)

      // TODO: Move this to a converter class.
      /* Convert to GPSPayloadWrapper object return class.
      GPSPayloadWrapper wrapper = new GPSPayloadWrapper(
        id: data.id,
        userId: data.userId,
        payload: base64Payload,
        payloadClass: data.payloadClass,
        createDate: data.createDate,
        updateDate: data.updateDate
      )
       */

      response.setStatus(200)
      render gson.toJson(data)
    }

    def findAll() {

      List<TestData> list = dataService.findAll(TestData.canonicalName)
      response.setStatus(200)
      render gson.toJson(list)
    }

    def validateRequestPayload() {

      Boolean validated = Boolean.TRUE
      if(!request.JSON?.userId) { validated = Boolean.FALSE }
      //if(!request.JSON?.payload) { validated = Boolean.FALSE }
      validated
    }

    def save() {

        /*
     if(!validateRequestPayload()) {
       response.setStatus(500)
       render gson.toJson(new InvalidPayloadException("Invalid payload format - failed validation."))
       return
     }


     GPSPayloadWrapper wrapper
     try {
       wrapper = new GPSPayloadWrapper(
         userId: request.JSON?.userId,
         payload: request.JSON?.payload
       )
     } catch (JsonSyntaxException jEx) {
       response.setStatus(500)
       render gson.toJson(new InvalidPayloadException("Invalid payload format (JsonSyntaxException)."))
       return
     }


     // Instantiate a TestData DB-compatible object.
     GPSData data = new GPSData(userId: wrapper.userId)

     // Compress and convert Base64-encoded payload to byte[].
     data.setPayload(StringCompressor.compress(wrapper.payload))

     /*
     def file = grailsAttributes.getApplicationContext().getResource("gpxdata/OgdenMarathon.base64").getFile()
     println "Size before compressed: ${file.getText('UTF-8').length()}"
     byte[] compressed = StringCompressor.compress(file.getText('UTF-8'))
     println "Size after compressed: ${compressed.length}"
     data.setPayload(compressed)*
     */

      TestData data = new TestData(userId: request.JSON.userId, )

      try {
        data = dataService.persistTestData(data)
      } catch (Exception ex) {
        response.setStatus(500)
        render gson.toJson(new InvalidPayloadException("Could not persist GPS data: " + ex.getCause()))
        return
      }

      response.setStatus(201)
      render gson.toJson(data)
    }

    def update() {
        //Employee e = new Employee(firstName: "Update", lastName: "Method")
        //render gson.toJson(e)
    }

    def delete() {
        //Employee e = new Employee(firstName: "Delete", lastName: "Method")
        //render gson.toJson(e)
    }

    def byId() {
      response.setStatus(500)
    }
}
