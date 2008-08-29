<html>
<head>
    <title>
        Timeline
    </title>
    <link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'bubbles.css')}"/>
    <g:javascript library="prototype"/>
</head>
<body>


<h2>Recent Love...</h2>


<g:if test="${fanBoys.size() > 0}">

    <div id="fanboys">

    <g:each var="fanboy" in="${fanBoys}">
        <div class="bubble">
        <blockquote>
            <p>
             ${fanboy.content.encodeAsHTML().replaceAll("\n", "<p/>")}
            </p>
        </blockquote>
        <cite>
            For <strong><a href="<g:markupName name="${fanboy.nomination.name}"/>">${fanboy.nomination.name}</a></strong>
            by <strong>${fanboy.name}</strong> &raquo; <g:dateFromNow date="${fanboy.created}"/>

            <jsec:hasRole name="admin">
                <span id="delete${fanboy.id}" class="admin">
                    &raquo; 
                    <g:remoteLink controller="admin"  action="deleteComment" id="${fanboy.id}" update="delete${fanboy.id}">Delete</g:remoteLink>
                </span>
            </jsec:hasRole>

        </cite>
        </div>



   </g:each>

    </div>

</g:if>

<div id="paginate">
    <g:paginate total="${totalFans}"/>
</div>

</body>
</html>