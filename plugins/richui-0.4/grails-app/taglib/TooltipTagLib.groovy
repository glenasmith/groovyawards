import de.andreasschmitt.richui.taglib.renderer.*

/*
*
* @author Andreas Schmitt
*/
class TooltipTagLib {
	
	static namespace = "richui"
	
	Renderer tooltipRenderer
	
	def tooltip = {attrs ->		
		//Render output
		try {
			out << tooltipRenderer.renderTag(attrs)
		}
		catch(RenderException e){
			log.error(e)
		}
	}

}