package de.andreasschmitt.richui.taglib.renderer

import groovy.xml.MarkupBuilder
import org.codehaus.groovy.grails.web.taglib.GroovyPageTagBody

/*
 * @author Andreas Schmitt
 */
class TabViewRenderer extends AbstractRenderer {

	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, GroovyPageTagBody body, MarkupBuilder builder) throws RenderException {			
		builder.script(type: "text/javascript"){
			builder.yieldUnescaped "	var tabView = new YAHOO.widget.TabView(\"${attrs.id}\");\n"
		
			if(attrs?.event){
				builder.yieldUnescaped "	function handleClick(e) {"   
				builder.yieldUnescaped "        ${attrs?.event};" 
				builder.yieldUnescaped "    } "
				builder.yieldUnescaped "    tabView.addListener('activeTabChange', handleClick);"
			}
		}
			
		builder."div"("class": "yui-skin-sam"){
			"div"("class": "yui-navset yui-navset-top", "id": attrs?.id){				
				builder.yieldUnescaped "${body.call()}"
			}	
		}
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {
		builder.yieldUnescaped "<!-- TabView -->"
		
		if(attrs?.skin){
			if(attrs.skin == "default"){
				builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/js/yui/fonts/fonts-min.css")
				builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/js/yui/tabview/assets/tabview-core.css")
				builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/css/tabView.css")
			}
		}
		else {
			builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/js/yui/tabview/assets/skins/sam/tabview.css")
		}
		
		
		builder.script(type: "text/javascript", src: "$resourcePath/js/yui/yahoo-dom-event/yahoo-dom-event.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/yui/element/element-beta-min.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/yui/tabview/tabview-min.js", "")			
	}

}