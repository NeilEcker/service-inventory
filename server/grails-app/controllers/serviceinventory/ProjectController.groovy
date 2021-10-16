package serviceinventory

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN', 'ROLE_SERVICE_INVENTORY_USERS'])
class ProjectController {

    ProjectService projectService
    def springSecurityService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        respond projectService.list(sort: "name")
    }

    def show(Long id) {
        respond projectService.get(id)
    }

    def save(Project project) {
        if (project == null) {
            render status: NOT_FOUND
            return
        }

        try {
            projectService.save(project)
        } catch (ValidationException e) {
            respond project.errors, view:'create'
            return
        }

        respond project, [status: CREATED, view:"show"]
    }

    def update(Project project) {
        if (project == null) {
            render status: NOT_FOUND
            return
        }

        try {
            projectService.save(project)
        } catch (ValidationException e) {
            respond project.errors, view:'edit'
            return
        }

        respond project, [status: OK, view:"show"]
    }

    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        projectService.delete(id)

        render status: NO_CONTENT
    }

    def gantt() {
        respond projectService.list()
    }

    /**
     * Find "in progress" projects assigned to me
     * @return
     */
    def myCurrentProjects() {
        def username = springSecurityService.principal.username

        def c = Project.createCriteria()
        def result = c.list {
            assignedTo {
                eq("username", username)
            }
            'in'("status", ['In Progress'])
            order("name", "asc")
        }

        respond result, [status: OK, view:"index"]

    }
}
