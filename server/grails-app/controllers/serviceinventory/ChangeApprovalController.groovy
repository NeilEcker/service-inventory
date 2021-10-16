package serviceinventory

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN', 'ROLE_SERVICE_INVENTORY_USERS'])
class ChangeApprovalController {

    ChangeApprovalService changeApprovalService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond changeApprovalService.list(params), model:[changeApprovalCount: changeApprovalService.count()]
    }

    def show(Long id) {
        respond changeApprovalService.get(id)
    }

    def save(ChangeApproval changeApproval) {
        if (changeApproval == null) {
            render status: NOT_FOUND
            return
        }

        try {
            changeApprovalService.save(changeApproval)
        } catch (ValidationException e) {
            respond changeApproval.errors, view:'create'
            return
        }

        respond changeApproval, [status: CREATED, view:"show"]
    }

    def update(ChangeApproval changeApproval) {
        if (changeApproval == null) {
            render status: NOT_FOUND
            return
        }

        try {
            changeApprovalService.save(changeApproval)
        } catch (ValidationException e) {
            respond changeApproval.errors, view:'edit'
            return
        }

        respond changeApproval, [status: OK, view:"show"]
    }

    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        changeApprovalService.delete(id)

        render status: NO_CONTENT
    }
}
