package de.andreasschmitt.richui.taglib.renderer;

import groovy.xml.MarkupBuilder
import org.codehaus.groovy.grails.web.taglib.GroovyPageTagBody

/*
*
* @author Andreas Schmitt
*/
class AccordionRenderer extends AbstractRenderer {
	
	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, GroovyPageTagBody body, MarkupBuilder builder) throws RenderException {
		builder."dl"("class": "accordion-menu ${attrs?.class}", style: "${attrs?.style}"){
			builder.yieldUnescaped "${body.call()}"	
		}
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {			
		builder.yieldUnescaped "<!-- Accordion -->"
		
		if(attrs?.skin){
			if(attrs.skin == "default"){
				builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/css/accordion.css")
			}
		}
		else {
			builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/css/accordion.css")
		}
		
		builder.script(type: "text/javascript", src: "$resourcePath/js/yui/yahoo-dom-event/yahoo-dom-event.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/yui/connection/connection-min.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/yui/animation/animation-min.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/accordion/accordion-menu-v2.js", "")
	}

}