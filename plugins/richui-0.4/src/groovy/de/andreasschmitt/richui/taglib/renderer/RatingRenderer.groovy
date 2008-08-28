package de.andreasschmitt.richui.taglib.renderer

import groovy.xml.MarkupBuilder
import org.codehaus.groovy.grails.web.taglib.GroovyPageTagBody

/*
*
* @author Andreas Schmitt
*/
class RatingRenderer extends AbstractRenderer {
	
	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, GroovyPageTagBody body, MarkupBuilder builder) throws RenderException {
		String id = "r" + RenderUtils.getUniqueId()
		int imageWidth = 30
		int currentRating = attrs?.rating * imageWidth
		int units = attrs?.units * imageWidth
		
		builder.div(id: "${id}"){
			div("class": "ratingblock"){
				div(id: "unit_long$id"){
					if(attrs?.dynamic){
						ul(id: "unit_ul$id", "class": "unit-rating", style: "width: ${units}px"){
							li("class": "current-rating", style:  "width: ${currentRating}px;", "Currently $attrs.rating")
							for(i in 1..attrs.units){
								li(){
									String link = attrs.link.replace(":class:", "r${i}-unit rater").replace(":title:", "$i").replace("update", "${id}").replace("number", "$i").replace("%3Arating%3A", "${i}")
									builder.yieldUnescaped "$link"
								}
							}
						}
					}
					else {
						ul(id: "unit_ul$id", "class": "unit-rating", style: "width: ${units}px"){
							li("class": "current-rating", style:  "width: ${currentRating}px;", "Currently $attrs.rating")
						}
					}
					
					if(attrs.showCurrent){
						p("class": "static"){
							strong(attrs?.rating)
						}
					}
				}
			}
		}
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {			
		builder.yieldUnescaped "<!-- Rating -->"
		builder.script(type: "text/javascript", src: "$resourcePath/js/prototype/prototype.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/rating/behavior.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/rating/rating.js", "")
		//builder.link(rel: "stylesheet", href: "$resourcePath/css/rating.css", "")
		builder.link(rel: "stylesheet", href: "$resourcePath/css//rating.css", "")
	}
}