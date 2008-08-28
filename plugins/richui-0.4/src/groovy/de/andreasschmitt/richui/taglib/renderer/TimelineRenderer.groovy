package de.andreasschmitt.richui.taglib.renderer

import groovy.xml.MarkupBuilder
import org.codehaus.groovy.grails.web.taglib.GroovyPageTagBody

/*
*
* @author Andreas Schmitt
*/
class TimelineRenderer extends AbstractRenderer {
	
	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, GroovyPageTagBody body, MarkupBuilder builder) throws RenderException {
		String id = "t" + RenderUtils.getUniqueId()
			
		builder.div("class": attrs?.'class', style: attrs?.style, "id": id, ""){}
					
		builder.script(type: "text/javascript"){
			builder.yieldUnescaped "	var tl;\n"
			builder.yieldUnescaped "	function initTimeline() {\n"
			builder.yieldUnescaped "		var eventSource = new Timeline.DefaultEventSource();\n"
			builder.yieldUnescaped "		var bandInfos = [\n"
			builder.yieldUnescaped "		Timeline.createBandInfo({\n"
			builder.yieldUnescaped "			eventSource:    eventSource,\n"
				
			builder.yieldUnescaped "			date:           '$attrs.startDate',\n"
			
			if(attrs?.eventBandWidth){
				builder.yieldUnescaped "			width:          '${attrs.eventBandWidth}%', \n"
			}
			else {
				builder.yieldUnescaped "			width:          '70%', \n"	
			}
			
			Map intervalUnits = [day: "Timeline.DateTime.DAY", week: "Timeline.DateTime.WEEK", month: "Timeline.DateTime.MONTH",
			                     quarter: "Timeline.DateTime.QUARTER", year: "Timeline.DateTime.YEAR"]
			
			String eventIntervalUnit = ""
			if(attrs?.eventIntervalUnit && intervalUnits.containsKey(attrs.eventIntervalUnit.toLowerCase())){
				eventIntervalUnit = intervalUnits[attrs.eventIntervalUnit.toLowerCase()]
			}
			else {
				eventIntervalUnit = intervalUnits["month"]
			}
			
			builder.yieldUnescaped "			intervalUnit:   ${eventIntervalUnit}, \n"
			if(attrs?.eventIntervalPixels){
				builder.yieldUnescaped "			intervalPixels: ${attrs.eventIntervalPixels}\n"
			}
			else {
				builder.yieldUnescaped "			intervalPixels: 100\n"	
			}
			builder.yieldUnescaped "		}),\n"

			builder.yieldUnescaped "		Timeline.createBandInfo({\n"
			builder.yieldUnescaped "		    showEventText:  false,\n"
			builder.yieldUnescaped "			trackHeight:    0.5,\n"
			builder.yieldUnescaped "			trackGap:       0.2,\n"
			builder.yieldUnescaped "			eventSource:    eventSource,\n"
				
			builder.yieldUnescaped "			date:           '$attrs.startDate',\n"
			
			if(attrs?.legendBandWidth){
				builder.yieldUnescaped "			width:          '${attrs.legendBandWidth}%',\n"
			}
			else {
				builder.yieldUnescaped "			width:          '30%',\n" 	
			}
			
			String legendIntervalUnit = ""
			if(attrs?.legendIntervalUnit && intervalUnits.containsKey(attrs.legendIntervalUnit.toLowerCase())){
				legendIntervalUnit = intervalUnits[attrs.legendIntervalUnit.toLowerCase()]
			}
			else {
				legendIntervalUnit = intervalUnits["year"]
			}
			
			builder.yieldUnescaped "			intervalUnit:   ${legendIntervalUnit}, \n"
			
			if(attrs?.legendIntervalPixels){
				builder.yieldUnescaped "			intervalPixels: ${attrs.legendIntervalPixels}\n"
			}
			else {
				builder.yieldUnescaped "			intervalPixels: 200\n"	
			}
			
			builder.yieldUnescaped "		})\n"
			builder.yieldUnescaped "		];\n"

			builder.yieldUnescaped "		bandInfos[1].syncWith = 0;\n"
			builder.yieldUnescaped "		bandInfos[1].highlight = true;\n"

			builder.yieldUnescaped "		tl = Timeline.create(document.getElementById('$id'), bandInfos);\n"
				
			if(attrs?.datasource) {
				builder.yieldUnescaped "		tl.loadXML('$attrs.datasource', function(xml, url) { eventSource.loadXML(xml, url); });\n"
			}
			builder.yieldUnescaped "}\n"

			builder.yieldUnescaped "var resizeTimerID = null;\n"
			builder.yieldUnescaped "function onResize() {\n"
			builder.yieldUnescaped "	if (resizeTimerID == null) {\n"
			builder.yieldUnescaped "		resizeTimerID = window.setTimeout(function() {\n"
			builder.yieldUnescaped "		resizeTimerID = null;\n"
			builder.yieldUnescaped "		tl.layout();\n"
			builder.yieldUnescaped "	}, 500);\n"
			builder.yieldUnescaped "}\n"
			builder.yieldUnescaped "}\n"
		}
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {		
		builder.script(type: "text/javascript", src: "$resourcePath/js/simile/simile-ajax-api.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/timeline/timeline-api.js", "")
	}

}