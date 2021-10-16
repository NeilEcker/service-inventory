<template>
  <div>
      <b-row>
        <b-col v-if="searchPaneOpen" cols="3">
          <b-card>
            <b-button size="sm" :to="{ path: '/applications/create' }"><span class="icon icon-plus"></span> New Application</b-button>
            <hr />
            <b-form-group label="Keyword" label-for="keyword" label-cols="4" horizontal>
              <b-input id="keyword" v-model="filter" placeholder="Type to filter table" />
            </b-form-group>
            <hr />
            <p><small>Advanced</small></p>
            <b-form-group label="Name" label-for="name" label-cols="4" horizontal>
              <b-input id="name" v-model="search.name" />
            </b-form-group>
            <b-form-group id="division" label="Division" label-for="divisionSelect" label-cols="4" horizontal>
              <b-form-select id="divisionSelect" v-model="search.division" :options="divisionOptions" />
            </b-form-group>
            <b-form-group id="type" label="Type" label-for="typeSelect" label-cols="4" horizontal>
              <b-form-select v-model="search.type" :options="typeOptions" />
            </b-form-group>
            <b-form-group id="location" label="Location" label-for="locationSelect" label-cols="4" horizontal>
              <b-form-select id="locationSelect" v-model="search.location" :options="locationOptions" />
            </b-form-group>
            <b-form-group id="platform" label="Platform" label-for="platformSelect" label-cols="4" horizontal>
              <b-form-select id="platformSelect" v-model="search.platform" :options="platformOptions" />
            </b-form-group>
            <b-form-group label="Database Name" label-for="databaseNames" label-cols="4" horizontal>
              <b-input v-model="search.databaseNames" />
            </b-form-group>
            <b-form-group label="Active" label-for="activeSelect" label-cols="4" horizontal>
              <b-form-select id="activeSelect" v-model="search.active" :options="checkboxOptions" />
            </b-form-group>
            <b-form-group label="Decommission" label-for="decommissionSelect" label-cols="4" horizontal>
              <b-form-select id="decommissionSelect" v-model="search.decommission" :options="checkboxOptions" />
            </b-form-group>
            <br />
            <div class="text-center">
              <b-btn size="sm" @click="resetSearch"><span class="icon icon-magnifier-remove"></span> Clear Filters</b-btn>
            </div>
          </b-card>
        </b-col>
        <b-col>
          <div class="card card-accent-primary">
            <b-table
              :sort-by.sync="sortBy"
              :sort-desc.sync="sortDesc"
              responsive
              small
              striped
              outlined
              :items="filteredApplications"
              :fields="fields"
              :filter="filter">
              <template #cell(name)="data">
                <router-link :to="{ path: '/applications/show/' + data.item.id}">
                  {{data.value}}
                </router-link>
              </template>
              <template #cell(supportStaff)="data">
                <ul>
                  <li v-for="staff in data.value" :key="staff.id">{{ staff.name }}</li>
                </ul>
              </template>
              <template #cell(details)="data">
                <div class="text-center">
                  <b-btn @click.stop="data.toggleDetails" class="btn-sm">
                    <i class="icons icon-arrow-up" v-if="data.detailsShowing"></i>
                    <i class="icons icon-arrow-down" v-if="!data.detailsShowing"></i>
                  </b-btn>
                </div>
              </template>
              <template #row-details="data">
                <b-card>
                  <b-row>
                    <b-col>
                      <b-row>
                        <b-col sm="4" class="text-sm-right"><b>Depends on:</b></b-col>
                        <b-col>
                          <span v-for="dependsOn in data.item.dependsOn" :key="dependsOn.id">{{ dependsOn.name }}<br /></span>
                        </b-col>
                      </b-row>
                      <b-row>
                        <b-col sm="4" class="text-sm-right"><b>Support Staff:</b></b-col>
                        <b-col>
                          <span v-for="staff in data.item.supportStaff" :key="staff.id">{{ staff.name }}<br /></span>
                        </b-col>
                      </b-row>
                      <b-row>
                        <b-col sm="4" class="text-sm-right"><b>Database Name(s):</b></b-col>
                        <b-col>
                          <span v-for="databaseName in data.item.databaseNames" :key="databaseName">{{ databaseName }}<br /></span>
                        </b-col>
                      </b-row>
                    </b-col>
                    <b-col>
                      <b-row>
                        <b-col sm="4" class="text-sm-right"><b>Business Units:</b></b-col>
                        <b-col>
                          <span v-for="businessUnit in data.item.businessUnits" :key="businessUnit.id">{{ businessUnit.name }}<br /></span>
                        </b-col>
                      </b-row>
                      <b-row>
                        <b-col sm="4" class="text-sm-right"><b>Type:</b></b-col>
                        <b-col>
                          <span v-if="data.item.type">{{ data.item.type }}</span>
                        </b-col>
                      </b-row>
                      <b-row>
                        <b-col sm="4" class="text-sm-right"><b>Location:</b></b-col>
                        <b-col>
                          <span v-if="data.item.location">{{ data.item.location }}</span>
                        </b-col>
                      </b-row>
                      <b-row>
                        <b-col sm="4" class="text-sm-right"><b>Division:</b></b-col>
                        <b-col>
                          <span v-if="data.item.division">{{ data.item.division.name }}</span>
                        </b-col>
                      </b-row>
                      <b-row>
                        <b-col sm="4" class="text-sm-right"><b>Production Server(s):</b></b-col>
                        <b-col>
                          <span v-for="server in data.item.productionServers" :key="server.id">{{ server.name }}<br /></span>
                        </b-col>
                      </b-row>
                      <b-row>
                          <b-col sm="4" class="text-sm-right"><b>Staging Server(s):</b></b-col>
                          <b-col>
                            <span v-for="server in data.item.stagingServers" :key="server.id">{{ server.name }}<br /></span>
                          </b-col>
                        </b-row>
                    </b-col>
                  </b-row>
                </b-card>
              </template>
            </b-table>
          </div>
          </b-col>
      </b-row>
    </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { mapState } from 'vuex';
