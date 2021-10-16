<template>
  <div>
      <b-card
              title="Applications by Service Account"
              class="mb-12"
      >
          <table class="table table-sm table-striped table-hover">
              <thead>
              <tr>
                  <th>Service Account</th>
                  <th>Application(s)</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="account in accounts">
                  <td>{{ account.name }}</td>
                  <td>
                      <b-row v-for="application in account.applications" :key="application.id">
                          <b-col>
                              <router-link :to="{ path: '/applications/show/' + application.id}">
                                  {{ application.name }}
                              </router-link>
                          </b-col>
                          <b-col>
                              {{ application.description }}
                          </b-col>
                      </b-row>
                  </td>
              </tr>
              </tbody>
          </table>
      </b-card>
    </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import ApplicationApiService from '@/services/application.api';

@Component
export default class ApplicationsByServiceAccount extends Vue {

  private accounts: any[] = [];

  private async mounted() {
     await ApplicationApiService.getApplicationsByServiceAccount()
      .then((response) => (this.accounts = response.data));
  }
}
</script>
