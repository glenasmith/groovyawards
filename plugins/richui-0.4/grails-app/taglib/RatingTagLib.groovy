import de.andreasschmitt.richui.taglib.renderer.*

/*
*
* @author Andreas Schmitt
*/
class RatingTagLib {
	
	static namespace = "richui"
	
	Renderer ratingRenderer
	
	def rating = {attrs ->	
		attrs.link = "${remoteLink(class: ':class:', title: ':title:', controller: attrs.controller, action: attrs.action, id: attrs?.id, update: 'update', params:[rating: ':rating:'], 'number')}"
		
		println attrs.link
		
		//Default static behaviour
		if(!attrs?.dynamic){
			attrs.dynamic = false
		}
		else {
			attrs.dynamic = attrs.dynamic == "true" ? true : false
		}
		
		if(attrs.dynamic && (!attrs?.action || !attrs?.controller)){
			throw new Exception("Attributes action is required when dynamic is true")
		}
		
		//Default don't show current rating text
		if(!attrs?.showCurrent){
			attrs.showCurrent = false
		}
		else {
			attrs.showCurrent = attrs.showCurrent == "true" ? true : false
		}
		
		//Default 5 units eg. stars
		if(!attrs?.units){
			attrs.units = 5
		}
		else {
			attrs.units = Integer.parseInt(attrs.units)
		}
		
		//Default no rating
		if(!attrs?.rating){
			attrs.rating = 0
		}
		else {
			try {
				attrs.rating = Double.parseDouble("${attrs.rating}")	
			}
			catch(NumberFormatException e){
				attrs.rating = 0
			}
		}
		
		//Render output
		try {
			out << ratingRenderer.renderTag(attrs)
		}
		catch(RenderException e){
			log.error(e)
		}
	}
}