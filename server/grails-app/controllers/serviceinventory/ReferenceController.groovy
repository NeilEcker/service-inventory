package serviceinventory

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN', 'ROLE_SERVICE_INVENTORY_USERS'])
class ReferenceController {

    ReferenceService referenceService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        respond referenceService.list(), model:[referenceCount: referenceService.count()]
    }

    def referencesByAppService(AppService appService) {
        respond Reference.findByAppService(appService)
    }


    def show(Long id) {
        respond referenceService.get(id)
    }

    def save(Reference reference) {

        if (reference == null) {
            render status: NOT_FOUND
            return
        }

        try {
            AppService appService = AppService.get(params.appServiceId)
            reference.appService = appService
            referenceService.save(reference)
        } catch (ValidationException e) {
            respond reference.errors, view:'create'
            return
        }

        respond reference, [status: CREATED, view:"show"]
    }

    def update(Reference reference) {
        if (reference == null) {
            render status: NOT_FOUND
            return
        }

        try {
            referenceService.save(reference)
        } catch (ValidationException e) {
            respond reference.errors, view:'edit'
            return
        }

        respond reference, [status: OK, view:"show"]
    }

    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        referenceService.delete(id)

        render status: NO_CONTENT
    }
}
