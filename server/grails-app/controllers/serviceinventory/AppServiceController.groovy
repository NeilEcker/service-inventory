package serviceinventory

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import org.springframework.http.HttpStatus

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN', 'ROLE_SERVICE_INVENTORY_USERS'])
class AppServiceController {

    AppServiceService appServiceService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        respond appServiceService.list(sort: "name"), model:[appServiceCount: appServiceService.count()]
    }

    def dependents(AppService appService) {
        def c = AppService.createCriteria()
        def result = c.list {
            dependsOn {
                idEq(appService.id)
            }
            order("name", "asc")
        }
        respond result, [status: OK, view:"index"]
    }

    def servicesByServer(Server server) {
        def result = []

        def productionCriteria = AppService.createCriteria()
        def production = productionCriteria.list {
            productionServers {
                idEq(server.id)
            }
        }

        def stagingCriteria = AppService.createCriteria()
        def staging = stagingCriteria.list {
            stagingServers {
                idEq(server.id)
            }
        }

        respond production + staging, [status: OK, view:"index"]
    }

    def servicesByVendor(Vendor vendor) {
        def result = AppService.findAllByVendor(vendor)
        respond result, [status: OK, view:"index"]
    }

    def reviewDue() {
        def c = AppService.createCriteria()
        def result = c.list {
            eq('active', true)
            or {
                isNull("nextReview")
                lt("nextReview", new Date())
            }
        }
        respond result, [status: OK, view:"index"]
    }

    def show(Long id) {
        respond appServiceService.get(id)
    }

    def save(AppService appService) {
        if (appService == null) {
            render status: NOT_FOUND
            return
        }

        try {
            appServiceService.save(appService)
        } catch (ValidationException e) {
            respond appService.errors, view:'create'
            return
        }

        respond appService, [status: CREATED, view:"show"]
    }

    def update(AppService appService) {
        if (appService == null) {
            render status: NOT_FOUND
            return
        }

        try {
            appServiceService.save(appService)
        } catch (ValidationException e) {
            respond appService.errors, view:'edit'
            return
        }

        respond appService, [status: OK, view:"show"]
    }

    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        appServiceService.delete(id)

        render status: NO_CONTENT
    }

    def search() {
        def result = AppService.findAllByNameIlike("%${params.query}%")

        respond result, [status: OK, view:"index"]
    }

    /**
     * Retrieve applications grouped by database name
     */
    def applicationsByDatabase() {
        def databases = [:]

        def activeApplications = AppService.findAllByActive(true)

        activeApplications.each { application ->
            application.databaseNames.each { databaseName ->
                //If key exists, add to existing otherwise add new
                if (databases[databaseName]) {
                    def applications = databases[databaseName].applications
                    applications << application
                    databases[databaseName] = [name: databaseName, applications: applications]
                } else {
                    databases.put(databaseName, [name: databaseName, applications: [application]])
                }

            }
        }

        def databaseArray = []
        databases.each {
            databaseArray << it.value
        }

        render databaseArray as JSON
    }

    /**
     * Retrieve applications grouped by service account name
     */
    def applicationsByServiceAccount() {
        def serviceAccounts = [:]

        def activeApplications = AppService.findAllByActive(true)

        activeApplications.each { application ->
            application.serviceAccounts.each { serviceAccount ->
                //If key exists, add to existing otherwise add new
                if (serviceAccounts[serviceAccount]) {
                    def applications = serviceAccounts[serviceAccount].applications
                    applications << application
                    serviceAccounts[serviceAccount] = [name: serviceAccount, applications: applications]
                } else {
                    serviceAccounts.put(serviceAccount, [name: serviceAccount, applications: [application]])
                }
            }
        }

        render serviceAccounts as JSON
    }

    /**
     * Retrieve applications grouped by business unit name
     */
    def applicationsByBusinessUnit() {
        def businessUnits = [:]

        def activeApplications = AppService.findAllByActive(true, [sort: "name"])

        activeApplications.each { application ->
            application.businessUnits.each { businessUnit ->
                //If key exists, add to existing otherwise add new
                if (businessUnits[businessUnit.name]) {
                    def applications = businessUnits[businessUnit.name].applications
                    applications << application
                    businessUnits[businessUnit.name] = [name: businessUnit.name, applications: applications]
                } else {
                    businessUnits.put(businessUnit.name, [name: businessUnit.name, applications: [application]])
                }

            }
        }

        def businessUnitArray = []
        businessUnits.each {
            businessUnitArray << it.value
        }

        render businessUnitArray.sort { it.name } as JSON
    }
}
