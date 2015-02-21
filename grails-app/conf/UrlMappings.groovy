class UrlMappings {

	static mappings = {

        /*
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
		"/"(view:"/index")
		*/
    "/data"       (controller:"data") { action = [PUT:"save", DELETE:"delete", POST:"update"] }
    "/data/$id"   (controller:"data") { action = [GET:"show", PUT:"updateById", DELETE: "deleteById"] }
    "/data/all"   (controller:"data", action:"findAll")
    "/data/push"  (controller:"data", action:"push")
    "/data/cache" (controller:"data") { action = [PUT:"setCache", GET:"getCache"] }

    "/user"       (controller:"user") { action = [GET:"findAll", PUT:"update", POST:"create"] }
    "/user/$id"   (controller:"user") { action = [GET:"show", DELETE:"delete"] }

		// "500"(view:'/error')
	}
}
