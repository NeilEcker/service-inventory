<template>
  <div>
      <b-card
              title="Applications by Database"
              class="mb-12"
      >
          <b-table
                  :sort-by.sync="sortBy"
                  :sort-desc.sync="sortDesc"
                  responsive
                  small
                  striped
                  outlined
                  :items="databases"
                  :fields="fields"
                  :filter="filter">
              <template #cell(applications)="data">
                  <ul class="nopad">
                      <li v-for="application in data.value" :key="application.id" class="nowrap">
                          <router-link :to="{ path: '/applications/show/' + application.id}">
                              {{ application.name }}
                          </router-link>
                      </li>
                  </ul>
              </template>
              <template #cell(description)="data">
                  <ul class="nopad">
                      <li v-for="application in data.item.applications" :key="application.id" class="nowrap">
                          {{ application.description }}
                      </li>
                  </ul>
              </template>
          </b-table>
      </b-card>
    </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import ApplicationApiService from '@/services/application.api';

@Component
export default class ApplicationsByDatabase extends Vue {

  private sortBy: string = 'name';
  private sortDesc: boolean = false;
  private fields: any[] = [
    { key: 'name', sortable: true },
    { key: 'applications', sortable: true },
    { key: 'description' },
  ];
  private filter: string = '';
  private databases: any[] = [];

  private async mounted() {
     await ApplicationApiService.getApplicationsByDatabase()
      .then((response) => (this.databases = response.data));
  }
}
</script>
