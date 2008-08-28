package de.andreasschmitt.richui.taglib.renderer;

import groovy.xml.MarkupBuilder
import org.codehaus.groovy.grails.web.taglib.GroovyPageTagBody

/*
*
* @author Andreas Schmitt
*/
class FlowRenderer extends AbstractRenderer {

	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, GroovyPageTagBody body, MarkupBuilder builder) throws RenderException {		
		//Seems to be a bug in protoflow
		String id = "protoflow" //"f" + RenderUtils.getUniqueId()
		
		builder."div"(id: id){
			builder.yieldUnescaped "${body.call()}"
		}
		
		builder.script(type: "text/javascript"){
			builder.yieldUnescaped "	Event.observe(window, 'load', function() {\n"
			builder.yieldUnescaped "		new ProtoFlow(\$('${id}'), {\n"
			builder.yieldUnescaped "			startIndex: ${attrs.startIndex},\n"
			builder.yieldUnescaped "			slider: ${attrs.slider},\n"
			builder.yieldUnescaped "			captions: ${attrs.caption},\n" 
			builder.yieldUnescaped "			useReflection: ${attrs.reflection},\n"
			builder.yieldUnescaped "			enableOnClickScroll: ${attrs.onClickScroll}\n"
			builder.yieldUnescaped "		});\n"
			builder.yieldUnescaped "	});\n"
		}
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {			
		builder.yieldUnescaped "<!-- Flow -->"
		builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/js/flow/protoFlow.css")
		
		builder.script(type: "text/javascript", src: "$resourcePath/js/flow/lib/prototype.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/flow/lib/scriptaculous.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/reflection/reflection.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/flow/protoFlow.js", "")
	}

}