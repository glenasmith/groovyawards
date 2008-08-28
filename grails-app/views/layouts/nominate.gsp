<html>
<head>
    <title>GroovyAwards &raquo; <g:layoutTitle default="Welcome" /></title>

    <link rel="shortcut icon" href="${createLinkTo(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
    <link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'reset-fonts-grids.css')}"/>    
    <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'groovyawards.css')}" />

    <g:layoutHead/>

</head>
<body>
           <div id="doc3" class="yui-t5">
		            <div id="hd">
                        <div id="topmenu">
		                    <g:link controller="nominate" action="home">Nominate</g:link> |
                            <g:link controller="nominate" action="how">About</g:link> |
                            <g:link controller="nominate" action="search">Search</g:link> |
                            <g:link controller="nominate" action="browse">Browse</g:link> |
                            <jsec:isNotLoggedIn>
                                <g:link controller="auth" action="login">Login</g:link>
                            </jsec:isNotLoggedIn>
                            <jsec:isLoggedIn>
                                <jsec:principal/>
                                <g:link controller="auth" action="signOut">Logout</g:link>
                            </jsec:isLoggedIn>

                        </div>


		                <div id="header">
                            <a href="<g:createLink controller="nominate" action="home"/>"><img src="<g:createLinkTo dir='images' file='logo.png'/>" alt="groovyawards logo"/>
                            </a>
						</div>



		            </div>
		            <div id="bd"><!-- start body -->

		                <div id="yui-main">
		                    <div class="yui-b">
		                        <g:if test="${flash.message}">
		                            <div class="flash">
		                                ${flash.message}
		                            </div>
		                        </g:if>
                                <div id="ga-body">
		                            <g:layoutBody/>
                                </div>
                            </div>
		                </div>
		                <div class="yui-b">

		                    <g:render template="/sidebar"/>

		                </div>

		            </div>  <!-- end body -->
		            <div id="ft">
		                <div id="footerText">
		                Groovy Awards <g:meta name="app.version"/> on Grails <g:meta name="app.grails.version"/> by <a href="http://blogs.bytecode.com.au/glen">Glen Smith</a>.
		                </div>
		            </div>
		        </div>
</body>
</html>