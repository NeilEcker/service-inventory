<template>
  <div>
    <div>
      <b-row>
        <b-col>
          <b-row>
            <b-col class="my-2">
              <b-card header-tag="header">
                <strong slot="header">Recent / Upcoming Changes</strong>
                <table class="table table-hover table-striped table-sm">
                  <thead>
                  <tr>
                    <th>Change</th>
                    <th>Services Affected</th>
                    <th class="text-center">Date</th>
                  </tr>
                  </thead>
                  <tbody>
                  <tr v-for="change in changesRecent" :key="change.id">
                    <td>
                      <router-link :to="{ path: '/change/show/' + change.id}">
                        {{ change.description}}
                      </router-link>
                    </td>
                    <td>
                      <ul class="nopad">
                        <li v-for="service in change.servicesAffected" :key="service.id" class="nowrap">
                          <span class="icon-settings" title="Service"></span>
                          <router-link :to="{ path: '/applications/show/' + service.id}">
                            {{ service.name }}
                          </router-link>
                        </li>
                        <li v-for="server in change.serversAffected" :key="server.id" class="nowrap">
                          <span class="icon-screen-desktop" title="Server"></span>
                          <router-link :to="{ path: '/servers/show/' + server.id}">
                            {{ server.name }}
                          </router-link>
                        </li>
                      </ul>
                    </td>
                    <td class="text-center"><span class="badge badge-success" title="Recent Change">{{ change.dateCompleted | formatDate }}</span></td>
                  </tr>
                  <tr v-for="change in changesUpcoming" :key="change.id">
                    <td>
                      <router-link :to="{ path: '/change/show/' + change.id}">
                        {{ change.description}}
                      </router-link>
                    </td>
                    <td>
                      <ul class="nopad">
                        <li v-for="service in change.servicesAffected" :key="service.id" class="nowrap">
                          <span class="icon-settings" title="Service"></span>
                          <router-link :to="{ path: '/applications/show/' + service.id}">
                            {{ service.name }}
                          </router-link>
                        </li>
                        <li v-for="server in change.serversAffected" :key="server.id" class="nowrap">
                          <span class="icon-screen-desktop" title="Server"></span>
                          <router-link :to="{ path: '/servers/show/' + server.id}">
                            {{ server.name }}
                          </router-link>
                        </li>
                      </ul>
                    </td>
                    <td class="text-center"><span class="badge badge-info" title="Upcoming Change">{{ change.dateCompleted | formatDate }}</span></td>
                  </tr>
                  </tbody>
                </table>
              </b-card>
            </b-col>
          </b-row>
          <b-row>
            <b-col class="my-2">
              <b-card header-tag="header">
                <strong slot="header">My Current Projects</strong>
                <table class="table table-hover table-striped table-sm">
                  <thead>
                  <tr>
                    <th>Project</th>
                    <th>Type</th>
                    <th class="text-center">% Complete</th>
                    <th class="text-center">Planned Completion</th>
                  </tr>
                  </thead>
                  <tbody>
                  <tr v-for="project in myCurrentProjects" :key="project.id">
                    <td>
                      <router-link :to="{ path: '/projects/show/' + project.id}">
                        {{ project.name }}
                      </router-link>
                    </td>
                    <td>
                      {{ project.projectType.name }}
                    </td>
                    <td class="text-center">
                      {{ project.completion }}%
                    </td>
                    <td class="text-center">
                      <span class="badge badge-warning" v-if="project.endDate">{{ project.endDate | formatDate }}</span>
                    </td>
                  </tr>
                  </tbody>
                </table>
              </b-card>
            </b-col>
          </b-row>
        </b-col>

        <b-col>
          <b-row>
            <b-col>
            <b-card header-tag="header">
              <strong slot="header">Server End of Life</strong>
              <table class="table table-hover table-striped table-sm">
                <thead>
                <tr>
                  <th>Server</th>
                  <th>OS</th>
                  <th>End of Life</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="server in serversEndOfLife" :key="server.id">
                  <td>
                    <router-link :to="{ path: '/servers/show/' + server.id}">
                      {{ server.name }}
                    </router-link>
                  </td>
                  <td>{{ server.operatingSystem }}</td>
                  <td><span class="badge badge-warning">{{ server.endOfLife | formatDate }}</span></td>
                </tr>
                </tbody>
              </table>
            </b-card>
            </b-col>
          </b-row>
        </b-col>
      </b-row>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import moment from 'moment';
import axios from 'axios';
import api from '@/services/api';
import ChangeApi from '@/services/change.api';
import ServerApi from '@/services/server.api';
import ProjectApi from '@/services/project.api';

Vue.filter('formatDate', (value: string) => {
  if (value) {
    return moment(String(value)).format('YYYY-MM-DD');
  }
});

@Component
export default class ServerIndex extends Vue {
  private reviewDue: any[] = [];
  private serversEndOfLife: any[] = [];
  private changesRecent: any[] = [];
  private changesUpcoming: any[] = [];
  private myCurrentProjects: any[] = [];

  private async mounted() {
    await api.getReviewsDue()
      .then((response) => (this.reviewDue = response.data));
    await ChangeApi.getChangesRecent()
      .then((response) => (this.changesRecent = response.data));
    await ChangeApi.getChangesUpcoming()
      .then((response) => (this.changesUpcoming = response.data));
    await ServerApi.getServersEndOfLife()
      .then((response) => (this.serversEndOfLife = response.data));
    await ProjectApi.getMyCurrentProjects()
      .then((response) => (this.myCurrentProjects = response.data));
  }
}
</script>
