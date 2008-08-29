import org.jsecurity.crypto.hash.Sha1Hash

class BootStrap {

     def init = { servletContext ->

         if (!JsecUser.findByUsername('admin')) {
             println "Creating Admin User and Role"
             def adminRole = new JsecRole(name: 'admin').save()
             def adminUser = new JsecUser(username: 'admin', passwordHash: new Sha1Hash("admin").toHex()).save()
             new JsecUserRoleRel(user: adminUser, role: adminRole).save()
         }

         if (!JsecRole.findByName('user')) {
             println "Creating General User Role"
             new JsecRole(name: 'user').save()
         }
     
     }


     def destroy = {
     }
} 