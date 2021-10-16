package serviceinventory

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class AppServiceServiceSpec extends Specification {

    AppServiceService appServiceService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new AppService(...).save(flush: true, failOnError: true)
        //new AppService(...).save(flush: true, failOnError: true)
        //AppService appService = new AppService(...).save(flush: true, failOnError: true)
        //new AppService(...).save(flush: true, failOnError: true)
        //new AppService(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //appService.id
    }

    void "test get"() {
        setupData()

        expect:
        appServiceService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<AppService> appServiceList = appServiceService.list(max: 2, offset: 2)

        then:
        appServiceList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        appServiceService.count() == 5
    }

    void "test delete"() {
        Long appServiceId = setupData()

        expect:
        appServiceService.count() == 5

        when:
        appServiceService.delete(appServiceId)
        sessionFactory.currentSession.flush()

        then:
        appServiceService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        AppService appService = new AppService()
        appServiceService.save(appService)

        then:
        appService.id != null
    }
}
