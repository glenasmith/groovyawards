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
</body>
</html>