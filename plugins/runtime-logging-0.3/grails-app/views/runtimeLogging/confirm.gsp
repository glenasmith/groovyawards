<%@ page contentType="text/html" %>

<html>
  <head>
	<meta name="layout" content="main" />
    	<title>Logging Level Changed</title>
	<link rel="stylesheet" href="${createLinkTo(dir:'css',file:'logging.css')}"></link>
  </head>
  <body>
  	<h1>Logging Level Changed</h1>
	Logger ${logger} set to level ${level}
	<h1>Config.groovy equivalent</h1>
	Update the log4j/logger{} closure in Config.groovy to achieve the same effect permanently:<br/><br/>
	<p>
		<pre>
log4j
{
	...
	logger
	{
		...
${loggerConfig}
	}
}
		</pre>
	</p>
	
	<p>
		Return to main <g:link controller="runtimeLogging">RuntimeLogging page</g:link>
	</p>
  </body>
</html>