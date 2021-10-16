package serviceinventory

import grails.util.Environment

class UrlMappings {

    static mappings = {

        "/api/vendor"(resources:"article")
        "/api/appService"(resources:'appService') {
            "/reference"(resources:'reference')
        }
        get "/api/appService/servicesByServer/$id"(controller:'appService',action:'servicesByServer')
        get "/api/appService/servicesByVendor/$id"(controller:'appService',action:'servicesByVendor')
        get "/api/appService/reviewDue"(controller:'appService',action:'reviewDue')
        get "/api/appService/search"(controller:'appService',action:'search')
        get "/api/appService/$id/dependents"(controller:'appService',action:'dependents')
        get "/api/appService/applicationsByDatabase"(controller:'appService',action:'applicationsByDatabase')
        get "/api/appService/applicationsByServiceAccount"(controller:'appService',action:'applicationsByServiceAccount')
        get "/api/appService/applicationsByBusinessUnit"(controller:'appService',action:'applicationsByBusinessUnit')

        get "/api/change/$id/changes"(controller:'change',action:'changes')
        get "/api/change/$id/serverChanges"(controller:'change',action:'serverChanges')
        get "/api/change/recent"(controller:'change',action:'recent')
        get "/api/change/upcoming"(controller:'change',action:'upcoming')
        get "/api/reference/referencesByAppService/$id"(controller:'reference',action:'referencesByAppService')
        get "/api/project/gantt"(controller:'project',action:'gantt')
        get "/api/project/myCurrentProjects"(controller:'project',action:'myCurrentProjects')
        get "/api/server/endOfLife"(controller:'server',action:'endOfLife')
        post "/api/appService/$id/reference"(controller:'reference',action:'save')

        get "/api/document/search"(controller:'document',action:'search')
        get "/api/document/content"(controller:'document',action:'getContent')
        get "/api/document/searchByTag"(controller:'document',action:'searchByTag')

        /* Default mappings */
        delete "/api/$controller/$id(.$format)?"(action:"delete")
        get "/api/$controller(.$format)?"(action:"index")
        get "/api/$controller/$id(.$format)?"(action:"show")
        post "/api/$controller(.$format)?"(action:"save")
        put "/api/$controller/$id(.$format)?"(action:"update")
        patch "/api/$controller/$id(.$format)?"(action:"patch")

        if ( Environment.current == Environment.PRODUCTION ) {
            '/'(uri: '/index.html')
        } else {
            '/'(controller: 'application', action:'index')
        }
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
