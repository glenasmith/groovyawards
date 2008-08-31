<html>
<head>
    <title>GroovyAwards &raquo; <g:layoutTitle default="Welcome" /></title>

    <link rel="shortcut icon" href="${createLinkTo(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
    <link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'reset-fonts-grids.css')}"/>    
    <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'groovyawards.css')}" />

    <meta name="keywords" content="groovy,grails,groovy awards,grails awards"/>
    <meta name="description" content="Community participation awards from the Groovy and Grails community"/> 

    <feed:meta kind="rss" version="2.0" controller="feed" action="nominations"/>
    <feed:meta kind="rss" version="2.0" controller="feed" action="comments"/>

    <g:layoutHead/>

</head>
<body>
           <div id="doc3" class="yui-t5">
		            <div id="hd">
                        <div id="topmenu">
		                    <g:link controller="nominate" action="home">Nominate</g:link> |
                             <g:link controller="nominate" action="timeline">Timeline</g:link> |

                            <g:link controller="nominate" action="search">Search</g:link> |
                            <g:link controller="nominate" action="browse">Browse</g:link> |
                            <jsec:isNotLoggedIn>
                                <g:link controller="nominate" action="register">Register</g:link> |
                                <g:link controller="auth" action="login">Login</g:link> |
                            </jsec:isNotLoggedIn>
                            <jsec:isLoggedIn>
                                <jsec:principal/>
                                <g:link controller="auth" action="signOut">Logout</g:link> |
                                <jsec:hasRole name="admin">
                                    <g:link controller="nomination">Nom Scaffold</g:link> |
                                    <g:link controller="fanBoy">Fan Scaffold</g:link> |
                                </jsec:hasRole>
                            </jsec:isLoggedIn>
                            <g:link controller="nominate" action="how">About</g:link>

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
		                Groovy Awards <g:meta name="app.version"/> on Grails <g:meta name="app.grails.version"/> by <a href="http://blogs.bytecode.com.au/glen">Glen Smith</a> and <a href="http://hansamann.podspot.de/">Sven Haiges</a>.
		                </div>
		            </div>
		        </div>
</body>
</html>