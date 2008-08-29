<html>
<head>
    <title>
        ${nom.name}    
    </title>
    <link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'bubbles.css')}"/>
    <g:javascript library="prototype"/>
    <g:javascript library="scriptaculous"/>
</head>
<body>
<p id="intro">
    The groovy and grails community appreciates...
</p>

<h2>${nom.name}</h2>

<div id="stats">
${nom.pageViews} views &raquo; ${nom.fanBoys.size()} comments
        <jsec:hasRole name="admin">
            <span id="deleteNom${nom.id}" class="admin">
                <g:remoteLink controller="admin" action="deleteNomination" id="${nom.id}" update="deleteNom${nom.id}">Delete</g:remoteLink>
            </span>
        </jsec:hasRole>
</div>

<g:if test="${flash.existingNom}">

        <g:render template="nomForm"/>

</g:if>


<g:if test="${nom.fanBoys.size() > 0}">

    <div id="fanboys">

    <g:each var="fanboy" in="${nom.fanBoys}">
        <g:render template="comment" model="[ fanboy : fanboy ]"/>
    </g:each>

    </div>

</g:if>


<g:if test="${!flash.existingNom}">

        <g:render template="nomForm"/>

</g:if>




</body>
</html>