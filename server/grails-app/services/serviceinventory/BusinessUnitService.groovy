package serviceinventory

import grails.gorm.services.Service

@Service(BusinessUnit)
interface BusinessUnitService {

    BusinessUnit get(Serializable id)

    List<BusinessUnit> list(Map args)

    Long count()

    void delete(Serializable id)

    BusinessUnit save(BusinessUnit businessUnit)

}