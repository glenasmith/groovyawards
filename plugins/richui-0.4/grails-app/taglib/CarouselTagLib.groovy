import de.andreasschmitt.richui.taglib.renderer.*
/*
*
* @author Andreas Schmitt
*/
class CarouselTagLib {
	
	static namespace = "richui"
		
	Renderer carouselRenderer
	Renderer carouselItemRenderer
	
	def carousel = {attrs, body ->	
		//Render output
		try {
			out << carouselRenderer.renderTag(attrs, body)
		}
		catch(RenderException e){
			log.error(e)
		}
	}
	
	def carouselItem = {attrs, body ->	
		//Render output
		try {
			out << carouselItemRenderer.renderTag(attrs, body)
		}
		catch(RenderException e){
			log.error(e)
		}
	}
}
