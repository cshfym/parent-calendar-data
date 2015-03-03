class UrlMappings {

	static mappings = {

      "/user"       (controller:"user") { action = [GET:"findAll", PUT:"update", POST:"create"] }
      "/user/$id"   (controller:"user") { action = [GET:"show", DELETE:"delete"] }

      "/calendar"       (controller:"calendar") { action = [GET:"findAll", PUT:"update", POST:"create"] }
      "/calendar/$id"   (controller:"calendar") { action = [GET:"show", DELETE:"delete"] }

      "/calendar/event"       (controller:"calendarEvent") { action = [GET:"findAll", PUT:"update", POST:"create"] }
      "/calendar/event/$id"   (controller:"calendarEvent") { action = [GET:"show", DELETE:"delete"] }

      "/user/token"       (controller:"userToken") { action = [PUT:"refresh", POST:"token"] }
	}
}