import ApiService from '@/services/api';

@Component({
  computed: mapState(['appServices', 'divisionOptions']),
})
export default class ApplicationIndex extends Vue {

  private sortBy: string = 'name';
  private sortDesc: boolean = false;
  private fields: any[] = [
    { key: 'name', sortable: true },
    { key: 'division.name', sortable: true },
    { key: 'description', sortable: true },
    'details',
  ];
  private locationOptions: string[] = ['', 'Local', 'Cloud'];
  private typeOptions: string[] = ['', 'Commercial', 'Custom'];
  private platformOptions: string[] = ['', 'Web', 'Desktop', 'Service'];
  private checkboxOptions = [{text: '', value: null}, {text: 'Yes', value: true}, {text: 'No', value: false}];
  private filter: string = '';
  private searchPaneOpen: boolean = true;
  private search: any = {
    name: '',
    type: null,
    division: null,
    location: null,
    platform: null,
    databaseNames: '',
    decommission: null,
    active: true,
  };
  private test: string = '';

  private async mounted() {
    this.$store.dispatch('fetchAppServices');
    this.$store.dispatch('fetchDivisions');
  }

  private resetSearch() {
    this.filter = '';
    this.search = {
      name: '',
      type: null,
      division: null,
      location: null,
      platform: null,
      databaseNames: '',
      decommission: null,
      active: true,
    };
  }

  // computed
  get filteredApplications() {
    let filtered: any = this.$store.getters.appServices;
    if (this.search.name) {
      filtered = filtered.filter(
        (m: any) => m.name.toLowerCase().indexOf(this.search.name.toLowerCase()) > -1,
      );
    }
    if (this.search.type) {
      filtered = filtered.filter(
        (m: any) => m.type === this.search.type,
      );
    }
    if (this.search.location) {
      filtered = filtered.filter(
        (m: any) => m.location === this.search.location,
      );
    }
    if (this.search.division) {
      filtered = filtered.filter(
        (m: any) => m.division && m.division.id === this.search.division,
      );
    }
    if (this.search.platform) {
      filtered = filtered.filter(
        (m: any) => m.platform === this.search.platform,
      );
    }
    if (this.search.decommission != null) {
      filtered = filtered.filter(
        (m: any) => m.decommission === this.search.decommission,
      );
    }
    if (this.search.active != null) {
      filtered = filtered.filter(
        (m: any) => m.active === this.search.active,
      );
    }
    if (this.search.databaseNames) {
      filtered = filtered.filter(
        (m: any) => m.databaseNames.find((el: string) => el.toUpperCase() === this.search.databaseNames.toUpperCase()),
      );
    }
    return filtered;
  }
}
</script>
