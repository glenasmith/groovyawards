package de.andreasschmitt.richui.taglib.renderer

import groovy.xml.MarkupBuilder
import org.codehaus.groovy.grails.web.taglib.GroovyPageTagBody

/*
*
* @author Andreas Schmitt
*/
class TreeViewRenderer extends AbstractRenderer {
	
	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, GroovyPageTagBody body, MarkupBuilder builder) throws RenderException {			
		if(!attrs?.id){
			attrs.id = "tree" + RenderUtils.getUniqueId()
		}
		
		builder.div(id: attrs?.id, "")					
		builder.script(type: "text/javascript"){  		 
		    builder.yieldUnescaped "	var tree = new YAHOO.widget.TreeView(\"$attrs.id\");\n"
		    builder.yieldUnescaped "	var root = tree.getRoot();\n"		    	
		    builder.yieldUnescaped "	function createNode(text, id, pnode){\n"
		    builder.yieldUnescaped "		var n = new YAHOO.widget.TextNode(text, pnode, false);\n"
		    if(attrs?.onLabelClick){
		    	builder.yieldUnescaped "		n.additionalId = id;\n"
		    }
		    builder.yieldUnescaped "		return n;\n"
		    builder.yieldUnescaped "	}\n\n"
	
		    if(attrs?.onLabelClick){
			    builder.yieldUnescaped "	tree.subscribe(\"labelClick\", function(node) {\n"
			    builder.yieldUnescaped "		var id = node.additionalId;"
			    builder.yieldUnescaped "		${attrs.onLabelClick}"
			    builder.yieldUnescaped "	});\n"
		    }
		    	
		    createTree(attrs.xml, "root", builder)
				
			builder.yieldUnescaped "	tree.draw();\n"
		}
	}
	
	private void createTree(nodes, parent, builder){
		nodes.each {
			//leaf
			if(it.children().size() == 0){
				builder.yieldUnescaped "	createNode(\"" + it.@name + "\", \"" + it?.@id + "\", $parent);\n"
			}
			//knot
			else {
				def nodeName = it.@name
				if(it.@name == ""){
					nodeName = "unknown"
				}
				
				def newParent = "t" + RenderUtils.getUniqueId()
				builder.yieldUnescaped "	" + newParent + " = createNode(\"" + nodeName + "\", \"" + it?.@id + "\", $parent);\n"
				createTree(it.children(), newParent, builder)
			}
		}
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {
		builder.yieldUnescaped "<!-- TreeView -->"
		
		
		if(attrs?.skin){
			if(attrs.skin == "default"){
				builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/css/treeView.css")
			}
		}
		else {
			builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/js/yui/treeview/assets/skins/sam/treeview.css")
		}
			
		builder.script(type: "text/javascript", src: "$resourcePath/js/yui/yahoo-dom-event/yahoo-dom-event.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/yui/event/event-min.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/yui/yahoo/yahoo-min.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/yui/treeview/treeview-min.js", "")
	}


}