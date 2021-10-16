<template>
  <div>
    <b-row>
      <b-col v-if="searchPaneOpen" cols="3">
        <b-card>
          <b-button size="sm" :to="{ path: '/projects/create' }"><span class="icon icon-plus"></span> New Project</b-button>
          <b-button size="sm" :to="{ path: '/charts/gantt' }" class="mx-2">Gantt</b-button>
          <hr />
          <b-form-group label="Keyword" label-for="keyword" label-cols-sm="6" label-cols-lg="6" horizontal>
            <b-input id="keyword" v-model="filter" placeholder="Type to filter table" />
          </b-form-group>
          <hr />
          <p><small>Advanced</small></p>
          <b-form-group label="Name" label-for="name" horizontal>
            <b-input id="name" v-model="search.name" />
          </b-form-group>
          <b-form-group id="division" label="Division" label-for="divisionSelect" horizontal>
            <b-form-select id="divisionSelect" v-model="search.division" :options="divisionOptions" />
          </b-form-group>
          <b-form-group id="type" label="Project Type" label-for="projectTypeSelect" horizontal>
            <select class="form-control" name="projectTypeSelect" v-model="search.projectType" >
              <option value=""></option>
              <option v-for="projectType in projectTypes" :key="projectType.id" :value="projectType">
                {{ projectType.name }}
              </option>
            </select>
          </b-form-group>
          <b-form-group id="status" label="Status" label-for="statusSelect" horizontal>
            <b-form-select id="statusSelect" v-model="search.status" :options="statusOptions" multiple />
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
                :items="filteredProjects"
                :fields="fields"
                :filter="filter">
          <template #cell(name)="data">
            <router-link :to="{ path: '/projects/show/' + data.item.id}">
              {{data.value}}
            </router-link>
          </template>
          <template #cell(dateCreated)="data">
            {{data.value | formatDate }}
          </template>
          <template #cell(completion)="data">
            {{ data.value }}%
          </template>
          <template #cell(assignedTo)="data">
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
                    <b-col sm="4" class="text-sm-right"><b>Support Staff:</b></b-col>
                    <b-col>
                      <ul>
                        <li v-for="staff in data.item.assignedTo" :key="staff.id">{{ staff.name }}</li>
                      </ul>
                    </b-col>
                  </b-row>
                  <b-row>
                    <b-col sm="4" class="text-sm-right"><b>Business Units:</b></b-col>
                    <b-col>
                      <ul>
                        <li v-for="businessUnit in data.item.businessUnits" :key="businessUnit.id">{{ businessUnit.name }}</li>
                      </ul>
                    </b-col>
                  </b-row>
                </b-col>
                <b-col>
                  <b-row>
                    <b-col sm="4" class="text-sm-right"><b>Affected Server(s):</b></b-col>
                    <b-col>
                      <ul>
                        <li v-for="server in data.item.serversAffected" :key="server.id">{{ server.name }}</li>
                      </ul>
                    </b-col>
                  </b-row>
                  <b-row>
                    <b-col sm="4" class="text-sm-right"><b>Affected Service(s):</b></b-col>
                    <b-col>
                      <ul>
                        <li v-for="appService in data.item.servicesAffected" :key="server.id">{{ appService.name }}</li>
                      </ul>
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
import ProjectApi from '@/services/project.api';
import { mapState } from 'vuex';

@Component({
  computed: mapState(['projects', 'projectTypes', 'divisionOptions']),
})
export default class ProjectIndex extends Vue {

  private sortBy: string = 'name';
  private sortDesc: boolean = false;
  private fields: any[] = [
    { key: 'name', sortable: true },
    { key: 'division.name', sortable: true },
    { key: 'projectType.name', sortable: true },
    { key: 'dateCreated', sortable: true },
    { key: 'completion', sortable: true },
    { key: 'status', sortable: true },
    'details',
  ];
  private statusOptions: string[] = ['', 'Not Started', 'In Progress', 'Complete'];
  private filter: string = '';
  private searchPaneOpen: boolean = true;
  private search: any = {
    name: '',
    projectType: '',
    status: ['Not Started', 'In Progress'],
    division: null,
  };

  private async mounted() {
    this.$store.dispatch('fetchProjects');
    this.$store.dispatch('fetchProjectTypes');
    this.$store.dispatch('fetchDivisions');
  }

  private resetSearch() {
    this.filter = '';
    this.search = {
      name: '',
      projectType: '',
      status: ['Not Started', 'In Progress'],
      division: null,
    };
  }

  // computed
  get filteredProjects() {
    let filtered: any[] = this.$store.getters.projects;
    if (this.search.name) {
      filtered = filtered.filter(
        (m: any) => m.name.toLowerCase().indexOf(this.search.name.toLowerCase()) > -1,
      );
    }
    if (this.search.division) {
      filtered = filtered.filter(
        (m: any) => m.division && m.division.id === this.search.division,
      );
    }
    if (this.search.projectType !== '') {
      filtered = filtered.filter(
        (m: any) => m.projectType.id === this.search.projectType.id,
      );
    }
    if (this.search.status) {
      filtered = filtered.filter(
        (m: any) => this.search.status.indexOf(m.status) > -1,
      );
    }
    return filtered;
  }
}
</script>
