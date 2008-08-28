<html>
<head>
    <title>Search</title>
</head>
<body>

<fieldset>
<legend>Search Criteria</legend>
<g:form action="search">

    <p>
        <label for="name">Search:</label>
        <g:textField name="query" value="${params.query}"/>
    </p>
    <p>
        <label for="search">&nbsp;</label>
        <g:submitButton name="search" value="Search"/>
    </p>



</g:form>
</fieldset>

Don't forget you can  <g:link action="browse">browse</g:link>


<g:if test="${results != null}">

    <div id="searchCount">
        <g:if test="${results.total > 0}">
            Displaying <b>${1 + results.offset} - ${results.offset + results.max}</b>
            of <b>${results.total}</b> matches
        </g:if>
        <g:else>
            No matches found.
        </g:else>
    </div>


    <g:each var="result" in="${results.results}" status="i">

        <div class='hit'>

            <div class='hitEntry'>
                <div class='hitTitle'>
                    <a href="<g:markupName name="${result.nomination.name}"/>">

                        ${result.nomination.name}

                        </a>
                </div>

                <p class='hitBody'>
                    ${ results.highlights[i] ?: "..." }
                </p>
            </div>
        </div>

    </g:each>


    <div class="paginate">
        <g:paginate action="search" total="${results.total}" max="10" params="[ query: params.query]"/>
     </div>


</g:if>


</body>
</html>