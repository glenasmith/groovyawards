class AdminController {

    /*
    These actions are accessed via AJAX, so no there are no view requirements
     */

    def deleteComment = {

        def fanBoy = FanBoy.get(params.id)
        if (fanBoy) {
            fanBoy.delete()
            render "Deleted"
        } else {
            render "Failed"
        }

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
