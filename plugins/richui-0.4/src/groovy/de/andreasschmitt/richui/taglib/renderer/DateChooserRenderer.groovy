package de.andreasschmitt.richui.taglib.renderer

import groovy.xml.MarkupBuilder
import org.codehaus.groovy.grails.web.taglib.GroovyPageTagBody
import java.text.SimpleDateFormat

/*
*
* @author Andreas Schmitt
*/
class DateChooserRenderer extends AbstractRenderer {
	
	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, GroovyPageTagBody body, MarkupBuilder builder) throws RenderException {
		String id = "c" + RenderUtils.getUniqueId()
		String inputId = "i" + RenderUtils.getUniqueId()

		builder.input("class": "${attrs?.'class'}", style: "${attrs?.style}", type:"text", name: "${inputId}", id: "${inputId}", value: "${attrs?.value}", "")
		builder.div("id": id, "class": "datechooser yui-skin-sam", "")
			
		builder.script(type: "text/javascript"){
			builder.yieldUnescaped "	var dateChooser = new DateChooser();\n"
			builder.yieldUnescaped "	dateChooser.setDisplayContainer(\"$id\");\n"
			builder.yieldUnescaped "	dateChooser.setInputId(\"${inputId}\");\n"
			builder.yieldUnescaped "	dateChooser.setStructId(\"${attrs?.id}\");\n"
			builder.yieldUnescaped "	dateChooser.setFormat(\"${attrs?.format}\");\n"
			if(attrs?.locale){
				builder.yieldUnescaped "	dateChooser.setLocale(\"${attrs?.locale}\");\n"
			}
			
			if(attrs?.firstDayOfWeek){
				Map days = [su: 0, mo: 1, tu: 2, we: 3, th: 4, fr: 5, sa: 6]
				
				if(days.containsKey(attrs.firstDayOfWeek.toLowerCase())){
					String day = days[attrs.firstDayOfWeek.toLowerCase()]
					builder.yieldUnescaped "	dateChooser.setFirstDayOfWeek(\"${day}\");\n"	
				}
			}
			builder.yieldUnescaped "	dateChooser.init();\n"
		}
		
		String day = ""
		String month = ""
		String year = ""
		
		if(attrs?.value != ""){
			try {
				Date date = new SimpleDateFormat(attrs.format).parse(attrs.value)
				day = "${date.date}"
				month = "${date.month + 1}"
				year = "${date.year + 1900}"
			}
			catch(Exception e){
				log.info("Error parsing date")
			}	
		}
		
		builder.input(type: "hidden", name: "${attrs?.name}", id: "${attrs?.id}", value: "struct")
		builder.input(type: "hidden", name: "${attrs?.name}_day", id: "${attrs?.id}_day", value: "${day}")
		builder.input(type: "hidden", name: "${attrs?.name}_month", id: "${attrs?.id}_month", value: "${month}")
		builder.input(type: "hidden", name: "${attrs?.name}_year", id: "${attrs?.id}_year", value: "${year}")
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {			
		builder.yieldUnescaped "<!-- DateChooser -->"
		builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/css/datechooser.css")
		builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/js/yui/calendar/assets/calendar.css")
		builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/js/yui/calendar/assets/skins/sam/calendar.css")
		builder.script(type: "text/javascript", src: "$resourcePath/js/yui/yahoo-dom-event/yahoo-dom-event.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/datechooser/datechooser.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/yui/calendar/calendar-min.js", "")
	}
}