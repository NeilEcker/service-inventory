package serviceinventory

class Staff {

    String name
    String title
    String username
    String email

    boolean active = true

    static constraints = {
        title nullable: true
        email email:true
    }

    String toString() { name }
}
