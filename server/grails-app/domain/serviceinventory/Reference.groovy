package serviceinventory

class Reference {

    String url
    String description
    ReferenceType referenceType

    static belongsTo = [appService: AppService]

    static constraints = {
        description nullable:true
    }
}
