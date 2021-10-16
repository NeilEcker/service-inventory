package serviceinventory

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ChangeServiceSpec extends Specification {

    ChangeRequestService changeRequestService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Change(...).save(flush: true, failOnError: true)
        //new Change(...).save(flush: true, failOnError: true)
        //Change changeRequest = new Change(...).save(flush: true, failOnError: true)
        //new Change(...).save(flush: true, failOnError: true)
        //new Change(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //changeRequest.id
    }

    void "test get"() {
        setupData()

        expect:
        changeRequestService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Change> changeRequestList = changeRequestService.list(max: 2, offset: 2)

        then:
        changeRequestList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        changeRequestService.count() == 5
    }

    void "test delete"() {
        Long changeRequestId = setupData()

        expect:
        changeRequestService.count() == 5

        when:
        changeRequestService.delete(changeRequestId)
        sessionFactory.currentSession.flush()

        then:
        changeRequestService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Change changeRequest = new Change()
        changeRequestService.save(changeRequest)

        then:
        changeRequest.id != null
    }
}
