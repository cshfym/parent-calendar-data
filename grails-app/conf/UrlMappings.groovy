class UrlMappings {

	static mappings = {

      "/user"       (controller:"user") { action = [GET:"findAll", PUT:"update", POST:"create"] }
      "/user/$id"   (controller:"user") { action = [GET:"show", DELETE:"delete"] }

      "/calendar"       (controller:"calendar") { action = [GET:"findAll", PUT:"update", POST:"create"] }
      "/calendar/$id"   (controller:"calendar") { action = [GET:"show", DELETE:"delete"] }
	}
}
