package serviceinventory

class AppService {

    String name
    String type
    String location
    String platform
    String description
    String url

    Vendor vendor
    Division division
    String tag //Alfresco tag
    String notes
    List<Server> productionServers
    List<Server> stagingServers
    List<String> databaseNames
    List<String> serviceAccounts

    boolean decommission = false
    boolean active = true

    Date implemented
    Date decommissioned
    Date nextReview

    Date dateCreated
    Date lastUpdated

    static hasMany = [references: Reference, supportStaff: Staff, businessUnits: BusinessUnit, dependsOn: AppService]

    static constraints = {
        type inList: ['Commercial', 'Custom']
        location nullable: true, inList: ['Local', 'Cloud']
        platform nullable: true, inList: ['Web', 'Desktop', 'Service']
        description nullable: true
        url nullable:true, url:true
        vendor nullable: true
        division nullable: true
        tag nullable:true
        notes nullable:true, type:'text'
        implemented nullable: true
        decommissioned nullable: true
        nextReview nullable: true
        productionServers nullable:true
        stagingServers nullable:true
        databaseNames nullable:true
        serviceAccounts nullable:true
    }

    String toString() { name }
}
