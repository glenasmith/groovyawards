import org.jsecurity.crypto.hash.Sha1Hash
import org.jsecurity.authc.UsernamePasswordToken

class NominateController {

    def jsecSecurityManager

    def index = {
        redirect(action:"home")
    }

    def home = {

        // build data for tagcloud using a projection
        def resList = FanBoy.withCriteria {
            createAlias("nomination", "n")
            projections {
                groupProperty('n.name')
                count('id')
            }
        }

        // projection returns a list of name,count -- but tagcloud needs a map. Convert it here.

        def commentMap = resList.inject([ : ]) { map, res -> map[ res[0] ] = res[1]; map }

        [ commentMap : commentMap ]

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

    def register = {

        if (params.username) {
            if (JsecUser.findByUsernameIlike(params.username)) {
                flash.message = "That id is already taken"
            } else if (!params.username || !params.password) {
                flash.message = "You must supply a username and password"
            } else {
                JsecRole userRole = JsecRole.findByName("user")
                def newUser = new JsecUser(username: params.username, passwordHash: new Sha1Hash(params.password).toHex()).save(flush: true)
                new JsecUserRoleRel(user: newUser, role: userRole).save(flush: true)

                // and log them in...
                def authToken = new UsernamePasswordToken(params.username, params.password)
                jsecSecurityManager.login(authToken)

                render(view:"registerSuccess", model: [ user: newUser ])
            }
            [ username: params.username, password: params.password ]
        }


    }

    def availability = {
        println "Available for: ${params.username}"
        if (JsecUser.findByUsernameIlike(params.username)) {
            render "<span style='color: red'>No</span>"
        } else {
            render "<span style='color: green'>Yes</span>"
        }
    }

    def browse = {
        log.debug "Now browsing.."
        def noms = Nomination.listOrderByName()

        // work out the letters we need to mark up
        def activeLetters = noms.collect { nom -> nom.name.toUpperCase()[0] }.unique()

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

    def showTag = {

        // encode the full name then redirect on
        redirect(action: show, id: params.selectedTag.encodeAsNiceTitle())

        
    }

    def show = {

        // shocking inefficiencies in the name of a nice URL
        def allNoms = Nomination.list()
        def nom = allNoms.find { nom -> nom.name.encodeAsNiceTitle() == params.id }
        if (nom) {
            
            try {
                nom.pageViews++
                nom.save(flush: true)
            } catch(org.springframework.dao.OptimisticLockingFailureException e) {
	            // whatever, it's just a hit counter...
                log.warn("Missed hit on ${nom.name} because of locking")
            }

            return [ nom : nom ]
        } else {
            response.sendError(404) 
        }

    }

    def previewLove = {
        FanBoy fb = new FanBoy(params)
        render(template: "comment", model: [ fanboy: fb ])
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


    def timeline = {
        params.max = 10
        params.order = 'desc'
        def fanBoys = FanBoy.listOrderByCreated(params)
        [ fanBoys : fanBoys, totalFans : FanBoy.count() ]

    }

    def how = {
        // placeholder for static content
    }

}
