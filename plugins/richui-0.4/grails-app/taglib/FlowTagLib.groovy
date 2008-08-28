import de.andreasschmitt.richui.taglib.renderer.*
/*
*
* @author Andreas Schmitt
*/
class FlowTagLib {
	
	static namespace = "richui"
		
	Renderer flowRenderer
		
	def flow = {attrs, body ->
		
		if(!attrs?.caption){
			attrs.caption = "false"
		}
			
		if(!attrs?.reflection){
			attrs.reflection = "true"
		}
			
		if(!attrs?.onClickScroll){
			attrs.onClickScroll = "true"
		}
		
		if(!attrs?.startIndex){
			attrs.startIndex = "2"
		}
			
		if(!attrs?.slider){
			attrs.slider = "true"
		}
				
		//Render output
		try {
			out << flowRenderer.renderTag(attrs, body)
		}
		catch(RenderException e){
			log.error(e)
		}
	}
}
