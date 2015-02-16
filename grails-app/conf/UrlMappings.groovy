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
        "/data"(controller:"data") {
            action = [PUT:"save", DELETE:"delete", POST:"update"]
        }
        "/data/$id"(controller:"data") {
            action = [GET:"show", PUT:"updateById", DELETE: "deleteById"]
        }
        "/data/all"(controller:"data", action:"findAll")
        "/data/push"(controller:"data", action:"push")
        "/data/cache"(controller:"data", action:"cache")

		"500"(view:'/error')
	}
}
