package serviceinventory

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN', 'ROLE_SERVICE_INVENTORY_USERS'])
class ServerController {

    ServerService serverService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        params.sort = "name"
        respond serverService.list(params)
    }

    def show(Long id) {
        respond serverService.get(id)
    }

    def save(Server server) {
        if (server == null) {
            render status: NOT_FOUND
            return
        }

        try {
            serverService.save(server)
        } catch (ValidationException e) {
            respond server.errors, view:'create'
            return
        }

        respond server, [status: CREATED, view:"show"]
    }

    def update(Server server) {
        if (server == null) {
            render status: NOT_FOUND
            return
        }

        try {
            serverService.save(server)
        } catch (ValidationException e) {
            respond server.errors, view:'edit'
            return
        }

        respond server, [status: OK, view:"show"]
    }

    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        serverService.delete(id)

        render status: NO_CONTENT
    }

    def endOfLife() {
        def max = params.int('max') ?: 5
        def c = Server.createCriteria()
        def result = c.list {
            eq("active", true)
            lte("endOfLife", new Date() + 365)
            order("endOfLife", "asc")
        }.take(max)

        respond result, [status: OK, view:"index"]
    }

}
