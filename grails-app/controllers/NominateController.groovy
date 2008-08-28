class NominateController {

    def index = {
        redirect(action:"home")
    }

    def home = {
        // nothing to do here...
    }

    def nominate = {

        def name = params.name?.trim()
        if (name) {
            def nom = Nomination.findByNameIlike(name)
            if (nom) {
                flash.existingNom = true

            } else {
                nom = new Nomination(name: name)
                if (!nom.validate()) {
                    log.warn "Invalid user nomination: ${name}"
                    flash.message = "Error creating new nomination"
                    redirect(action: home)
                    return
                } else {
                    log.debug "Confirm new user creation"
                    redirect(action: 'confirm', params: ['name' : nom.name ])
                }
            }
            redirect(action: show, id: nom.name.encodeAsNiceTitle())

        } else {
            log.warn "Invalid empty user nomination"
            flash.message = "You must supply a name, dude"
            redirect(action: home)
        }



    }

    // for autocomplete
    def nominationAutoComplete = {

        log.debug "Querying on ${params.query}..."
        def queryRegex = "(?i)${params.query}" // case insensitive...
        def noms = Nomination.list().findAll { nom -> nom.name =~ queryRegex }
        render(contentType: "text/xml") {
            results() {
                noms.each { nom ->
                    result() {
                        name(nom.name)
                    }
                }
            }
        }

    }

    def confirm = {

        if (params.confirmed && params.name) {
            Nomination nom = new Nomination(params)
            if (nom.save()) {
                redirect(action: show, id: nom.name.encodeAsNiceTitle())
            } else {
                flash.message = "I need at least a name"
                redirect(action: home)
            }
        } else {
            return [ name : params.name ]
        }
        
    }



    def browse = {
        log.debug "Now browsing.."
        def noms = Nomination.listOrderByName()

        // work out the letters we need to mark up
        def activeLetters = noms.collect { nom -> nom.name[0] }.unique()

        [ noms : noms, activeLetters : activeLetters ]
    }

    def search = {

        def query = params.query

        if (!query) {
            return [ ]
        }

        params.withHighlighter = { highlighter, index, sr ->
            // lazy-init the storage
            if (!sr.highlights) {
                sr.highlights = []
            }

            // store highlighted text; "content" is a searchable-property of the FanBoy domain class
            def matchedFragment = highlighter.fragment("content")

           
            sr.highlights[index] = matchedFragment ? "...${matchedFragment}..." : ""
        }

        // limit query to current blog published entries...
        def results = FanBoy.search(query, params)

        return [ results: results, query: query ]
    }

    /*

     Sample of dynamic criteria queries... Keep for the book

    def searchResults = {


        def results = Nomination.createCriteria().list {
            or {
                if (params.name) {
                    ilike("name", params.name)
                }
                if (params.url) {
                    ilike("url", params.url)
                }
                if (params.nominator) {
                    fanBoys {
                        ilike("name", params.nominator)
                    }
                }
                if (params.comments) {
                    fanBoys {
                        ilike("content", params.nominator)
                    }
                }
            }
        }
        println results.dump()
        return [noms: results]

    }
    */


    def show = {

        // shocking inefficiencies in the name of a nice URL
        def allNoms = Nomination.list()
        def nom = allNoms.find { nom -> nom.name.encodeAsNiceTitle() == params.id }
        if (nom) {

            nom.pageViews++

            return [ nom : nom ]
        } else {
            reponse.sendError(404) 
        }

    }

    def directLove = {
        FanBoy fb = new FanBoy(params)
        if (!fb.name)
            fb.name = "Anonymous"
        
        Nomination nom = Nomination.get(params.nomid)
        nom.addToFanBoys(fb)
        if (!fb.validate()) {
            nom.discard()
            flash.message = "Dude. I need at *least* some content lovin"
        } else {
            nom.save(flush:true)
            flash.message = "Successfully add new love for: ${nom.name}"
        }
        redirect(action: 'show', id: nom.name.encodeAsNiceTitle())
    }

    def how = {
        // placeholder for static content
    }

}
