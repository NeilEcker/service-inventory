package serviceinventory

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ServerServiceSpec extends Specification {

    ServerService serverService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Server(...).save(flush: true, failOnError: true)
        //new Server(...).save(flush: true, failOnError: true)
        //Server server = new Server(...).save(flush: true, failOnError: true)
        //new Server(...).save(flush: true, failOnError: true)
        //new Server(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //server.id
    }

    void "test get"() {
        setupData()

        expect:
        serverService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Server> serverList = serverService.list(max: 2, offset: 2)

        then:
        serverList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        serverService.count() == 5
    }

    void "test delete"() {
        Long serverId = setupData()

        expect:
        serverService.count() == 5

        when:
        serverService.delete(serverId)
        sessionFactory.currentSession.flush()

        then:
        serverService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Server server = new Server()
        serverService.save(server)

        then:
        server.id != null
    }
}
