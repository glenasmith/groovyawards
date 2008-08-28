<html>
<head>
    <title>Browse All</title>
</head>
<body>

<h3>Browsing</h3>

<div class="rolodex">
    <g:each var="letter" in="${ ('A'..'Z') }">
        <g:if test="${activeLetters.contains(letter)}">
            <span class="activeLetter">
                <a href="#${letter}">${letter}</a>
            </span>
        </g:if>
        <g:else>
            <span class="inactiveLetter">
                ${letter}
            </span>
        </g:else>

    </g:each>
</div>


<g:each var="nom" in="${noms}">

    <g:if test="${lastLetter != nom.name[0]}">
        <a name="${nom.name[0]}"
        <div class="bigLetter">${nom.name[0]}</div>
        <g:set var="lastLetter" value="${nom.name[0]}"/>
    </g:if>

    <div class="nomList">
        <div class="nomName">
            <a href="<g:createLink action="show" id="${nom.name.encodeAsNiceTitle()}"/>">${nom.name}</a>
        </div>
        
        <div class="nomStats">
            ${nom.pageViews} views &raquo; ${nom.fanBoys.size()} comments
        </div>
    </div>

</g:each>

</body>
</html>