
class AwardTagLib {

    public static String getNiceDate(Date date) {

        def now = new Date()

        def diff = Math.abs(now.getTime() - date.getTime())

        long second = 1000
        long minute = 1000 * 60
        long hour = minute * 60
        long day = hour * 24

        def niceTime = ""

        long calc = 0L;

        calc = Math.floor(diff / day)
        if (calc > 0) {
            niceTime += calc + " day" + (calc > 1 ? "s " : " ")
            return niceTime + " ago"
        }

        calc = Math.floor(diff / hour)
        if (calc > 0) {
            niceTime += calc + " hour" + (calc > 1 ? "s " : " ")
            return niceTime + " ago"
        }

        calc = Math.floor(diff / minute)
        if (calc > 0) {
            niceTime += calc + " min" + (calc > 1 ? "s " : " ")
            return niceTime + " ago"
        }

        return "Right now"
        

    }


    def dateFromNow = {attrs ->

        def date = attrs.date

        out << getNiceDate(date)

    }

    def recentlyAdded = {

        def recent = Nomination.createCriteria().list {
            maxResults(5)
            order('created', 'desc')
            cacheable(true)
               
        }
        out << "<table>"
        recent.each { nom ->
            out << "<tr>"
            out << "<td class='title'><a href='${markupName(name: nom.name)}'>${nom.name}</a></td>"
            out << "<td class='stat'>" + getNiceDate(nom.created) + "</td>"
            out << "</tr>"

        }
        out << "</table>"
        


    }

    def recentlyCommented = {

        def rc = FanBoy.createCriteria().list {
            maxResults(5)
            order('created', 'desc')
            cacheable(true)
        }
        out << "<table>"
        rc.each { fanboy ->
            out << "<tr>"
            out << "<td class='title'><a href='${markupName(name: fanboy.nomination.name)}#c${fanboy.id}'>${fanboy.nomination.name}</a></td>"
            out << "<td class='stat'>by ${fanboy.name}</td>"
            out << "</tr>"

        }
        out << "</table>"



    }



    def mostCommented = {

        Nomination.withTransaction {
            def mc = Nomination.list().sort() { -it.fanBoys.size() }
            if (mc.size() > 5) {
                mc = mc[0..4]
            }
            out << "<table>"
            mc.each { nom ->
                out << "<tr>"
                out << "<td class='title'><a href='${markupName(name: nom.name)}'>${nom.name}</a></td>"
                out << "<td class='stat'>${nom.fanBoys.size()}</td>"
                out << "</tr>"

            }
            out << "</table>"
        }



    }

    def mostViewed = {

        def mv = Nomination.createCriteria().list {
            maxResults(5)
            order('pageViews', 'desc')
            cacheable(true)

        }
        out << "<table>"
        mv.each { nom ->
            out << "<tr>"
            out << "<td class='title'><a href='${markupName(name: nom.name)}'>${nom.name}</a></td>"
            out << "<td class='stat'>${nom.pageViews}</td>"
            out << "</tr>"

        }
        out << "</table>"

        
    }

    def markupName = { attrs ->

        out << createLink(controller: 'nominate', action:'show', id:attrs.name.encodeAsNiceTitle())
        
    }


}