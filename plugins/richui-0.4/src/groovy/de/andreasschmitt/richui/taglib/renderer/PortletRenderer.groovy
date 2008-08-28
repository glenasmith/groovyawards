package de.andreasschmitt.richui.taglib.renderer

import groovy.xml.MarkupBuilder
import org.codehaus.groovy.grails.web.taglib.GroovyPageTagBody

/*
*
* @author Andreas Schmitt
*/
class PortletRenderer extends AbstractRenderer {
	
	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, GroovyPageTagBody body, MarkupBuilder builder) throws RenderException {
		
		builder."div"(){
			builder.yieldUnescaped "${body.call()}"	
		}
		
		if(!attrs?.readOnly || attrs.readOnly == "false"){
			builder.script(type: "text/javascript"){
				builder.yieldUnescaped "	var slots = [], players = [],\n"
				builder.yieldUnescaped "	YUIEvent = YAHOO.util.Event, DDM = YAHOO.util.DDM;\n"
							
				builder.yieldUnescaped "	YUIEvent.onDOMReady(function() {\n" 				
				//	slots
				int i = 0
				attrs.views.each { view ->
					builder.yieldUnescaped "		slots[${i+1}] = new YAHOO.util.DDTarget('slot_${attrs.page}_${i+1}', 'bottomslots');\n"
					builder.yieldUnescaped "		players[${i+1}] = new YAHOO.example.DDPlayer('${view}', 'bottomslots', {action: '${attrs.action}'});\n"
					builder.yieldUnescaped "	    slots[${i+1}].player = players[${i+1}];\n"
					builder.yieldUnescaped "	    players[${i+1}].slot = slots[${i+1}];\n"	
					i += 1
				}
								    
				// players
				builder.yieldUnescaped "	YUIEvent.on('ddmode', 'change', function(e) {\n"
				builder.yieldUnescaped "	YAHOO.util.DDM.mode = this.selectedIndex;\n"
				builder.yieldUnescaped "	   });\n"
				builder.yieldUnescaped "   });\n"
			}
		}
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {			
		builder.yieldUnescaped "<!-- Portlet -->"
		
		if(attrs?.skin){
			//user defined css
		}
		else {
			builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/css/portlet.css")
		}
		
		builder.script(type: "text/javascript", src: "$resourcePath/js/yui/yahoo-dom-event/yahoo-dom-event.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/yui/dragdrop/dragdrop-min.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/yui/yahoo/yahoo-min.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/yui/event/event-min.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/yui/connection/connection-min.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/portlet/portlet.js", "")
	}
}