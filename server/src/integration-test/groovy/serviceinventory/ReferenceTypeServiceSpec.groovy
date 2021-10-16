package serviceinventory

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ReferenceTypeServiceSpec extends Specification {

    ReferenceTypeService referenceTypeService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ReferenceType(...).save(flush: true, failOnError: true)
        //new ReferenceType(...).save(flush: true, failOnError: true)
        //ReferenceType referenceType = new ReferenceType(...).save(flush: true, failOnError: true)
        //new ReferenceType(...).save(flush: true, failOnError: true)
        //new ReferenceType(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //referenceType.id
    }

    void "test get"() {
        setupData()

        expect:
        referenceTypeService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ReferenceType> referenceTypeList = referenceTypeService.list(max: 2, offset: 2)

        then:
        referenceTypeList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        referenceTypeService.count() == 5
    }

    void "test delete"() {
        Long referenceTypeId = setupData()

        expect:
        referenceTypeService.count() == 5

        when:
        referenceTypeService.delete(referenceTypeId)
        sessionFactory.currentSession.flush()

        then:
        referenceTypeService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ReferenceType referenceType = new ReferenceType()
        referenceTypeService.save(referenceType)

        then:
        referenceType.id != null
    }
}
