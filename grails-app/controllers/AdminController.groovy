class AdminController {

    /*
    These actions are accessed via AJAX, so no there are no view requirements
     */

    def deleteComment = {

        def fanBoy = FanBoy.get(params.id)
        def nom = Nomination.createCriteria().get {
            fanBoys {
                eq('id', fanBoy.id)
            }

        }
        println "Found ${fanBoy} in ${nom}"
        if (nom) {
            fanBoy.delete()
            nom.removeFromFanBoys(fanBoy)
            render "Deleted"
        } else {
            render "Failed"
        }

//        def fanBoy = FanBoy.get(params.id)
//        if (fanBoy) {
//            fanBoy.delete()  // nested exception is org.hibernate.ObjectDeletedException: deleted object would be re-saved by cascade (remove deleted object from associations)
//            render "Deleted"
//        } else {
//            render "Failed"
//        }

    }

    def deleteNomination = {

        def nom = Nomination.get(params.id)
        if (nom) {
           nom.delete()
           render "Deleted"
        } else {
            render "Failed"
        }

    }
}
