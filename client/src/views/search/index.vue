<template>
  <div>
    <div>
      <b-row>
        <b-col class="my-2">
          <b-card header="Search for Applications / Services">
            <b-form @submit.prevent="search()">
                <b-form-group label="Search" label-for="search" horizontal>
                    <b-input id="search" v-model="query" required />
                </b-form-group>
            </b-form>
          </b-card>
        </b-col>
        <b-col class="my-2" cols="8">
          <b-card header="Results">
            <b-table
                  :sort-by.sync="sortBy"
                  :sort-desc.sync="sortDesc"
                  responsive
                  small
                  striped
                  outlined
                  :items="results"
                  :fields="fields"
                  :filter="filter">
                  <template slot="name" slot-scope="row">
                    <router-link :to="{ path: '/applications/show/' + row.item.id}">
                      {{row.value}}
                    </router-link>
                  </template>
                  <template slot="supportStaff" slot-scope="row">
                    <ul>
                      <li v-for="staff in row.value" :key="staff.id">{{ staff.name }}</li>
                    </ul>
                  </template>
                </b-table>
          </b-card>
        </b-col>
      </b-row>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import moment from 'moment';
import axios from 'axios';
import ApplicationApiService from '@/services/application.api';

Vue.filter('formatDate', (value: string) => {
  if (value) {
    return moment(String(value)).format('YYYY-MM-DD');
  }
});

@Component
export default class ServerIndex extends Vue {
  private query: string = '';
  private results: any[] = [];
  private sortBy: string = 'name';
    private sortDesc: boolean = false;
    private fields: any[] = [
      { key: 'name', sortable: true },
      { key: 'type', sortable: true },
      { key: 'location', sortable: true },
      { key: 'description', sortable: true },
      'details',
    ];
    private filter: string = '';

  private async search() {
     await ApplicationApiService.search(this.query)
       .then((response) => {
         this.results = response.data;
       })
       .catch((error) => {
         Vue.toasted.show(error.response);
       });
  }
}
</script>
