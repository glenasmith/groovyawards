import groovy.util.XmlSlurper
import de.andreasschmitt.richui.taglib.renderer.*

/*
*
* @author Andreas Schmitt
*/
class TreeViewTagLib {
	
	static namespace = "richui"
	
	Renderer treeViewRenderer
	
	def treeView = {attrs ->
		try {
			attrs.xml = new XmlSlurper().parseText(attrs.xml)
		}
		catch(Exception e){
			log.error("Error parsing xml", e)
			return ""
		}
	
		//Render output
		try {
			out << treeViewRenderer.renderTag(attrs)
		}
		catch(RenderException e){
			log.error(e)
		}	

	}

}