export default {
    items: [
        {
            name: 'Dashboard',
            url: '/',
            icon: 'icon-speedometer',
        },
        {
            divider: true
        },
        {
            name: 'Applications',
            url: '/applications',
            icon: 'icon-layers'
        },
        {
            name: 'Servers',
            url: '/servers',
            icon: 'icon-screen-desktop'
        },
        {
            name: 'Change Log',
            url: '/change',
            icon: 'icon-pencil'
        },
        {
            name: 'Projects',
            url: '/projects',
            icon: 'icon-list'
        },
        {
            name: 'Vendors',
            url: '/vendors',
            icon: 'icon-user'
        },
        {
            name: 'Staff',
            url: '/staff',
            icon: 'icon-user'
        },
        {
            divider: true
        },
        {
            name: 'Queries',
            url: '/query',
            icon: 'icon-list',
            children: [
                {
                    name: 'Database',
                    url: '/query/databases',
                    icon: 'icon-puzzle'
                },
                {
                    name: 'Service Account',
                    url: '/query/serviceAccount',
                    icon: 'icon-puzzle'
                },
                {
                    name: 'Business Unit',
                    url: '/query/businessUnit',
                    icon: 'icon-puzzle'
                },
            ]
        },
    ]
};