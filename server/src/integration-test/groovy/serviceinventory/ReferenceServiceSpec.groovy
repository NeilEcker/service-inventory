package serviceinventory

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ReferenceServiceSpec extends Specification {

    ReferenceService referenceService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Reference(...).save(flush: true, failOnError: true)
        //new Reference(...).save(flush: true, failOnError: true)
        //Reference reference = new Reference(...).save(flush: true, failOnError: true)
        //new Reference(...).save(flush: true, failOnError: true)
        //new Reference(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //reference.id
    }

    void "test get"() {
        setupData()

        expect:
        referenceService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Reference> referenceList = referenceService.list(max: 2, offset: 2)

        then:
        referenceList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        referenceService.count() == 5
    }

    void "test delete"() {
        Long referenceId = setupData()

        expect:
        referenceService.count() == 5

        when:
        referenceService.delete(referenceId)
        sessionFactory.currentSession.flush()

        then:
        referenceService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Reference reference = new Reference()
        referenceService.save(reference)

        then:
        reference.id != null
    }
}
