import de.andreasschmitt.richui.taglib.renderer.*
import java.text.SimpleDateFormat

/*
*
* @author Andreas Schmitt
*/
class CalendarTagLib {
	static namespace = "richui"
		
	Renderer calendarRenderer
		
	def calendar = {attrs ->
			if(attrs?.format && (attrs.format == "dd.MM.yyyy" || attrs.format == "MM/dd/yyyy")){
		
			}
			else {
				attrs.format = "dd.MM.yyyy"
			}
				
			if(!attrs.value){
				attrs.value = new Date()	
			}		
				
			if(!attrs?.'class'){
				attrs.'class' = ""
			}
				
			if(!attrs?.style){
				attrs.style = ""
			}
				
			if(!attrs?.locale){
				attrs.locale = request?.locale?.language
			}
			
			//Render output
			try {
				out << calendarRenderer.renderTag(attrs)
			}
			catch(RenderException e){
				log.error(e)
			}
	}
}
