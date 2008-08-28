import org.apache.commons.logging.LogFactory
import org.springframework.web.context.support.*
import org.springframework.core.io.*
import org.springframework.core.io.support.*
import org.codehaus.groovy.grails.plugins.PluginManagerHolder

/*
*
* @author Andreas Schmitt
*/
class RichuiGrailsPlugin {
	
	def version = 0.4
    def author = 'Andreas Schmitt'
    def authorEmail = 'andreas.schmitt.mi@gmail.com'
    def title = 'Provides a set of AJAX components'
    def description = '''
Provides a set of AJAX components
'''
    def documentation = 'http://grails.org/RichUI+Plugin'
	
	def dependsOn = [:]
	
	def doWithSpring = {
		//This is only necessary here, because later on log is injected by Spring
		def log = LogFactory.getLog(RichuiGrailsPlugin)
		 
		try {				
			RichuiConfig.renderers.each { key, value ->
		  		try {
		      		Class clazz = Class.forName(value, true, new GroovyClassLoader())
		      			
		      		//Add to spring
		      		"$key"(clazz)	
		  		}
		  		catch(ClassNotFoundException e){
		  			log.error("Couldn't find class: ${value}", e)
		  		}
			}			
		}
		catch(Exception e){
			log.error("Error initializing RichUI plugin", e)
		}
		catch(Error e){
			//Strange error which happens when using generate-all and hibernate.cfg
			log.error("Error initializing RichUI plugin")
		}
	}   
	
	def doWithApplicationContext = { applicationContext ->
		// TODO Implement post initialization spring config (optional)		
	}
	
	def doWithWebDescriptor = { xml ->
		// TODO Implement additions to web.xml (optional)
	}	                                      
	
	def doWithDynamicMethods = { ctx ->
		// TODO Implement additions to web.xml (optional)
	}	
	
	def onChange = { event ->
		// TODO Implement code that is executed when this class plugin class is changed  
		// the event contains: event.application and event.applicationContext objects
	}                                                                                  
	
	def onApplicationChange = { event ->
		// TODO Implement code that is executed when any class in a GrailsApplication changes
		// the event contain: event.source, event.application and event.applicationContext objects
	}
}