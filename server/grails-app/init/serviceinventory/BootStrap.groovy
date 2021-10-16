package serviceinventory

class BootStrap {

    def init = { servletContext ->

        Map saveMap = [failOnError:true, flush:true]

        if (UserAccount.count() == 0) {
            def adminRole = new Role(authority: 'ROLE_ADMIN').save()
            def userRole = new Role(authority: 'ROLE_SERVICE_INVENTORY_USERS').save()

            def testUser = new UserAccount(username: 'user', password: 'user').save()
            def adminUser = new UserAccount(username: 'admin', password: 'admin').save()

            UserAccountRole.create testUser, userRole
            UserAccountRole.create adminUser, adminRole

            UserAccountRole.withSession {
                it.flush()
                it.clear()
            }
        }

        assert UserAccount.count() == 2
        assert Role.count() == 2
        assert UserAccountRole.count() == 2

        if (BusinessUnit.list().size == 0) {
            def businessUnits = ['Clerks', 'Communication', 'Corporation', 'Finance',
                                 'Human Resources', 'Information Technology', 'Legal Services', 'Long Term Care',
                                 'Planning', 'Social Services', 'Tourism', 'Transportation']
            businessUnits.each { businessUnit ->
                new BusinessUnit(name: businessUnit).save(saveMap)
            }
        }

        if (Division.list().size == 0) {
            def divisions = ['Development', 'Quality Assurance', 'Networking']
            divisions.each { division ->
                new Division(name: division).save(saveMap)
            }
        }

        if (ProjectType.list().size == 0) {
            def projectTypes = ['Application Creation', 'Application Replacement', 'Application Enhancement', 'Infrastructure', 'Software Deployment', 'Process']
            projectTypes.each { projectType ->
                new ProjectType(name: projectType).save(saveMap)
            }
        }

        if (AppService.list().size == 0) {
            ['Microsoft', 'Oracle', 'Amazon'].each { vendor ->
                new Vendor(name: vendor).save(saveMap)
            }

            ['Reference', 'Website', 'Agreement', 'Ticket'].each { linkType ->
                new ReferenceType(name:  linkType).save(saveMap)
            }

            new Staff(name: 'Bill', username: 'user', email: 'bill@email.com').save(saveMap)
            new Staff(name: 'Sally', username: 'sally', email: 'sally@email.com').save(saveMap)
            new Staff(name: 'Joe', username: 'admin', email: 'joe@email.com').save(saveMap)

            def app1 = new Server(name: 'APP1', description: 'Application Server 1', operatingSystem: 'Ubuntu 18.04 LTS').save(saveMap)
            def app2 = new Server(name: 'APP2', description: 'Application Server 2', operatingSystem: 'Ubuntu 18.04 LTS').save(saveMap)
            def app3 = new Server(name: 'APP3', description: 'Application Server 3', operatingSystem: 'Ubuntu 16.04 LTS').save(saveMap)
            def app4 = new Server(name: 'APP4', description: 'Application Server 4', operatingSystem: 'Ubuntu 16.04 LTS').save(saveMap)
            def ts1 = new Server(name: 'TS1', description: 'Terminal Server 1', operatingSystem: 'Windows Server 2012 R2').save(saveMap)
            def db1 = new Server(name: 'DB1', description: 'Database Server 1', operatingSystem: 'CentOS 6.5').save(saveMap)
            def db2 = new Server(name: 'DB2', description: 'Database Server 2', operatingSystem: 'Windows Server 2012 R2').save(saveMap)
            def db3 = new Server(name: 'DB3', description: 'Database Server 3', operatingSystem: 'Windows Server 2012 R2').save(saveMap)

            def pgsql = new AppService(name: 'PostgreSQL', type: 'Commercial', location: 'Local', description: "PostgreSQL Databases",
                    businessUnits: [BusinessUnit.findWhere(name: "Corporation")], productionServers: [db1]).save(saveMap)
            def mssql = new AppService(name: 'Microsoft SQL', type: 'Commercial', location: 'Local', description: "MSSQL Databases",
                    businessUnits: [BusinessUnit.findWhere(name: "Corporation")], productionServers: [db2], stagingServers: [db3]).save(saveMap)
            def searchServices = new AppService(name: 'Search Services', type: 'Commercial', location: 'Local', description:'SOLR6 Index',
                    businessUnits: [BusinessUnit.findWhere(name: "Corporation")], productionServers: [app2]).save(saveMap)
            def contentServices = new AppService(name: 'Content Services', type: 'Commercial', location: 'Local', description:'Alfresco Share and Repository', serviceAccounts: ['LDAPReader'],
                    businessUnits: [BusinessUnit.findWhere(name: "Corporation")], productionServers: [app1], dependsOn: [searchServices, pgsql]).save(saveMap)
            def greatPlains = new AppService(name: 'ERP', type: 'Commercial', location: 'Local', description:'Financial System', databaseNames: ['DYNAMICS'],
                    businessUnits: [BusinessUnit.findWhere(name: "Corporation")], productionServers: [ts1], stagingServers: [db3], dependsOn: [mssql]).save(saveMap)
            def linkService = new AppService(name: 'Document Link Service', type: 'Custom', location: 'Local', description:'Handles public links for current and historical document management systems',
                    businessUnits: [BusinessUnit.findWhere(name: "Corporation")], productionServers: [app3], stagingServers: [app4], dependsOn: [contentServices]).save(saveMap)
            def intranet = new AppService(name: 'Intranet', type: 'Custom', location: 'Local', description:'Corporate intranet', databaseNames: 'intranet', serviceAccounts: ['LDAPReader'],
                    businessUnits: [BusinessUnit.findWhere(name: "Corporation")], productionServers: [app3], stagingServers: [app4], dependsOn: [contentServices, pgsql]).save(saveMap)

            new Change(staff:Staff.findByName('Bill'), description:'Switch proxy used from Apache to Nginx', servicesAffected:[linkService], serversAffected:[app3]).save(saveMap)
            new Change(staff:Staff.findByName('Bill'), description:'Upgrade from version 5.0.4 to 5.2.4', servicesAffected:[contentServices, searchServices],
                    approval: new ChangeApproval(priority:'Low', datePlanned: new Date(), estimatedDuration:'2 hours')).save(saveMap)

            new Reference(url: 'https://docs.alfresco.com', appService: contentServices, referenceType: ReferenceType.findByName("Reference")).save(saveMap)

            new Project(name:'Rebuild Postgres Server', description:'New Ubuntu LTS server, update PostgreSQL, migrate databases', projectType:ProjectType.findWhere(name:'Infrastructure'),
                    assignedTo:[Staff.findWhere(name:'Bill')], serversAffected:[db1]).save(saveMap)
            new Project(name:'HCM to Active Directory Synchronization Process', description:'Modernize directory update process', projectType:ProjectType.findWhere(name:'Process'),
                    assignedTo:[Staff.findWhere(name:'Bill')]).save(saveMap)
        }

    }
    def destroy = {
    }
}
