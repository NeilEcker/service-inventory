package serviceinventory

import grails.gorm.services.Service

@Service(Change)
interface ChangeRequestService {

    Change get(Serializable id)

    List<Change> list(Map args)

    Long count()

    void delete(Serializable id)

    Change save(Change changeRequest)

}