class FanBoy implements Comparable {

    String name
    String content
    //String url

    Date created = new Date()

    static constraints = {
        name(nullable: false, blank: false)
        content(nullable: false, blank: false, maxSize: 1024)
        //url(nullable: true, url: true)
    }

    // keep fanboys sorted
    public int compareTo(Object obj) {
        return created <=> obj.created
    }

    static belongsTo = [ nomination : Nomination ]

    static searchable = {
        name()
        content()
        nomination(component: true)
    }

    static mapping = {
        cache: true
    }


}
