import de.andreasschmitt.richui.taglib.renderer.*

/*
*
* @author Andreas Schmitt
*/
class RichTextEditorTagLib {
	static namespace = "richui"
		
	Renderer richTextEditorRenderer
			
	def richTextEditor = {attrs ->
		if(attrs?.name){
			
			if(!attrs.id){
				attrs.id = attrs.name
			}
			
			if(!attrs?.height){
				attrs.height = "200"
			}
			
			if(!attrs?.width){
				attrs.width = "300"
			}
			
			if(!attrs?.value){
				attrs.value = ""
			}			
			
			//Render output
			try {
				out << richTextEditorRenderer.renderTag(attrs)
			}
			catch(RenderException e){
				log.error(e)
			}
		}
		else {
			throw new Exception("Attribute name is required")
		}
	}
}
