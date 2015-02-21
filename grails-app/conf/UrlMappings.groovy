class UrlMappings {

	static mappings = {

    "/user"       (controller:"user") { action = [GET:"findAll", PUT:"update", POST:"create"] }
    "/user/$id"   (controller:"user") { action = [GET:"show", DELETE:"delete"] }

	}
}
