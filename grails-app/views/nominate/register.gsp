<html>
<head>
    <title>Register</title>
    <g:javascript library="prototype"/>
</head>
<body>

<div id="welcome">
    Signup for an account and you'll be able to nominate and add comments
    to your fave dudes.
</div>

<fieldset>
<legend>Signup</legend>
    <g:form action="register">
        <p>
            <label for="username">Username:</label> <g:textField name="username" value="${username}/>

            <g:submitToRemote url="[controller: 'nominate', action: 'availability']" update="availability" value="Available?"/> <span id="availability"></span>
        </p>
        <p>
            <label for="password">Password: </label><g:passwordField name="password" value="${password}"/>
        </p>
        <p>
            <g:hiddenField name="name" value="${name}"/>
            <g:submitButton name="register" value="Register"/>
        </p>

    </g:form>
</fieldset>




</body>
</html>