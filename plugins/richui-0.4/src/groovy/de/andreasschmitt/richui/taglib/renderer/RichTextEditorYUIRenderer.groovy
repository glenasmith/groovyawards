package de.andreasschmitt.richui.taglib.renderer;

import groovy.xml.MarkupBuilder
import org.codehaus.groovy.grails.web.taglib.GroovyPageTagBody

/*
*
* @author Andreas Schmitt
*/
class RichTextEditorYUIRenderer extends AbstractRenderer {

	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, GroovyPageTagBody body, MarkupBuilder builder) throws RenderException {
		builder."div"("class": "yui-skin-sam"){
			textarea(name: "${attrs?.name}", id: "${attrs?.id}", "${attrs.value}")
		}

		builder.script(type: "text/javascript"){
			if(attrs?.type == "advanced"){
				builder.yieldUnescaped "	var editor = new YAHOO.widget.Editor('${attrs?.id}', {\n"
			}
			else {
				builder.yieldUnescaped "	var editor = new YAHOO.widget.SimpleEditor('${attrs?.id}', {\n"
			}
			builder.yieldUnescaped "	    height: '${attrs?.height}px',\n"
			builder.yieldUnescaped "	    width: '${attrs?.width}px',\n"
			builder.yieldUnescaped "	    handleSubmit: true\n"
			builder.yieldUnescaped "	});\n"
			builder.yieldUnescaped "	editor.render();\n"
		}
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {			
		builder.yieldUnescaped "<!-- RichTextEditor -->"
		builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/js/yui/assets/skins/sam/skin.css")
		
		builder.script(type: "text/javascript", src: "$resourcePath/js/yui/yahoo-dom-event/yahoo-dom-event.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/yui/element/element-beta-min.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/yui/animation/animation-min.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/yui/container/container_core-min.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/yui/menu/menu-min.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/yui/button/button-min.js", "")
		if(attrs?.type == "advanced"){
			builder.script(type: "text/javascript", src: "$resourcePath/js/yui/editor/editor-beta-min.js", "")	
		}
		else {
			builder.script(type: "text/javascript", src: "$resourcePath/js/yui/editor/simpleeditor-beta-min.js", "")
		}
	}

}