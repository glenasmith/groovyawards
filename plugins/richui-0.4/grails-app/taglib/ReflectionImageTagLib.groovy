import de.andreasschmitt.richui.taglib.renderer.*
/*
*
* @author Andreas Schmitt
*/
class ReflectionImageTagLib {
	
	static namespace = "richui"
		
	Renderer reflectionImageRenderer
	
	def reflectionImage = { attrs ->
		//Render output
		try {
			if(!attrs?.reflectionHeight){
				attrs.reflectionHeight = "50"
			}
			
			if(!attrs?.reflectionOpacity){
				attrs.reflectionOpacity = "80"
			}
			
			out << reflectionImageRenderer.renderTag(attrs)
		}
		catch(RenderException e){
			log.error(e)
		}
	}

}
