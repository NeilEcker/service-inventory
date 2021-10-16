package serviceinventory

class Change {

    String description
    String details
    Staff staff
    Division division

    Date dateCompleted

    ChangeApproval approval

    String wikiUrl
    String ticketUrl

    Date dateCreated
    Date lastUpdated

    static hasMany = [servicesAffected: AppService, serversAffected: Server]

    static constraints = {
        details nullable:true, type:'text'
        dateCompleted nullable:true
        approval nullable:true
        division nullable:true
        wikiUrl nullable:true, url:true
        ticketUrl nullable:true, url:true
    }

    static mapping = {
        approval cascade: "all-delete-orphan"
    }
}
