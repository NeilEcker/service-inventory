package serviceinventory

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN', 'ROLE_SERVICE_INVENTORY_USERS'])
class BusinessUnitController {

    BusinessUnitService businessUnitService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        respond businessUnitService.list(sort: "name")
    }

    def show(Long id) {
        respond businessUnitService.get(id)
    }

    def save(BusinessUnit businessUnit) {
        if (businessUnit == null) {
            render status: NOT_FOUND
            return
        }

        try {
            businessUnitService.save(businessUnit)
        } catch (ValidationException e) {
            respond businessUnit.errors, view:'create'
            return
        }

        respond businessUnit, [status: CREATED, view:"show"]
    }

    def update(BusinessUnit businessUnit) {
        if (businessUnit == null) {
            render status: NOT_FOUND
            return
        }

        try {
            businessUnitService.save(businessUnit)
        } catch (ValidationException e) {
            respond businessUnit.errors, view:'edit'
            return
        }

        respond businessUnit, [status: OK, view:"show"]
    }

    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        businessUnitService.delete(id)

        render status: NO_CONTENT
    }
}
