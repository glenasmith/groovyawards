<div id="sidebar">


    <div class="infoBox">
        <div class="infoBoxTitle">
            Recently Added
        </div>
        <div class="infoBoxBody">
            <g:recentlyAdded/>
        </div>

    </div>                          

    <div class="infoBox">
        <div class="infoBoxTitle">
            Recently Commented
        </div>
        <div class="infoBoxBody">
            <g:recentlyCommented/>
        </div>

    </div>

    <div class="infoBox">
        <div class="infoBoxTitle">
            Most Commented
        </div>
        <div class="infoBoxBody">
           <g:mostCommented/>
        </div>

    </div>

    <div class="infoBox">
        <div class="infoBoxTitle">
            Most Viewed
        </div>
        <div class="infoBoxBody">
            <g:mostViewed/>
        </div>

    </div>

    <div class="infoBox">
        <div class="infoBoxTitle">
            Feeds
        </div>
        <div class="infoBoxBody">
           <g:link controller="feed" action="nominations">Nominations</g:link> <br/>
           <g:link controller="feed" action="comments">Comments</g:link>
        </div>

    </div>

    <div class="infoBox">
        <div class="infoBoxTitle">
            Button Love
        </div>
        <div class="infoBoxBody" style="margin-left: 3em;">

                <a href="http://hansamann.podspot.de/"><img src="<g:createLinkTo dir='images' file='grails_podcast_button.png'/>" alt="grails podcast logo"/></a> <br/>
            <br/>

                <a href="http://www.grails.org"><img src="<g:createLinkTo dir='images' file='grails_button.png'/>" alt="grails logo"/></a>
            <br/>
            
        </div>

    </div>

</div>