import org.jsecurity.crypto.hash.Sha1Hash

class BootStrap {

     def init = { servletContext ->

         if (!Nomination.list()) {
            Nomination n = new Nomination(name: "Joe Cool", url: "http://www.groovyblogs.org/")
            if (n.validate()) {
                println n.save()
            } else {
                println "\n\n\nValidation failed!!!\n\n\n"
            }
         }

         if (!JsecUser.list()) {
             def adminRole = new JsecRole(name: 'admin').save()
             def adminUser = new JsecUser(username: 'admin', passwordHash: new Sha1Hash("admin").toHex()).save()
             new JsecUserRoleRel(user: adminUser, role: adminRole).save()
         }

     }
     def destroy = {
     }
} 