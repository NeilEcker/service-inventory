package serviceinventory

import grails.gorm.services.Service

@Service(ProjectType)
interface ProjectTypeService {

    ProjectType get(Serializable id)

    List<ProjectType> list(Map args)

    Long count()

    void delete(Serializable id)

    ProjectType save(ProjectType projectType)

}