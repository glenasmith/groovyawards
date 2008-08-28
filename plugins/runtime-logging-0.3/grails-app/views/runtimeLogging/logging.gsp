<html>
<head>
    <meta name="layout" content="main"/>
    <title>Set Logging Levels</title>
</head>
<body>
    <h1>Set Logging Levels</h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <fieldset>
        <legend>Controller Loggers</legend>
        <g:form action="setLogLevel" method="POST">
            <span class="loggerName">
                <g:select name="logger" from="${controllerLoggers}" optionValue="name" optionKey="logger"></g:select></span>
            <span class="level"><g:select name="level" from="${['OFF','TRACE','DEBUG','INFO','WARN','ERROR','FATAL']}" value="DEBUG"></g:select></span>
            <span class="submitButton"><input type="submit" value="Submit"/></span>
        </g:form>
    </fieldset>

    <fieldset>
        <legend>Service Loggers</legend>
        <g:form action="setLogLevel" method="POST">
            <span class="loggerName">
                <g:select name="logger" from="${serviceLoggers}" optionValue="name" optionKey="logger"></g:select></span>
            <span class="level"><g:select name="level" from="${['OFF','TRACE','DEBUG','INFO','WARN','ERROR','FATAL']}" value="DEBUG"></g:select></span>
            <span class="submitButton"><input type="submit" value="Submit"/></span>
        </g:form>
    </fieldset>

    <fieldset>
        <legend>Domain Class Loggers</legend>
        <g:form action="setLogLevel" method="POST">
            <span class="loggerName">
                <g:select name="logger" from="${domainLoggers}" optionValue="name" optionKey="logger"></g:select></span>
            <span class="level"><g:select name="level" from="${['OFF','TRACE','DEBUG','INFO','WARN','ERROR','FATAL']}" value="DEBUG"></g:select></span>
            <span class="submitButton"><input type="submit" value="Submit"/></span>
        </g:form>
    </fieldset>

    <fieldset>
        <legend>Grails Loggers</legend>
        <g:form action="setLogLevel" method="POST">
            <span class="loggerName">
                <g:select name="logger" from="${grailsLoggers}" optionValue="name" optionKey="logger"></g:select></span>
            <span class="level"><g:select name="level" from="${['OFF','TRACE','DEBUG','INFO','WARN','ERROR','FATAL']}" value="DEBUG"></g:select></span>
            <span class="submitButton"><input type="submit" value="Submit"/></span>
        </g:form>
    </fieldset>

    <fieldset>
        <legend>3rd Party Loggers</legend>
        <g:form action="setLogLevel" method="POST">
            <span class="loggerName">
                <g:select name="logger" from="${otherLoggers}" optionValue="name" optionKey="logger"></g:select></span>
            <span class="level"><g:select name="level" from="${['OFF','TRACE','DEBUG','INFO','WARN','ERROR','FATAL']}" value="DEBUG"></g:select></span>
            <span class="submitButton"><input type="submit" value="Submit"/></span>
        </g:form>
    </fieldset>
</body>
</html>
