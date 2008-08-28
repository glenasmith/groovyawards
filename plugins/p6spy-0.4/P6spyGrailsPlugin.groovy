
class P6spyGrailsPlugin {
	def version = 0.4
	def dependsOn = [:]
	
	def author = "Paul Rule"
	def authorEmail="paul@javathinking.com"
	def title="This plugin adds p6spy to your application"
	def description='''\
P6Spy plugin adds the p6spy library to your Grails application.
P6Spy lets you monitor the JDBC queries by proxying your database driver.
In addition to logging the prepared statements, it also logs the sql with
parameters in place so you can copy and paste the exact sql into your favourite
database client to test the results.

Visit the p6spy website at http://p6spy.com/
	'''
	
	def doWithSpring = {
		// TODO Implement runtime spring config (optional)
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
