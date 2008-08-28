import de.andreasschmitt.richui.taglib.renderer.*

/*
*
* @author Andreas Schmitt
*/
class TagCloudTagLib {
	
	static namespace = "richui"
	
	Renderer tagCloudRenderer
	
	def tagCloud = {attrs ->				
		def attributes = ["style", "class", "action", "controller", "linkStyle", "linkClass"]
		
		//Ensure attributes are not null
		attributes.each {
			if(!attrs[it]){
				attrs[it] = ""
			}
		}
		
		if(attrs?.values && attrs.values instanceof Map){
			//Default sort order is ascending by key
			String sortOrder = "asc"
			String sortField = "key"
			
			if(attrs?.sortOrder){
				sortOrder = attrs.sortOrder == "asc" ? "asc" : "desc"
			}
			if(attrs?.sortField){
				sortField = attrs.sortField == "key" ? "key" : "value"
			}
			
			//Sort field
			if(sortField == "key"){
				attrs.values = attrs.values.entrySet().sort{it.key}
			}
			else {
				attrs.values = attrs.values.entrySet().sort{it.value}
			}
			
			if(!attrs?.minSize){
				attrs.minSize = 0
			}
			else {
				attrs.minSize = Integer.parseInt(attrs.minSize)
			}
			
			if(!attrs?.maxSize){
				attrs.maxSize = 50
			}
			else {
				attrs.maxSize = Integer.parseInt(attrs.maxSize)
			}
			
			if(!attrs?.showNumber){
				attrs.showNumber = false
			}
			else {
				attrs.showNumber = new Boolean(attrs.showNumber)
			}
			
			//Sort order
			if(sortOrder == "desc"){
				attrs.values = attrs.values.reverse()
			}
			
			if(attrs?.controller){
				attrs.action = "${createLink(action: attrs.action, controller: attrs.controller)}"
			}
			
			//Render output
			try {
				out << tagCloudRenderer.renderTag(attrs)
			}
			catch(RenderException e){
				log.error(e)
			}	
		}
	}

}