package serviceinventory

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class BusinessUnitServiceSpec extends Specification {

    BusinessUnitService businessUnitService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new BusinessUnit(...).save(flush: true, failOnError: true)
        //new BusinessUnit(...).save(flush: true, failOnError: true)
        //BusinessUnit businessUnit = new BusinessUnit(...).save(flush: true, failOnError: true)
        //new BusinessUnit(...).save(flush: true, failOnError: true)
        //new BusinessUnit(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //businessUnit.id
    }

    void "test get"() {
        setupData()

        expect:
        businessUnitService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<BusinessUnit> businessUnitList = businessUnitService.list(max: 2, offset: 2)

        then:
        businessUnitList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        businessUnitService.count() == 5
    }

    void "test delete"() {
        Long businessUnitId = setupData()

        expect:
        businessUnitService.count() == 5

        when:
        businessUnitService.delete(businessUnitId)
        sessionFactory.currentSession.flush()

        then:
        businessUnitService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        BusinessUnit businessUnit = new BusinessUnit()
        businessUnitService.save(businessUnit)

        then:
        businessUnit.id != null
    }
}
