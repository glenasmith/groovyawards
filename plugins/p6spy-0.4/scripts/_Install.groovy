import org.apache.commons.lang.StringUtils

// This script is executed by Grails after plugin was installed to project.

Ant.property(environment:"env")
grailsHome = Ant.antProject.properties."env.GRAILS_HOME"


// default driver
String realDriver = 'org.hsqldb.jdbcDriver'

//	 add p6spy driver as a commented line to DataSource.groovy
File p6file = new File("${basedir}/grails-app/conf/spy.properties")
File dsfile = new File("${basedir}/grails-app/conf/DataSource.groovy")
if(dsfile.getText().indexOf('p6spy')==-1 && !p6file.exists()) {
	println "Adding p6spy driver to DataSource.groovy"
	StringBuffer sb = new StringBuffer()
	dsfile.eachLine { line ->
		sb << line << '\n'
		if(line.indexOf('driverClassName')>-1) {
			// record the real driver so we can put it in spy.properties
			realDriver = StringUtils.substringBetween(line,'"')
			// add the p6spy driver so it can easily be enabled
			sb << '//    driverClassName = "com.p6spy.engine.spy.P6SpyDriver" // use this driver to enable p6spy logging\n'
		}
	}
	dsfile.write(sb.toString())

	println "Configuring spy.properties with realdriver=${realDriver}"
	// set real driver in spy.properties
	File spyfile = new File("${pluginBasedir}/grails-app/conf/spy.template")
	sb = new StringBuffer()
	spyfile.eachLine { line ->
		if(line.startsWith('realdriver=')) {
			sb << "realdriver=${realDriver}\n"
		} else {
			sb << line << '\n'
		}
	}
	p6file.write(sb.toString())
} else {
	println "It appears configuration for p6spy already exists. Not modifying DataSource.groovy or spy.properties"
}
