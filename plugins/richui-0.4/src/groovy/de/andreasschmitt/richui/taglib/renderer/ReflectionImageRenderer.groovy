package de.andreasschmitt.richui.taglib.renderer

import groovy.xml.MarkupBuilder
import org.codehaus.groovy.grails.web.taglib.GroovyPageTagBody

/*
*
* @author Andreas Schmitt
*/
class ReflectionImageRenderer extends AbstractRenderer {

	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, GroovyPageTagBody body, MarkupBuilder builder) throws RenderException {
		String resultId = "a" + RenderUtils.getUniqueId()
		
		String clazz = "reflect rheight${attrs?.reflectionHeight} ropacity${attrs?.reflectionOpacity}"
		if(attrs?."class"){
			clazz += " ${attrs.'class'}"
		}
		
		attrs.put("class", clazz)
		attrs.remove("reflectionHeight")
		attrs.remove("reflectionOpacity")
		
		builder.img(attrs)
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {			
		builder.yieldUnescaped "<!-- Reflection Image -->"		
		builder.script(type: "text/javascript", src: "$resourcePath/js/reflection/reflection.js", "")
	}

}