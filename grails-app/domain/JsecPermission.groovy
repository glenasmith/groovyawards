class JsecPermission {
    String type
    String possibleActions

    static constraints = {
        type(nullable: false, blank: false, unique: true)
        possibleActions(nullable:false, blank: false)
    }
}
