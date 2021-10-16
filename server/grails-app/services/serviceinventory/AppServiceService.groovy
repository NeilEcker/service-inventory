package serviceinventory

import grails.gorm.services.Service

@Service(AppService)
interface AppServiceService {

    AppService get(Serializable id)

    List<AppService> list(Map args)

    Long count()

    void delete(Serializable id)

    AppService save(AppService appService)

}