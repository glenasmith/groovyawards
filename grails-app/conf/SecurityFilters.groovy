/**
 * Created by IntelliJ IDEA.
 * User: glen
 * Date: Aug 27, 2008
 * Time: 1:39:42 PM
 * To change this template use File | Settings | File Templates.
 */
class SecurityFilters {

    def filters = {

        adminActions(controller: "admin", action: "*") {
            before = {
                accessControl {
                    role("admin")
                }
            }
        }

        nomScaffoldActions(controller: "nomination", action: "*") {
            before = {
                accessControl {
                    role("admin")
                }
            }
        }

        fanboyScaffoldActions(controller: "fanBoy", action: "*") {
            before = {
                accessControl {
                    role("admin")
                }
            }
        }

        loggingActions(controller: "runtimeLogging", action: "*") {
            before = {
                accessControl {
                    role("admin")
                }
            }
        }

        secureActions(controller: "nominate", action:"(nominate|confirm|directLove)") {
            before = {
                accessControl {
                    true
                }
            }

        }

    }

}