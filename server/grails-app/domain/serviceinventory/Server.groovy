package serviceinventory

class Server {

    String name
    String description
    String operatingSystem
    String staticAddress
    String notes
    Division division

    boolean active = true

    Date endOfLife
    Date nextReview

    Date dateCreated
    Date lastUpdated

    static constraints = {
        staticAddress nullable:true
        endOfLife nullable:true
        nextReview nullable:true
        notes nullable:true, type:'text'
        division nullable:true
    }
}
