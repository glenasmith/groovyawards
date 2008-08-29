

<jsec:isNotLoggedIn>
    <p style="text-align: center;">
        Once you <g:link controller="nominate" action="register">register</g:link> or
        <g:link controller="auth" action="login" params="[ targetUri: '/nominate/show/'+ nom.name.encodeAsNiceTitle() ]">login</g:link> you can add comments.
    </p>
</jsec:isNotLoggedIn>



<jsec:isLoggedIn>

<a name="#newnom"/>


<div id="preview" style="margin: 1em; display: none;">

</div>

<fieldset>
    <legend>I think they deserve a nomination because...</legend>
    <g:form action="directLove">

        <p>
            <label for="content">Reason <br/> (no html)</label>
            <g:textArea name="content" cols="35" rows="6">
            </g:textArea>
        </p>
        <p>
            <label for="name">Your Name</label> <g:textField name="name" value="${jsec.principal()}"/>
        </p>
        <p>

            <g:hiddenField name="nomid" value="${nom.id}"/>
        </p>
        <p>
       <label for="finish">then...</label>
        <g:submitToRemote url="[controller: 'nominate', action: 'previewLove']" update="preview" onSuccess="Effect.Appear('preview');" value="Preview"/> or
        <g:submitButton name="finish" value="Add Love"/>
        </p>
    </g:form>
</fieldset>

</jsec:isLoggedIn>
