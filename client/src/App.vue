<template>
  <div class="app">
    <AppHeader fixed>
      <SidebarToggler class="d-lg-none" display="md" mobile />
      <b-link class="navbar-brand" to="/">
        <!--<img class="navbar-brand-full" src="img/brand/logo.svg" width="89" height="25" alt="Services">-->
        <span class="navbar-brand-full">SERVICES</span>
        <span class="navbar-brand-minimized"><i class="icons icon-paper-plane"></i></span>
        <!--<img class="navbar-brand-minimized" src="img/brand/sygnet.svg" width="30" height="30" alt="Navbar">-->
      </b-link>
      <SidebarToggler class="d-md-down-none" display="lg" />
      <b-navbar-nav class="ml-auto">
        <b-nav-item class="d-md-down-none">
          <i class="icon-list"></i>
        </b-nav-item>
        <HeaderDropdown/>
      </b-navbar-nav>
    </AppHeader>
    <div class="app-body">
      <AppSidebar fixed>
        <SidebarHeader/>
        <SidebarForm/>
        <SidebarNav :navItems="nav"></SidebarNav>
        <SidebarFooter/>
        <SidebarMinimizer/>
      </AppSidebar>
      <main class="main">
        <Breadcrumb :list="list"/>
        <div class="container-fluid">
          <transition name="fade" mode="out-in">
            <router-view></router-view>
          </transition>
        </div>
      </main>
    </div>
    <TheFooter>
      <!--footer-->
      <div>
        <a href="https://www.grey.ca">Grey County</a>
        <span class="ml-1">&copy; 2019</span>
      </div>
      <div class="ml-auto">
      </div>
    </TheFooter>
  </div>
</template>

<script>
import nav from '@/_nav';
import { Header as AppHeader, SidebarToggler, Sidebar as AppSidebar,
  SidebarFooter, SidebarForm, SidebarHeader, SidebarMinimizer, SidebarNav,
  Footer as TheFooter, Breadcrumb } from '@coreui/vue';
import HeaderDropdown from '@/components/HeaderDropdown.vue';


export default {
  name: 'DefaultContainer',
  components: {
    AppHeader,
    AppSidebar,
    TheFooter,
    Breadcrumb,
    SidebarForm,
    SidebarFooter,
    SidebarToggler,
    SidebarHeader,
    SidebarNav,
    SidebarMinimizer,
    HeaderDropdown,
  },
  data() {
    return {
      nav: nav.items,
    };
  },
  computed: {
    name() {
      return this.$route.name;
    },
    list() {
      return this.$route.matched.filter((route) => route.name || route.meta.label );
    },
  },
};
</script>

<style lang="scss">
  // CoreUI Icons Set
  @import '~@coreui/icons/css/coreui-icons.min.css';
  /* Import Font Awesome Icons Set */
  /*$fa-font-path: '~font-awesome/fonts/';
  @import '~font-awesome/scss/font-awesome.scss';*/
  /* Import Simple Line Icons Set */
  $simple-line-font-path: '~simple-line-icons/fonts/';
  @import '~simple-line-icons/scss/simple-line-icons.scss';
  /* Import Flag Icons Set */
  @import '~flag-icon-css/css/flag-icon.min.css';
  /* Import Bootstrap Vue Styles */
  @import '~bootstrap-vue/dist/bootstrap-vue.css';
  // Import Main styles for this application
  @import 'assets/scss/style';
</style>

<style lang="css" scoped>
  .navbar {
    padding-top: 0px;
  }
</style>