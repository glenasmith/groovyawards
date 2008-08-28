package de.andreasschmitt.richui.taglib.renderer

import groovy.xml.MarkupBuilder
import org.codehaus.groovy.grails.web.taglib.GroovyPageTagBody

/*
*
* @author Andreas Schmitt
*/
class TooltipRenderer extends AbstractRenderer {
	
	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, GroovyPageTagBody body, MarkupBuilder builder) throws RenderException {			
		builder.script(type: "text/javascript"){
			builder.yieldUnescaped " var myTooltip = new YAHOO.widget.Tooltip(\"myTooltip\", { context:\"$attrs.id\" } );"
		}
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {
		builder.yieldUnescaped "<!-- Tooltip -->"
		builder.script(type: "text/javascript", src: "$resourcePath/js/yui/yahoo-dom-event/yahoo-dom-event.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/yui/container/container-min.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/yui/animation/animation-min.js", "")		
		builder.link(rel: "stylesheet", href: "$resourcePath/js/yui/container/assets/container.css", "")
	}
}