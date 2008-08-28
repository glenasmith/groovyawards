<a name="#newnom"/>
<fieldset>
    <legend>I think they deserve a nomination because...</legend>
    <g:form action="directLove">

        <p>
            <label for="content">Reason <br/> (no html)</label>
            <g:textArea name="content" cols="35" rows="6">
            </g:textArea>
        </p>
        <p>
            <label for="name">Your Name</label> <g:textField name="name" value="Anonymous"/>
        </p>
        <p>

            <g:hiddenField name="nomid" value="${nom.id}"/>
        </p>
        <p>
       <label for="finish">then...</label><g:submitButton name="finish" value="Add Love"/>
    </g:form>
</p>
</fieldset>