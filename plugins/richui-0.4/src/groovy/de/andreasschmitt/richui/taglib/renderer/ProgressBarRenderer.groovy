package de.andreasschmitt.richui.taglib.renderer;

import groovy.xml.MarkupBuilder
import org.codehaus.groovy.grails.web.taglib.GroovyPageTagBody

/*
*
* @author Andreas Schmitt
*/
class ProgressBarRenderer extends AbstractRenderer {

	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, GroovyPageTagBody body, MarkupBuilder builder) throws RenderException {		
		builder."div"("class": attrs?.'class', style: "width: 100px; ${attrs?.style}", title: attrs?.value){
			"div"("class": attrs?.progressClass, style: "width: ${attrs?.value}px; ${attrs?.progressStyle}"){
				builder.yieldUnescaped "&nbsp;"
			}
		}
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {			
		builder.yieldUnescaped "<!-- ProgressBar -->"
	}

}