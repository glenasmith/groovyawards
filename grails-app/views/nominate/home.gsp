<html>
<head>
    <title>Homepage</title>
</head>
<body>

<div id="welcome">
    <h4 style="font-size: 40px; text-align: center; font-weight: bold; border-bottom: 1px dotted gray;">
        We Love Your Work...
    </h4>
    <p style="font-size: 24px; text-align: center;">
        Recognising those people who contribute heavily
        to our community through mailing lists, plugins,
        blogs, source code and other coolness.
    </p>
    
</div>

<jsec:isNotLoggedIn>
    <p style="text-align: center;">
        Once you <g:link controller="nominate" action="register">register</g:link> or
        <g:link controller="auth" action="login">login</g:link> you can nominate.
    </p>
</jsec:isNotLoggedIn>


<jsec:isLoggedIn>
<fieldset>
<legend>Autocomplete a name or type a new one...</legend>
        <g:form action="nominate">
    <p>
    <label for="name"> Name:</label>

        <resource:autoComplete skin="default" />
        <richui:autoComplete name="name" style="width: 40%"
                action="${createLink(action: 'nominationAutoComplete')}"/>



     </p>
      <br/>
          <label for="nominate">&nbsp;</label>
          <g:submitButton name="nominate" value="Nominate"/>
      <p/>
            

        </g:form>
    </fieldset>
<p style="text-align: center;">
    or you can <a href="search">search</a> or
            <a href="browse">browse</a>
            
</p>
</jsec:isLoggedIn>



<div class="infoBox">
    <div class="infoBoxTitle" style="border-bottom: 1px dotted darkgray; margin-bottom: 1em;">
        Most Commented Tagcloud
    </div>
    <div class="infoBoxBody">
        <richui:tagCloud values="${commentMap}" controller="nominate" action="showTag" minSize="8" maxSize="30" linkClass="tagLink"/>
    </div>
</div>


</body>
</html>