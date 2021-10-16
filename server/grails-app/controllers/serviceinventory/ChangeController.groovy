package serviceinventory

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN', 'ROLE_SERVICE_INVENTORY_USERS'])
class ChangeController {

    ChangeService changeService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        respond changeService.list(), model:[changeCount: changeService.count()]
    }

    def show(Long id) {
        respond changeService.get(id)
    }

    def save(Change change) {
        if (change == null) {
            render status: NOT_FOUND
            return
        }

        try {
            changeService.save(change)
        } catch (ValidationException e) {
            respond change.errors, view:'create'
            return
        }

        respond change, [status: CREATED, view:"show"]
    }

    def update(Change change) {
        if (change == null) {
            render status: NOT_FOUND
            return
        }

        try {
            changeService.save(change)
        } catch (ValidationException e) {
            respond change.errors, view:'edit'
            return
        }

        respond change, [status: OK, view:"show"]
    }

    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        changeService.delete(id)

        render status: NO_CONTENT
    }

    def changes(AppService appService) {
        def c = Change.createCriteria()
        def result = c.list {
            servicesAffected {
                idEq(appService.id)
            }
        }
        respond result, [status: OK, view:"index"]
    }

    def serverChanges(Server server) {
        def c = Change.createCriteria()
        def result = c.list {
            serversAffected {
                idEq(server.id)
            }
        }
        respond result, [status: OK, view:"index"]
    }

    /**
     * Return recent changes
     * @return
     */
    def recent() {
        def max = params.int('max') ?: 5
        def c = Change.createCriteria()
        def result = c.list {
            lte("dateCompleted", new Date())
            order("dateCompleted", "desc")
        }.take(max)
        respond result, [status: OK, view:"index"]
    }

    /**
     * Return upcoming changes
     * @return
     */
    def upcoming() {
        def max = params.int('max') ?: 5
        def c = Change.createCriteria()
        def result = c.list {
            gt("dateCompleted", new Date())
            order("dateCompleted", "asc")
        }.take(max)
        respond result, [status: OK, view:"index"]
    }
}
