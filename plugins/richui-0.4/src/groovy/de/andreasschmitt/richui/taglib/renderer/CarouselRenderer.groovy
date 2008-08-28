package de.andreasschmitt.richui.taglib.renderer;

import groovy.xml.MarkupBuilder
import org.codehaus.groovy.grails.web.taglib.GroovyPageTagBody

/*
*
* @author Andreas Schmitt
*/
class CarouselRenderer extends AbstractRenderer {
	
	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, GroovyPageTagBody body, MarkupBuilder builder) throws RenderException {
		String id = "c" + RenderUtils.getUniqueId()
		
		String direction = "horizontal"
		if(attrs?.direction){
			direction = attrs.direction
		}
		
		builder."div"(id: "${id}", "class": "${direction}_carousel ${attrs?.carouselClass}", style: "${attrs?.carouselStyle}"){
			"div"("class": "previous_button", ""){
			}

			"div"("class": "container ${attrs?.itemsClass}", style: "${attrs?.itemsStyle}"){
				ul(){
					builder.yieldUnescaped "${body.call()}"
				}	
			}
			
			"div"("class": "next_button", ""){
			}
		}
		
		builder.script(type: "text/javascript"){
			builder.yieldUnescaped "	    carousel = new UI.Carousel('${id}', {direction: '${direction}'});\n"
		}
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {			
		builder.yieldUnescaped "<!-- Carousel -->"		
		if(attrs?.skin){
			if(attrs.skin == "classic"){
				builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/css/carousel/classic.css")
			}
		}
		else {
			builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/css/carousel/prototype-ui.css")
		}
		
		builder.script(type: "text/javascript", src: "$resourcePath/js/carousel/prototype.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/carousel/effects.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/carousel/carousel.packed.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/carousel/prototype-ui.packed.js", "")
	}
}