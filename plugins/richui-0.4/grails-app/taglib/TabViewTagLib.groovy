import de.andreasschmitt.richui.taglib.renderer.*

/*
*
* @author Andreas Schmitt
*/
class TabViewTagLib {
	
	static namespace = "richui"
	
	Renderer tabViewRenderer
	Renderer tabLabelsRenderer
	Renderer tabLabelRenderer
	Renderer tabContentsRenderer
	Renderer tabContentRenderer
	
	def tabView = {attrs, body ->	
		//Render output
		try {
			out << tabViewRenderer.renderTag(attrs, body)
		}
		catch(RenderException e){
			log.error(e)
		}	
	}
	
	def tabLabels = { attrs, body ->
		//Render output
		try {			
			out << tabLabelsRenderer.renderTag(attrs, body)
		}
		catch(RenderException e){
			log.error(e)
		}	
	}
	
	def tabContents = { attrs, body ->
		//Render output
		try {			
			out << tabContentsRenderer.renderTag(attrs, body)
		}
		catch(RenderException e){
			log.error(e)
		}		
	}
	
	def tabContent = { attrs, body ->
		//Render output
		try {			
			out << tabContentRenderer.renderTag(attrs, body)
		}
		catch(RenderException e){
			log.error(e)
		}	
	}
	
	def tabLabel = { attrs ->
		//Render output
		try {			
			out << tabLabelRenderer.renderTag(attrs)
		}
		catch(RenderException e){
			log.error(e)
		}	
	}
}