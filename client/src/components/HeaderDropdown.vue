<template>
  <AppHeaderDropdown right>
    <template slot="header">
      {{ username }}
    </template>\
    <template slot="dropdown">
      <b-dropdown-header tag="div" class="text-center"><strong>Account</strong></b-dropdown-header>
      <b-dropdown-divider />
      <b-dropdown-item @click.prevent="logout"><i class="fa fa-lock" /> Logout</b-dropdown-item>
    </template>
  </AppHeaderDropdown>
</template>

<script>
import { HeaderDropdown as AppHeaderDropdown } from '@coreui/vue';
export default {
  name: 'HeaderDropdown',
  components: {
    AppHeaderDropdown,
  },
  computed: {
    isLoggedIn() {
      return this.$store.state.isLoggedIn;
    },
    username() {
      return this.$store.state.username;
    },
  },
  methods: {
    logout() {
       this.$store.dispatch('logout')
        .then(() => this.$router.push('/login'));
    },
  },
};
</script>