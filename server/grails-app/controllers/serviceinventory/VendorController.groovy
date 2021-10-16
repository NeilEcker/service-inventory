package serviceinventory

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN', 'ROLE_SERVICE_INVENTORY_USERS'])
class VendorController {

    VendorService vendorService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        respond vendorService.list()
    }

    def show(Long id) {
        respond vendorService.get(id)
    }

    def save(Vendor vendor) {
        if (vendor == null) {
            render status: NOT_FOUND
            return
        }

        try {
            vendorService.save(vendor)
        } catch (ValidationException e) {
            respond vendor.errors, view:'create'
            return
        }

        respond vendor, [status: CREATED, view:"show"]
    }

    def update(Vendor vendor) {
        if (vendor == null) {
            render status: NOT_FOUND
            return
        }

        try {
            vendorService.save(vendor)
        } catch (ValidationException e) {
            respond vendor.errors, view:'edit'
            return
        }

        respond vendor, [status: OK, view:"show"]
    }

    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        vendorService.delete(id)

        render status: NO_CONTENT
    }
}
