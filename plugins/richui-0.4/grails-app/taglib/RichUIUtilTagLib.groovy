import de.andreasschmitt.richui.taglib.renderer.*

/*
*
* @author Andreas Schmitt
*/
class RichUIUtilTagLib {
	
	static namespace = "richui"
	
	Renderer feedRenderer
	
	//Redirects to another url
	def redirect = { attrs ->
		if(attrs?.url){
			response?.sendRedirect(attrs.url)	
		}	
	}
	
	//Embed feed
	def feed = { attrs ->
		if(attrs?.type && attrs?.url){
			attrs.title = attrs?.title ? attrs.title: attrs?.type?.toUpperCase()
			attrs.type = "application/${attrs?.type?.toLowerCase()}+xml"	
				
			//Render output
			try {
				out << feedRenderer?.renderTag(attrs)
			}
			catch(RenderException e){
				log.error(e)
			}	
		}
		else {
			log.error("Attributes type and url are mandatory")
		}
	}

}