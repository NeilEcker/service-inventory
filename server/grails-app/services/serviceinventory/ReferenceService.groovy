package serviceinventory

import grails.gorm.services.Service

@Service(Reference)
interface ReferenceService {

    Reference get(Serializable id)

    List<Reference> list(Map args)

    Long count()

    void delete(Serializable id)

    Reference save(Reference reference)

}