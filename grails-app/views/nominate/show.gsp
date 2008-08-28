<html>
<head>
    <title>
        ${nom.name}    
    </title>
    <link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'bubbles.css')}"/>    

</head>
<body>
<p id="intro">
    The groovy and grails community appreciates...
</p>

<h2>${nom.name}</h2>

<div id="stats">
${nom.pageViews} views &raquo; ${nom.fanBoys.size()} comments
</div>

<g:if test="${flash.existingNom}">

        <g:render template="nomForm"/>

</g:if>


<g:if test="${nom.fanBoys.size() > 0}">

    <div id="fanboys">

    <g:each var="fanboy" in="${nom.fanBoys}">
        <a name="c${fanboy.id}"/>
        <div class="bubble">
        <blockquote>
            <p>
             ${fanboy.content.encodeAsHTML()}
            </p>
        </blockquote>
        <cite>
            <strong>${fanboy.name}</strong> &raquo; <g:dateFromNow date="${fanboy.created}"/>
        </cite>
        </div>
    </g:each>

    </div>

</g:if>


<g:if test="${!flash.existingNom}">

        <g:render template="nomForm"/>

</g:if>




</body>
</html>