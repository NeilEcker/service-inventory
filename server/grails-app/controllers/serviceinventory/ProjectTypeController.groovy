package serviceinventory

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN', 'ROLE_SERVICE_INVENTORY_USERS'])
class ProjectTypeController {

    ProjectTypeService projectTypeService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond projectTypeService.list(params), model:[projectTypeCount: projectTypeService.count()]
    }

    def show(Long id) {
        respond projectTypeService.get(id)
    }

    def save(ProjectType projectType) {
        if (projectType == null) {
            render status: NOT_FOUND
            return
        }

        try {
            projectTypeService.save(projectType)
        } catch (ValidationException e) {
            respond projectType.errors, view:'create'
            return
        }

        respond projectType, [status: CREATED, view:"show"]
    }

    def update(ProjectType projectType) {
        if (projectType == null) {
            render status: NOT_FOUND
            return
        }

        try {
            projectTypeService.save(projectType)
        } catch (ValidationException e) {
            respond projectType.errors, view:'edit'
            return
        }

        respond projectType, [status: OK, view:"show"]
    }

    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        projectTypeService.delete(id)

        render status: NO_CONTENT
    }
}
