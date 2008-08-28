class UrlMappings {
    static mappings = {
      "/$controller/$action?/$id?"{
	      constraints {
			 // apply constraints here
		  }

//
//       "/" {
//            controller = "nominate"
//            action = "home"
//        }

      }

        
      "500"(view:'/error')
	}
}
