package serviceinventory

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import grails.gorm.transactions.Transactional
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse

@Transactional
class DocumentService implements GrailsConfigurationAware {

    Boolean enabled
    String restUrl
    String userId
    String password

    @Override
    void setConfiguration(Config co) {
        enabled = co.getProperty('alfresco.enabled', Boolean, false)
        restUrl = co.getProperty('alfresco.restUrl', String, 'https://devdocs.grey.ca/alfresco/api/-default-/public')
        userId = co.getProperty('alfresco.userId', String)
        password = co.getProperty('alfresco.password', String)
    }

    def search(String name) {

        if (enabled) {
            RestBuilder rest = new RestBuilder()
            String url = "${restUrl}/search/versions/1/search"

            String token = "${userId}:${password}".encodeAsBase64()

            def query = """
            {
              "query": {
                "query": "name:(${name}) AND TAG:'wiki'"
              },
              "include": ["aspectNames", "properties"]
            }
        """

            RestResponse restResponse = rest.post(url) {
                auth("Basic ${token}")
                json(query)
            }

            return restResponse.json.list?.entries.collect { it.entry }
        } else {
            return []
        }
    }

    def searchByTag(String tag) {

        if (enabled) {
            RestBuilder rest = new RestBuilder()
            String url = "${restUrl}/search/versions/1/search"

            String token = "${userId}:${password}".encodeAsBase64()

            def query = """
            {
              "query": {
                "query": "TAG:'${tag}'"
              },
              "include": ["aspectNames", "properties"]
            }
        """

            RestResponse restResponse = rest.post(url) {
                auth("Basic ${token}")
                json(query)
            }

            return restResponse.json.list?.entries.sort { it.entry.name }.collect { it.entry }
        } else {
            return []
        }
    }

    def getContent(String node) {
        RestBuilder rest = new RestBuilder()
        String url = "${restUrl}/alfresco/versions/1/nodes/${node}/content"

        String token = "${userId}:${password}".encodeAsBase64()

        RestResponse restResponse = rest.get(url) {
            auth("Basic ${token}")
        }

        return restResponse
    }

}
