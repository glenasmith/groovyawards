<html>
<head>
    <title>Confirmation for ${name}</title>
</head>
<body>

<fieldset>
<legend>Confirm New Nominee</legend>
<g:form action="confirm">

    <div style="text-align: center">
    <p>
    That's the first time we've seen that name. Just confirming you wanted to nominate:
</p>

<h2>${name}</h2>

    <p>
       for a groovy and grails award
    </p>
    <p>
        <g:hiddenField name="name" value="${name}"/>
        <g:submitButton name="confirmed" value="Continue"/>
    </p>
    </div>


</g:form>
</fieldset>




</body>
</html>