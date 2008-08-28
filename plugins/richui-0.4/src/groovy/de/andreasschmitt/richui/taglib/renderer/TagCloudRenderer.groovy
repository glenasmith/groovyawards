package de.andreasschmitt.richui.taglib.renderer

import groovy.xml.MarkupBuilder
import java.text.SimpleDateFormat
import org.codehaus.groovy.grails.web.taglib.GroovyPageTagBody

/*
*
* @author Andreas Schmitt
*/
class TagCloudRenderer extends AbstractRenderer {

	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, GroovyPageTagBody body, MarkupBuilder builder) throws RenderException {
		String tagForm = RenderUtils.getUniqueId()
		String selectedTag = RenderUtils.getUniqueId()			
			
		builder."div"("class": attrs?.'class', style: attrs?.style){
			form(id:"$tagForm", action:"${attrs?.action}", method:"post"){
				if(attrs?.params){
					attrs.params.each { key, value ->
						input(id: "$key", name:"$key", type:"hidden", "value": "$value")
					}
				}
				
				input(id: "$selectedTag", name:"selectedTag", type:"hidden")
				attrs?.values.each { entry ->					
					int fontSize = entry.value + attrs?.minSize
							
					if(attrs?.maxSize){
						if(fontSize > attrs.maxSize){
							fontSize = attrs.maxSize
						}
					}
					
					a(href: "#", "class":  attrs?.linkClass, style: "font-size: ${fontSize}pt; ${attrs?.linkStyle}", onclick: 'javascript: document.getElementById("' + selectedTag + '").value = "' + entry.key + '"; document.getElementById("' + tagForm + '").submit();', "${entry.key}")
					if(attrs?.showNumber){
						span(" (${entry.value})")
					}
				}
			}
		}
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {

	}

}