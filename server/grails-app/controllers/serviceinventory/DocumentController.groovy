package serviceinventory

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class DocumentController {

    DocumentService documentService

	static responseFormats = ['json']

    def search() {
        render documentService.search(params.q) as JSON
    }

    def searchByTag() {
        render documentService.searchByTag(params.tag) as JSON
    }

    def getContent() {
        render documentService.getContent(params.node).body
    }
}
