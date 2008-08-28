import de.andreasschmitt.richui.taglib.renderer.*
/*
*
* @author Andreas Schmitt
*/
class PortletTagLib {
	
	static namespace = "richui"
		
	Renderer portletRenderer
	Renderer portletViewRenderer
	
	def portlet = {attrs, body ->
		if(!attrs?.action){
			throw new Exception("Attribute action is required")	
		}
		
		if(!attrs?.views){
			throw new Exception("Attribute views is required")	
		}
		
		if(!attrs?.page){
			attrs.page = "1"	
		}
			
		//Render output
		try {
			out << portletRenderer.renderTag(attrs, body)
		}
		catch(RenderException e){
			log.error(e)
		}
	}
	
	def portletView = {attrs, body ->
		if(!attrs?.page){
			attrs.page = "1"	
		}
	
		//Render output
		try {
			out << portletViewRenderer.renderTag(attrs, body)
		}
		catch(RenderException e){
			log.error(e)
		}
	}
}
