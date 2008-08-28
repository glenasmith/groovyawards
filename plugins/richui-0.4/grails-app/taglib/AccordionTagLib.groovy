import de.andreasschmitt.richui.taglib.renderer.*
/*
*
* @author Andreas Schmitt
*/
class AccordionTagLib {
	
	static namespace = "richui"
		
	Renderer accordionRenderer
	Renderer accordionItemRenderer
		
	def accordion = {attrs, body ->
						
		//Render output
		try {
			out << accordionRenderer.renderTag(attrs, body)
		}
		catch(RenderException e){
				log.error(e)
		}
	}
	
	def accordionItem = {attrs, body ->
	
		//Render output
		try {
			out << accordionItemRenderer.renderTag(attrs, body)
		}
		catch(RenderException e){
				log.error(e)
		}
	}
}
