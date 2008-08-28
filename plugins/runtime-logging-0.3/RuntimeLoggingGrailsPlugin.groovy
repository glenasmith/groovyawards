class RuntimeLoggingGrailsPlugin {
    def version = 0.3
    def dependsOn = [:]
    def title = "Grails Runtime Logging Plugin"
    def author = "Jason Morris"
    def authorEmail = "jason.morris@torusit.com"
    def description = '''
RuntimeLogging plugin allows you to change the logging characteristics (e.g. Level) 
for common parts of a Grails application at runtime without the need to restart.
    '''
    def documentation = "http://grails.org/Runtime+Logging+Plugin"
    
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
        // TODO Implement registering dynamic methods to classes (optional)
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
