package serviceinventory

class ChangeApproval {

    String priority
    String risks

    Date datePlanned
    String estimatedUsers
    String estimatedDuration

    String communicationRequired = "None"
    String status = "Pending"

    Staff approver
    String outcome

    Date dateCreated
    Date lastUpdated

    static belongsTo = [change: Change]

    static constraints = {
        priority inList:['Low','Medium','High']
        risks nullable:true
        estimatedUsers nullable:true
        estimatedDuration nullable:true
        communicationRequired inList:['None', 'All Employees', 'Affected Users']
        status inList:['Pending', 'Approved', 'Denied']
        approver nullable:true
        outcome nullable:true, inList:['Success', 'Rolled Back', 'Rescheduled']
    }
}
