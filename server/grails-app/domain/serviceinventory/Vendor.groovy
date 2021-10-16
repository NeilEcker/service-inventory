package serviceinventory

class Vendor {

    String name
    String url
    String contact
    String notes

    static constraints = {
        url nullable: true
        contact nullable: true
        notes nullable: true, type:'text'
    }

    String toString() { name }
}
