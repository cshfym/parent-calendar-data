package com.parentcalendar.services.mongo

import com.mongodb.*
import grails.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class MongoService {

    private static String MONGOLAB_URI = (System.getenv("MONGOLAB_URL")) ?:
        "mongodb://heroku_app32454887:quhav5sp9l34c8rb867ccvf996@ds047040.mongolab.com:47040/heroku_app32454887"

    MongoClient client
    DB connection

    public MongoService() throws UnknownHostException {
        MongoClientURI uri  = new MongoClientURI(MONGOLAB_URI)
        client = new MongoClient(uri)
        connection = client.getDB(uri.getDatabase())
    }

    public void saveData(Object payload) {

        /*
          Estimated data size:
          Marathon GPX data = ~2M.
          4-Mile GPX data = ~125-150K.

         */

        final BasicDBObject insertGPSObject = createDBObjectFromPayload(payload)
        DBCollection gpsData = connection.getCollection("gpsdata")
        gpsData.insert(insertGPSObject)
    }

    /*
    public List<GPSPayloadWrapper> getGPSPayloadWrapperDataByUserId(String userId) {

        List<GPSPayloadWrapper> list = []

        DBCollection gpsData = connection.getCollection("gpsdata")
        BasicDBObject findQuery = new BasicDBObject("userId", new BasicDBObject("\$eq", userId)) //gte, eq, lte
        BasicDBObject orderBy = new BasicDBObject("createDate", 1)
        DBCursor docs = gpsData.find(findQuery).sort(orderBy)
        while(docs.hasNext()){
            list << createGPSPayloadWrapperFromDBObject(docs.next())
        }
        list
    }
    */

    /*
    private BasicDBObject createDBObjectFromPayload(TestData payload) {
        BasicDBObject dbObject = new BasicDBObject()
        dbObject.with {
            put("userId", payload.userId)
            put("createDate", payload.createDate)
            put("payloadType", payload.payloadType)
            put("base64Payload", payload.base64Payload)
        }
        dbObject
    }
    */

    /*
    private GPSPayloadWrapper createGPSPayloadWrapperFromDBObject(DBObject dbObject) {
        new GPSPayloadWrapper(
                userId: dbObject.get("userId"),
                createDate: dbObject.get("createDate"),
                payloadType: dbObject.get("payloadType"),
                base64Payload: dbObject.get("base64Payload")
        )
    }
    */

    /*
    private stuff() {

        // Sample Update
        BasicDBObject updateQuery = new BasicDBObject("song", "One Sweet Day")
        songs.update(updateQuery, new BasicDBObject("\$set", new BasicDBObject("artist", "Mariah Carey ft. Boyz II Men")))

        // Sample query
        BasicDBObject findQuery = new BasicDBObject("weeksAtOne", new BasicDBObject("\$gte",10)) //gte, eq, lte
        BasicDBObject orderBy = new BasicDBObject("decade", 1)
        DBCursor docs = songs.find(findQuery).sort(orderBy)
        while(docs.hasNext()){
            DBObject doc = docs.next()
            System.out.println(
                    "In the " + doc.get("decade") + ", " + doc.get("song") +
                            " by " + doc.get("artist") + " topped the charts for " +
                            doc.get("weeksAtOne") + " straight weeks."
            )
        }

        // Since this is an example, we'll clean up after ourselves.
        songs.drop()

        // Only close the connection when your app is terminating
        client.close()
    }

    public static BasicDBObject[] createSeedData(){

        BasicDBObject seventies = new BasicDBObject()
        seventies.put("decade", "1970s")
        seventies.put("artist", "Debby Boone")
        seventies.put("song", "You Light Up My Life")
        seventies.put("weeksAtOne", 10)

        BasicDBObject eighties = new BasicDBObject()
        eighties.put("decade", "1980s")
        eighties.put("artist", "Olivia Newton-John")
        eighties.put("song", "Physical")
        eighties.put("weeksAtOne", 10)

        BasicDBObject nineties = new BasicDBObject()
        nineties.put("decade", "1990s")
        nineties.put("artist", "Mariah Carey")
        nineties.put("song", "One Sweet Day")
        nineties.put("weeksAtOne", 16)

        final BasicDBObject[] seedData = new BasicDBObject[3]
        seedData[0] = seventies
        seedData[1] = eighties
        seedData[2] = nineties
        seedData
    }
    */
}

