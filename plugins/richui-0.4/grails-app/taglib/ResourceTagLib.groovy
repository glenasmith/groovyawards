import de.andreasschmitt.richui.taglib.renderer.*

/*
*
* @author Andreas Schmitt
*/
class ResourceTagLib {
	
	static namespace = "resource"
	Renderer accordionRenderer
	Renderer autoCompleteRenderer
	Renderer dateChooserRenderer
	Renderer calendarRenderer
	Renderer carouselRenderer
	Renderer flowRenderer
	Renderer mapRenderer
	Renderer portletRenderer
	Renderer portletViewRenderer
	Renderer ratingRenderer
	Renderer tabViewRenderer
	Renderer tagCloudRenderer
	Renderer timelineRenderer
	Renderer tooltipRenderer
	Renderer treeViewRenderer
	Renderer reflectionImageRenderer
	Renderer richTextEditorRenderer
	
	def autoComplete = { attrs ->	
		//Render output
		try {
			out << autoCompleteRenderer.renderResources(attrs, request?.contextPath)
		}
		catch(RenderException e){
			log.error(e)
		}	
	}
	
	def calendar = { attrs ->
		//Render output
		try {
			out << calendarRenderer.renderResources(attrs, request?.contextPath)
		}
		catch(RenderException e){
			log.error(e)
		}
	}
	
	def carousel = { attrs ->
		//Render output
		try {
			out << carouselRenderer.renderResources(attrs, request?.contextPath)
		}
		catch(RenderException e){
			log.error(e)
		}
	}
	
	def dateChooser = {	attrs ->
		//Render output
		try {
			out << dateChooserRenderer.renderResources(attrs, request?.contextPath)
		}
		catch(RenderException e){
			log.error(e)
		}
	}
	
	def googlemaps = { attrs ->
		if(!attrs?.key){
			throw new Exception("Attribute key is required")
		}
		
		if(!attrs?.version){
			attrs.version = "2.x"
		}
	
		//Render output
		try {
			out << mapRenderer.renderResources(attrs, request?.contextPath)
		}
		catch(RenderException e){
			log.error(e)
		}
	}
	
	def rating = { attrs ->
		//Render output
		try {
			out << ratingRenderer.renderResources(attrs, request?.contextPath)
		}
		catch(RenderException e){
			log.error(e)
		}
	}
	
	def tabView = { attrs ->
		//Render output
		try {
			out << tabViewRenderer.renderResources(attrs, request?.contextPath)
		}
		catch(RenderException e){
			log.error(e)
		}
	}
	
	def tagCloud = { attrs ->
		//Render output
		try {
			out << tagCloudRenderer.renderResources(attrs, request?.contextPath)
		}
		catch(RenderException e){
			log.error(e)
		}		
	}
	
	def timeline = { attrs ->
		//Render output
		try {
			out << timelineRenderer.renderResources(attrs, request?.contextPath)
		}
		catch(RenderException e){
			log.error(e)
		}		
	}
	
	def tooltip = { attrs ->
		//Render output
		try {
			out << tooltipRenderer.renderResources(attrs, request?.contextPath)
		}
		catch(RenderException e){
			log.error(e)
		}		
	}
	
	def treeView = { attrs ->	
		//Render output
		try {
			out << treeViewRenderer.renderResources(attrs, request?.contextPath)
		}
		catch(RenderException e){
			log.error(e)
		}
	}
	
	def reflectionImage = { attrs ->
		//Render output
		try {
			out << reflectionImageRenderer.renderResources(attrs, request?.contextPath)
		}
		catch(RenderException e){
			log.error(e)
		}	
	}
	
	def richTextEditor = { attrs ->
		//Render output
		try {
			out << richTextEditorRenderer.renderResources(attrs, request?.contextPath)
		}
		catch(RenderException e){
			log.error(e)
		}	
	}
	
	def portlet = { attrs ->
		//Render output
		try {
			out << portletRenderer.renderResources(attrs, request?.contextPath)
		}
		catch(RenderException e){
			log.error(e)
		}	
	}
	
	def portletView = { attrs ->
		//Render output
		try {
			out << portletViewRenderer.renderResources(attrs, request?.contextPath)
		}
		catch(RenderException e){
			log.error(e)
		}	
	}
	
	def flow = { attrs ->
		//Render output
		try {
			out << flowRenderer.renderResources(attrs, request?.contextPath)
		}
		catch(RenderException e){
			log.error(e)
		}	
	}
	
	def accordion = { attrs ->
		//Render output
		try {
			out << accordionRenderer.renderResources(attrs, request?.contextPath)
		}
		catch(RenderException e){
			log.error(e)
		}	
	}

}