import de.andreasschmitt.richui.taglib.renderer.*

/*
*
* @author Andreas Schmitt
*/
class ProgressBarTagLib {
	
	static namespace = "richui"
		
	Renderer progressBarRenderer

	def progressBar = { attrs ->
		
		if(!attrs?.'class'){
			attrs.'class' = ""
		}
		
		if(!attrs?.style){
			attrs.style = ""
		}
		
		if(!attrs?.progressClass){
			attrs.progressClass = ""
		}
		
		if(!attrs?.progressStyle){
			attrs.progressStyle = ""
		}
		
		if(!attrs?.value){
			attrs.value = 0
		}
	
		//Render output
		try {
			out << progressBarRenderer.renderTag(attrs)
		}
		catch(RenderException e){
			log.error(e)
		}
	}
}