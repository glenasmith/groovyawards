class FeedController {


    def index = {  redirect(action: "nominations") }

    def nominations = {

        render(feedType: "rss", feedVersion: "2.0") {
            title = "GroovyAwards: New Nominations"
            link = g.createLink( controller:'feed', action:'nominations', absolute: true )
            description = "New Nominations created at GroovyAwards"

            Nomination.listOrderByCreated(max:20, order:"desc").each { nom ->
                entry(nom.name) {
                    link = g.createLink( controller:'nominate', action:'show', id:nom.name.encodeAsNiceTitle(), absolute: true)
                    publishedDate = nom.created
                }
            }
        }


    }

    def comments = {
        
        render(feedType: "rss", feedVersion: "2.0") {
            title = "GroovyAwards: New Comments"
            link = g.createLink( controller:'feed', action:'comments', absolute: true )
            description = "New Comments created at GroovyAwards"

            FanBoy.listOrderByCreated(max:20, order:"desc").each { fanboy ->
                entry(fanboy.nomination.name) {
                    link = g.createLink( controller:'nominate', action:'show', id:fanboy.nomination.name.encodeAsNiceTitle(), absolute: true) + "#c" + fanboy.id
                    publishedDate = fanboy.created
                    author = fanboy.name
                    fanboy.content
                }
            }
        }

    }
}

