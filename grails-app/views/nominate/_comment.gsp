<a name="c${fanboy.id}"/>
<div class="bubble">
    <blockquote>
        <p>
            ${fanboy.content.encodeAsHTML().replaceAll("\n", "<p/>")}
        </p>
    </blockquote>
    <cite>
        <strong>${fanboy.name}</strong> &raquo; <g:dateFromNow date="${fanboy.created}"/>
        <jsec:hasRole name="admin">
            <span id="delete${fanboy.id}" class="admin">
                &raquo; 
                <g:remoteLink controller="admin" action="deleteComment" id="${fanboy.id}" update="delete${fanboy.id}">Delete</g:remoteLink>
            </span>
        </jsec:hasRole>
    </cite>
</div>