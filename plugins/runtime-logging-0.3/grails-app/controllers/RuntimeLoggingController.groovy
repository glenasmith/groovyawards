import org.apache.log4j.*

/**
 * Grails Plugin to control log4j logging at runtime, and to simplify persistent
 * configuration though Config.groovy
 *
 * @author Jason Morris (jason.morris@torusit.com)
 */
class RuntimeLoggingController
{
    void addCurrentLevelToLoggerMapList(List loggerMapList) {
        loggerMapList.each {
            it.name = "${it.name} - ${Logger.getLogger(it.logger).getLevel()}"
        }
    }
    
    String classCase(s)
    {
    	// Effectively ucFirst() i.e. first letter of artefacts must be upper case
    	if(!s)
    		return s;
    		
    	def head = s[0].toUpperCase()
    	def tail = (s.length() > 1 ? s.substring(1) : "")
    		
    	return "${head}${tail}"
    }
    
    String loggerName(name,artefactType)
    {
    	// Domains just use the artefact name, controllers/services etc need "Controller"/"Service" etc appended
    	return (artefactType.toLowerCase() == "domain") ? "${classCase(name)}" : "${classCase(name)}${artefactType}"
    }

    List buildArtefactLoggerMapList(String artefactType) {
        def artefacts = grailsApplication.getArtefacts(artefactType)
        List artefactList = []
        artefacts.each { artefactList << it }
        // sort the artefacts into alphabetical order
        Collections.sort(artefactList, new GrailsArtefactComparator())
        def logMapList = []
        artefactList.each {
            def artefactLoggerMap = [name: it.fullName, logger: "grails.app.${artefactType.toLowerCase()}.${loggerName(it.logicalPropertyName,artefactType)}"]
            logMapList += artefactLoggerMap
        }
        return logMapList
    }

    static final List grailsLogs = [
            [name: 'Grails Application', logger: 'grails.app'],
            [name: 'Controllers', logger: 'grails.app.controller'],
            [name: 'Services', logger: 'grails.app.service'],
            [name: 'Domains', logger: 'grails.app.domain'],
            [name: 'Filters', logger: 'grails.app.filters'],
            [name: 'TagLibs', logger: 'grails.app.taglib'],
            [name: 'Grails Web Requests', logger: 'org.codehaus.groovy.grails.web'],
            [name: 'URL Mappings', logger: 'org.codehaus.groovy.grails.web.mapping'],
            [name: 'Plugins', logger: 'org.codehaus.groovy.grails.plugins'],
            [name: 'Apache Commons', logger: 'org.codehaus.groovy.grails.commons'],
    ]


    static final List otherLogs = [
            [name: 'Spring', logger: 'org.springframework'],
            [name: 'SQL', logger: 'org.hibernate.SQL'],
            [name: 'Hibernate', logger: 'org.hibernate']
    ]


    // By default render the standard "chooser" view
    def index =
    {
        def domainLoggers = buildArtefactLoggerMapList("Domain")
        addCurrentLevelToLoggerMapList(domainLoggers)

        def controllerLoggers = buildArtefactLoggerMapList("Controller")
        addCurrentLevelToLoggerMapList(controllerLoggers)

        def serviceLoggers = buildArtefactLoggerMapList("Service")
        addCurrentLevelToLoggerMapList(serviceLoggers)

        def grailsLoggers = []
        grailsLogs.each { grailsLoggers << it.clone(); }
        addCurrentLevelToLoggerMapList(grailsLoggers)

        def otherLoggers = []
        otherLogs.each { otherLoggers << it.clone() }
        addCurrentLevelToLoggerMapList(otherLoggers)

        render(view:  'logging',
                model : [
               controllerLoggers: controllerLoggers,
               serviceLoggers: serviceLoggers,
               domainLoggers: domainLoggers,
               grailsLoggers: grailsLoggers,
               otherLoggers: otherLoggers,
               ])
    }


	// Sets the log level based on parameter values
    def setLogLevel =
    {
    	def logger = params.logger
    	def level = Level.toLevel(params.level)

    	// Find the right Logger
    	Logger l
    	if(logger)
    		l = Logger.getLogger(logger)
    	else
    		l = Logger.getRootLogger()

    	// Set the Logger level
    	l.setLevel(level)

//        flash.message = "Logger $logger set to level $level"

//        redirect (action: 'index')

        log.info("Logger $logger set to level $level")

    	// Produce and render equivalent Config.groovy script
		def tail = logger.replaceFirst("[^\\.]*\\.","")
		def head = logger.replaceAll("\\..*","")

		def loggerConfig = "\t\tgrails\n\t\t{\n\t\t\t$tail=\"$level\"\n\t\t}"

		render(view:'confirm',model:[logger:logger,level:level,loggerConfig:loggerConfig])
    }

}
