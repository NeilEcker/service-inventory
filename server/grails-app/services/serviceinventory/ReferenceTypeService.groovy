package serviceinventory

import grails.gorm.services.Service

@Service(ReferenceType)
interface ReferenceTypeService {

    ReferenceType get(Serializable id)

    List<ReferenceType> list(Map args)

    Long count()

    void delete(Serializable id)

    ReferenceType save(ReferenceType referenceType)

}