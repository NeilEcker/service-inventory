package serviceinventory

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN', 'ROLE_SERVICE_INVENTORY_USERS'])
class ReferenceTypeController {

    ReferenceTypeService referenceTypeService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        respond referenceTypeService.list(), model:[referenceTypeCount: referenceTypeService.count()]
    }

    def show(Long id) {
        respond referenceTypeService.get(id)
    }

    def save(ReferenceType referenceType) {
        if (referenceType == null) {
            render status: NOT_FOUND
            return
        }

        try {
            referenceTypeService.save(referenceType)
        } catch (ValidationException e) {
            respond referenceType.errors, view:'create'
            return
        }

        respond referenceType, [status: CREATED, view:"show"]
    }

    def update(ReferenceType referenceType) {
        if (referenceType == null) {
            render status: NOT_FOUND
            return
        }

        try {
            referenceTypeService.save(referenceType)
        } catch (ValidationException e) {
            respond referenceType.errors, view:'edit'
            return
        }

        respond referenceType, [status: OK, view:"show"]
    }

    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        referenceTypeService.delete(id)

        render status: NO_CONTENT
    }
}
