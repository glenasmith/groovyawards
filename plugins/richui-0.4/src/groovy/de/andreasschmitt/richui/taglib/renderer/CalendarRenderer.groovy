package de.andreasschmitt.richui.taglib.renderer

import groovy.xml.MarkupBuilder
import org.codehaus.groovy.grails.web.taglib.GroovyPageTagBody
import java.text.SimpleDateFormat

/*
*
* @author Andreas Schmitt
*/
class CalendarRenderer extends AbstractRenderer {

	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, GroovyPageTagBody body, MarkupBuilder builder) throws RenderException {
		String id = "c" + RenderUtils.getUniqueId()

		builder.div("class": "yui-skin-sam"){
			"div"("id": id, "class": "calendar", "")
		}

		String month = new SimpleDateFormat("MM/yyyy").format(new Date())
		switch(attrs?.format){
			case "dd.MM.yyyy":
				month = new SimpleDateFormat("MM.yyyy").format(attrs?.value)
				break
				
			case "MM/dd/yyyy":
				month = new SimpleDateFormat("MM/yyyy").format(attrs?.value)
				break
		}
		

		builder.script(type: "text/javascript"){
			builder.yieldUnescaped "	var cal = new Calendar();\n"
			builder.yieldUnescaped "	cal.setDisplayContainer(\"$id\");\n"
			builder.yieldUnescaped "	cal.setFormat(\"${attrs?.format}\");\n"
			builder.yieldUnescaped "	cal.setMonth(\"${month}\");\n"
			//builder.yieldUnescaped "	cal.setSelectedDates(\"${attrs?.format}\");\n"
			if(attrs?.locale){
				builder.yieldUnescaped "	cal.setLocale(\"${attrs?.locale}\");\n"
			}
			builder.yieldUnescaped "	cal.init();\n"
		}
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {			
		builder.yieldUnescaped "<!-- Calendar -->"
		builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/js/yui/calendar/assets/skins/sam/calendar.css")
		
		if(!attrs.skin || attrs.skin == "default"){
			builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/css/calendar.css")	
		}
		
		builder.script(type: "text/javascript", src: "$resourcePath/js/yui/yahoo-dom-event/yahoo-dom-event.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/calendar/calendar.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/yui/calendar/calendar-min.js", "")
	}

}