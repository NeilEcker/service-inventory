import Vue from 'vue';
import Router from 'vue-router';
import Home from './views/Home.vue';
import store from '@/store';
const Login = () => import('@/views/Login.vue');
const Application = () => import('@/views/applications/index.vue');
const ApplicationShow = () => import('@/views/applications/show.vue');
const ApplicationCreate = () => import('@/views/applications/create.vue');
const Servers = () => import('@/views/servers/index.vue');
const ServersShow = () => import('@/views/servers/show.vue');
const ServersCreate = () => import('@/views/servers/create.vue');
const ChangeLog = () => import('@/views/change/index.vue');
const ChangeShow = () => import('@/views/change/show.vue');
const ChangeCreate = () => import('@/views/change/create.vue');
const Projects = () => import('@/views/projects/index.vue');
const ProjectsShow = () => import('@/views/projects/show.vue');
const ProjectsCreate = () => import('@/views/projects/create.vue');
const Vendors = () => import('@/views/vendors/index.vue');
const VendorsShow = () => import('@/views/vendors/show.vue');
const VendorsCreate = () => import('@/views/vendors/create.vue');
const Staff = () => import('@/views/staff/index.vue');
const StaffShow = () => import('@/views/staff/show.vue');
const StaffCreate = () => import('@/views/staff/create.vue');
const Bubble = () => import('@/views/charts/bubble.vue');
const Gantt = () => import('@/views/charts/gantt.vue');
const Org = () => import('@/views/charts/org.vue');
const Search = () => import('@/views/search/index.vue');
const ApplicationsByDatabase = () => import('@/views/query/databases.vue');
const ApplicationsByServiceAccount = () => import('@/views/query/serviceAccount.vue');
const ApplicationsByBusinessUnit = () => import('@/views/query/businessUnit.vue');

Vue.use(Router);

const router = new Router({
  mode: 'hash', // https://router.vuejs.org/api/#mode
  linkActiveClass: 'open active',
  routes: [
    {
        path: '/login',
        name: 'Login',
        component: Login,
    },
    {
      path: '/',
      name: 'Home',
      component: Home,
      meta: {
          requiresAuth: true,
        },
    },
    {
      path: '/about',
      name: 'About',
      component: () => import(/* webpackChunkName: "about" */ './views/About.vue'),
    },
    {
      path: '/applications',
      name: 'Applications',
      component: Application,
      meta: {
        requiresAuth: true,
      },
    },
    {
      path: '/applications/show/:id',
      name: 'Show Application',
      component: ApplicationShow,
      meta: {
          requiresAuth: true,
        },
    },
    {
      path: '/applications/create',
      name: 'Create Application',
      component: ApplicationCreate,
      meta: {
          requiresAuth: true,
        },
    },
    {
      path: '/servers',
      name: 'Servers',
      component: Servers,
      meta: {
          requiresAuth: true,
        },
    },
    {
      path: '/servers/show/:id',
      name: 'Show Server',
      component: ServersShow,
      meta: {
          requiresAuth: true,
        },
    },
    {
      path: '/servers/create',
      name: 'Create Server',
      component: ServersCreate,
      meta: {
          requiresAuth: true,
        },
    },
    {
      path: '/change',
      name: 'Change Log',
      component: ChangeLog,
      meta: {
          requiresAuth: true,
        },
    },
    {
      path: '/change/show/:id',
      name: 'Show Change',
      component: ChangeShow,
      meta: {
          requiresAuth: true,
        },
    },
    {
      path: '/change/create',
      name: 'Create Change',
      component: ChangeCreate,
      meta: {
          requiresAuth: true,
        },
    },
    {
      path: '/projects',
      name: 'Projects',
      component: Projects,
      meta: {
          requiresAuth: true,
        },
    },
    {
      path: '/projects/show/:id',
      name: 'Show Project',
      component: ProjectsShow,
      meta: {
          requiresAuth: true,
        },
    },
    {
      path: '/projects/create',
      name: 'Create Project',
      component: ProjectsCreate,
      meta: {
          requiresAuth: true,
        },
    },
    {
      path: '/vendors',
      name: 'Vendors',
      component: Vendors,
      meta: {
          requiresAuth: true,
        },
    },
    {
      path: '/vendors/show/:id',
      name: 'Show Vendor',
      component: VendorsShow,
      meta: {
          requiresAuth: true,
        },
    },
    {
      path: '/vendors/create',
      name: 'Create Vendor',
      component: VendorsCreate,
      meta: {
          requiresAuth: true,
        },
    },
    {
      path: '/staff',
      name: 'Staff',
      component: Staff,
      meta: {
          requiresAuth: true,
        },
    },
    {
      path: '/staff/show/:id',
      name: 'Show Staff Member',
      component: StaffShow,
      meta: {
          requiresAuth: true,
        },
    },
    {
      path: '/staff/create',
      name: 'Create Staff Member',
      component: StaffCreate,
      meta: {
          requiresAuth: true,
        },
    },
    {
      path: '/charts/bubble',
      name: 'Bubble',
      component: Bubble,
      meta: {
          requiresAuth: true,
        },
    },
    {
      path: '/charts/gantt',
      name: 'Projects Gantt Chart',
      component: Gantt,
      meta: {
          requiresAuth: true,
        },
    },
    {
      path: '/charts/org',
      name: 'Org',
      component: Org,
      meta: {
          requiresAuth: true,
        },
    },
    {
      path: '/search',
      name: 'Search',
      component: Search,
      meta: {
          requiresAuth: true,
        },
    },
    {
      path: '/query/databases',
      name: 'Databases',
      component: ApplicationsByDatabase,
      meta: {
          requiresAuth: true,
        },
    },
    {
      path: '/query/serviceAccount',
      name: 'Service Account',
      component: ApplicationsByServiceAccount,
      meta: {
        requiresAuth: true,
      },
    },
    {
      path: '/query/businessUnit',
      name: 'Business Unit',
      component: ApplicationsByBusinessUnit,
      meta: {
        requiresAuth: true,
      },
    },
  ],
});

router.beforeEach((to, from, next) => {
  if (to.matched.some((record) => record.meta.requiresAuth)) {
    if (store.getters.isLoggedIn) {
      next();
      return;
    }
    next('/login') ;
  } else {
    next();
  }
});

export default router;
