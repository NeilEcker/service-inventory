package serviceinventory

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN', 'ROLE_SERVICE_INVENTORY_USERS'])
class StaffController {

    StaffService staffService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        respond staffService.list(), model:[staffCount: staffService.count()]
    }

    def show(Long id) {
        respond staffService.get(id)
    }

    def save(Staff staff) {
        if (staff == null) {
            render status: NOT_FOUND
            return
        }

        try {
            staffService.save(staff)
        } catch (ValidationException e) {
            respond staff.errors, view:'create'
            return
        }

        respond staff, [status: CREATED, view:"show"]
    }

    def update(Staff staff) {
        if (staff == null) {
            render status: NOT_FOUND
            return
        }

        try {
            staffService.save(staff)
        } catch (ValidationException e) {
            respond staff.errors, view:'edit'
            return
        }

        respond staff, [status: OK, view:"show"]
    }

    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        staffService.delete(id)

        render status: NO_CONTENT
    }
}
