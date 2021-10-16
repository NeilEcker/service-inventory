package serviceinventory

import grails.gorm.services.Service

@Service(ChangeApproval)
interface ChangeApprovalService {

    ChangeApproval get(Serializable id)

    List<ChangeApproval> list(Map args)

    Long count()

    void delete(Serializable id)

    ChangeApproval save(ChangeApproval changeApproval)

}