import de.andreasschmitt.richui.taglib.renderer.*
import java.text.SimpleDateFormat

/*
*
* @author Andreas Schmitt
*/
class TimelineTagLib {
	
	static namespace = "richui"
	
	Renderer timelineRenderer
	
	def timeline = { attrs ->
	
		if(!attrs?.style){
			attrs.style = "" 
		}
		if(!attrs?.'class'){
			attrs.'class' = ""
		}
		
		if(!attrs?.startDate){
			attrs.startDate = new SimpleDateFormat("MMM dd yyyy HH:mm:ss", Locale.US).format(new Date()) + " GMT"
		}
		else {
			attrs.startDate = new SimpleDateFormat("MMM dd yyyy HH:mm:ss", Locale.US).format(attrs.startDate) + " GMT"
		}
		
		//Render output
		try {
			out << timelineRenderer.renderTag(attrs)
		}
		catch(RenderException e){
			log.error(e)
		}
	}

}