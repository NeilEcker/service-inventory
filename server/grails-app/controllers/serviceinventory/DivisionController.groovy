package serviceinventory

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN', 'ROLE_SERVICE_INVENTORY_USERS'])
class DivisionController {

    DivisionService divisionService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        respond divisionService.list(), model:[divisionCount: divisionService.count()]
    }

    def show(Long id) {
        respond divisionService.get(id)
    }

    def save(Division division) {
        if (division == null) {
            render status: NOT_FOUND
            return
        }

        try {
            divisionService.save(division)
        } catch (ValidationException e) {
            respond division.errors, view:'create'
            return
        }

        respond division, [status: CREATED, view:"show"]
    }

    def update(Division division) {
        if (division == null) {
            render status: NOT_FOUND
            return
        }

        try {
            divisionService.save(division)
        } catch (ValidationException e) {
            respond division.errors, view:'edit'
            return
        }

        respond division, [status: OK, view:"show"]
    }

    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        divisionService.delete(id)

        render status: NO_CONTENT
    }
}
