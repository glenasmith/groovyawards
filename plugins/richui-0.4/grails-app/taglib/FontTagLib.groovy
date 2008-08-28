import de.andreasschmitt.richui.taglib.renderer.*

/*
*
* @author Andreas Schmitt
*/
class FontTagLib {
	
	static namespace = "richui"
		
	Renderer fontRenderer
	
	def font = { attrs ->
		List requiredAttributes = ["text", "fontName", "size"]
		
		requiredAttributes.each {
			if(!attrs[it]){
				throw new Exception("Attribute ${it} is required!")
			}
		}
		
		if(!attrs?.fontStyle){
			attrs.fontStyle = "plain"
		}
		
		if(!attrs?.color){
			attrs.color = "000000"
		}
		
		attrs.src = "${createLink(action: 'image', controller: 'fontImage')}"
		
		//Render output
		try {
			out << fontRenderer.renderTag(attrs)
		}
		catch(RenderException e){
			log.error(e)
		}
	}
}