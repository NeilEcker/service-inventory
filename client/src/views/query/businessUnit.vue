<template>
  <div>
      <b-card
              title="Applications by Business Unit"
              class="mb-12"
      >
          <table class="table table-sm table-striped table-hover">
              <thead>
              <tr>
                  <th>Business Unit</th>
                  <th>Application(s)</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="businessUnit in businessUnits">
                  <td>{{ businessUnit.name }}</td>
                  <td>
                      <b-row v-for="application in businessUnit.applications" :key="application.id">
                          <b-col sm="4">
                              <router-link :to="{ path: '/applications/show/' + application.id}">
                                  {{ application.name }}
                              </router-link>
                          </b-col>
                          <b-col sm="8">
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
export default class ApplicationsByBusinessUnit extends Vue {

  private businessUnits: any[] = [];

  private async mounted() {
     await ApplicationApiService.getApplicationsByBusinessUnit()
      .then((response) => (this.businessUnits = response.data));
  }
}
</script>
