package de.andreasschmitt.richui.taglib.renderer

import groovy.xml.MarkupBuilder
import org.codehaus.groovy.grails.web.taglib.GroovyPageTagBody

/*
 * @author Andreas Schmitt
 */
class TabLabelRenderer extends AbstractRenderer {

	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, GroovyPageTagBody body, MarkupBuilder builder) throws RenderException {			
		String uniqueId = RenderUtils.getUniqueId()
			
		builder.li("class": (attrs?.selected == "true") ? "selected" : ""){
			a("href": "#tab${uniqueId}"){
				em("${attrs?.title}")
			}
		}
	}
		
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {

	}	
}