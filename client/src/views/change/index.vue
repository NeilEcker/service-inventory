<template>
   <div class="card card-accent-primary">
        <div class="card-header">
          <b-row>
            <b-col md="8" class="my-2">
              <b-button size="sm" :to="{ path: '/change/create' }"><span class="icon icon-plus"></span> New Change</b-button>
            </b-col>
            <b-col md="4" class="my-2">
                <b-input-group>
                  <b-form-input v-model="filter" placeholder="Type to Filter" />
                  <b-input-group-append>
                    <b-btn :disabled="!filter" @click="filter = ''">Clear</b-btn>
                  </b-input-group-append>
                </b-input-group>
            </b-col>
          </b-row>
        </div>
        <div class="card-body">
            <b-table
                :sort-by.sync="sortBy"
                :sort-desc.sync="sortDesc"
                responsive
                small
                striped
                outlined
                :items="changes"
                :fields="fields"
                :filter="filter">
                <template #cell(dateCreated)="data">
                  <router-link :to="{ path: '/change/show/' + data.item.id}">
                    {{ data.value | formatDate }}
                  </router-link>
                </template>
                <template #cell(staff)="data">
                    {{data.value.name}}
                </template>
                <template #cell(servicesAffected)="data">
                    <ul class="nopad">
                        <li v-for="service in data.value" :key="service.id" class="nowrap">
                            <span class="icon-settings" title="Service"></span>
                            <router-link :to="{ path: '/applications/show/' + service.id}">
                                {{ service.name }}
                            </router-link>
                        </li>
                    </ul>
                </template>
                <template #cell(serversAffected)="data">
                    <ul class="nopad">
                        <li v-for="server in data.value" :key="server.id" class="nowrap">
                            <span class="icon-screen-desktop" title="Server"></span>
                            <router-link :to="{ path: '/servers/show/' + server.id}">
                                {{ server.name }}
                            </router-link>
                        </li>
                    </ul>
                </template>
                <template #cell(dateCompleted)="data">
                    <router-link :to="{ path: '/change/show/' + data.item.id}">
                        {{ data.value | formatDate }}
                    </router-link>
                </template>
                <template #cell(approval)="data">
                    <span v-if="data.value">
                        {{data.value.status}}
                    </span>
                    <span v-else>N/A</span>
                </template>
            </b-table>
        </div>
    </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import moment from 'moment';
import { mapState } from 'vuex';
import ChangeApiService from '@/services/change.api';

Vue.filter('formatDate', (value: string) => {
  if (value) {
    return moment(String(value)).format('YYYY-MM-DD');
  }
});

@Component({
    computed: mapState(['changes']),
})
export default class ServerIndex extends Vue {

  private sortBy: string = 'dateCreated';
  private sortDesc: boolean = true;
  private fields: any[] = [
    { key: 'dateCreated', sortable: true },
    { key: 'staff', sortable: true },
    { key: 'division.name', sortable: true },
    { key: 'description' },
    { key: 'servicesAffected' },
    { key: 'serversAffected' },
    { key: 'dateCompleted', sortable: true },
    { key: 'approval' },
  ];
  private filter: string = '';

  private async mounted() {
    this.$store.dispatch('fetchChanges');
  }
}
</script>
