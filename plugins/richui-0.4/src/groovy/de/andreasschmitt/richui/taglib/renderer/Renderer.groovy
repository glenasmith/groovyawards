package de.andreasschmitt.richui.taglib.renderer

import org.codehaus.groovy.grails.web.taglib.GroovyPageTagBody

/*
*
* @author Andreas Schmitt
*/

interface Renderer {
	
	String renderResources(Map attrs, String contextPath) throws RenderException
	String renderTag(Map attrs) throws RenderException
	String renderTag(Map attrs, GroovyPageTagBody body) throws RenderException

}