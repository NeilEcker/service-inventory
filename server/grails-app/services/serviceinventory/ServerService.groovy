package serviceinventory

import grails.gorm.services.Service

@Service(Server)
interface ServerService {

    Server get(Serializable id)

    List<Server> list(Map args)

    Long count()

    void delete(Serializable id)

    Server save(Server server)

}