package serviceinventory

class Project {

    String name
    String description
    ProjectType projectType
    String status = "Not Started"
    String notes
    Integer completion = 0
    Division division

    boolean underPrioritization = false
    boolean capitalProject = false
    String projectForm

    static hasMany = [servicesAffected: AppService, serversAffected: Server, businessUnits: BusinessUnit, assignedTo: Staff]

    Date startDate
    Date endDate

    Date dateCreated
    Date lastUpdated

    static constraints = {
        status inList:['Not Started', 'In Progress', 'Complete']
        notes nullable:true, type:'text'
        completion min: 0, max: 100
        division nullable: true
        projectForm nullable:true, url:true
        startDate nullable:true
        endDate nullable:true
    }
}
