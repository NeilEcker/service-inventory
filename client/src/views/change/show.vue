<template>
  <div class="mt-2">
    <b-row v-if="change">
      <b-col sm="7">
        <b-card header-tag="header" footer-tag="footer">
          <strong slot="header">Change</strong>
          <div v-if="!editChange">
            <b-row class="mb-2">
              <b-col md="4" lg="3" class="text-sm-right"><b>Description:</b></b-col>
              <b-col>{{ change.description }}</b-col>
            </b-row>
            <b-row class="mb-2">
              <b-col md="4" lg="3" class="text-sm-right"><b>Details:</b></b-col>
              <b-col><pre>{{ change.details }}</pre></b-col>
            </b-row>
            <b-row class="mb-2">
              <b-col md="4" lg="3" class="text-sm-right"><b>Staff:</b></b-col>
              <b-col>{{ change.staff.name }}</b-col>
            </b-row>
            <b-row class="mb-2">
              <b-col md="4" lg="3" class="text-sm-right"><b>Division:</b></b-col>
              <b-col><span v-if="change.division">{{ change.division.name }}</span></b-col>
            </b-row>
            <b-row class="mb-2">
              <b-col md="4" lg="3" class="text-sm-right"><b>Ticket URL:</b></b-col>
              <b-col><span v-if="change.ticketUrl"><a :href="change.ticketUrl" target="_blank">{{ change.ticketUrl }}</a></span></b-col>
            </b-row>
            <b-row class="mb-2">
              <b-col md="4" lg="3" class="text-sm-right"><b>Wiki URL:</b></b-col>
              <b-col><span v-if="change.wikiUrl"><a :href="change.wikiUrl" target="_blank">{{ change.wikiUrl }}</a></span></b-col>
            </b-row>
            <b-row class="mb-2">
              <b-col md="4" lg="3" class="text-sm-right"><b>Services Affected:</b></b-col>
              <b-col>
                <ul>
                  <li v-for="service in change.servicesAffected" :key="service.id">
                    <router-link :to="{ path: '/applications/show/' + service.id}">{{ service.name }}</router-link></li>
                </ul>
              </b-col>
            </b-row>
            <b-row class="mb-2">
              <b-col md="4" lg="3" class="text-sm-right"><b>Servers Affected:</b></b-col>
              <b-col>
                <ul>
                  <li v-for="server in change.serversAffected" :key="server.id">
                    <router-link :to="{ path: '/servers/show/' + server.id}">{{ server.name }}</router-link>
                  </li>
                </ul>
              </b-col>
            </b-row>
            <b-row class="mb-2">
              <b-col md="4" lg="3" class="text-sm-right"><b>Date Completed:</b></b-col>
              <b-col>{{ change.dateCompleted | formatDate }}</b-col>
            </b-row>
          </div>

          <b-form v-if="editChange">
            <b-form-group label="Description" label-for="description" horizontal>
              <b-input id="description" v-model="change.description" placeholder="Short description" />
            </b-form-group>
            <b-form-group label="Details" label-for="details" horizontal>
              <b-form-textarea id="details" v-model="change.details" rows="4"/>
            </b-form-group>
            <b-form-group label="Staff" horizontal>
              <multiselect v-model="change.staff" :options="staffMembers" placeholder="Select Staff Member" label="name" track-by="id" :close-on-select="false"></multiselect>
            </b-form-group>
            <b-form-group id="division" label="Division" label-for="divisionSelect" horizontal>
              <select class="form-control" name="divisionSelect" v-model="change.division">
                <option value=""></option>
                <option v-for="division in divisions" :key="division.id" :value="division">
                  {{ division.name }}
                </option>
              </select>
            </b-form-group>
            <b-form-group label="Ticket URL" label-for="url" horizontal>
              <b-input type="url" id="url" v-model="change.ticketUrl" />
            </b-form-group>
            <b-form-group label="Wiki URL" label-for="url" horizontal>
              <b-input type="url" id="url" v-model="change.wikiUrl" />
            </b-form-group>
            <b-form-group label="Services Affected" horizontal>
              <multiselect v-model="change.servicesAffected" :options="appServices" :multiple="true" placeholder="Select Services" label="name" track-by="id" :close-on-select="false"></multiselect>
            </b-form-group>
            <b-form-group label="Servers Affected" horizontal>
              <multiselect v-model="change.serversAffected" :options="servers" :multiple="true" placeholder="Select Servers" label="name" track-by="id" :close-on-select="false"></multiselect>
            </b-form-group>
            <b-form-group id="dateCompleted" label="Date Completed" label-for="dateCompletedPicker" horizontal>
              <vuejs-datepicker id="dateCompletedPicker" name="dateCompleted" v-model="change.dateCompleted" :format="customFormatter"></vuejs-datepicker>
            </b-form-group>
          </b-form>
          <div slot="footer" class="text-center">
            <b-button @click="toggleEditChange()" class="btn-sm mx-2">
              {{ editChange ? 'Cancel' : 'Edit' }}
            </b-button>
            <b-button @click="update()" variant="primary" class="btn-sm mx-2" v-if="editChange">Submit</b-button>
          </div>
        </b-card>
      </b-col>

      <b-col sm="5">

        <div v-if="change.approval">
          <div v-if="editApproval">
            <b-form @submit.prevent="updateApproval">
              <b-card header-tag="header" footer-tag="footer">
                <strong slot="header">Approval</strong>

                <ApprovalForm :approval="change.approval"></ApprovalForm>
                <div slot="footer" class="text-center">
                  <b-button type="submit" variant="primary" class="btn-sm mx-2">Save</b-button>
                  <b-button @click="toggleEditApproval()" class="btn-sm mx-2">Cancel</b-button>
                </div>

              </b-card>
            </b-form>
          </div>
          <div v-else>
            <b-card header-tag="header" footer-tag="footer">
              <strong slot="header">Approval</strong>
              <b-row class="mb-2">
                <b-col cols="4" class="text-sm-right"><b>Date Planned:</b></b-col>
                <b-col>{{ change.approval.datePlanned | formatDate }}</b-col>
              </b-row>
              <b-row class="mb-2">
                <b-col cols="4" class="text-sm-right"><b>Priority:</b></b-col>
                <b-col>{{ change.approval.priority }}</b-col>
              </b-row>
              <b-row class="mb-2">
                <b-col cols="4" class="text-sm-right"><b>Communication:</b></b-col>
                <b-col>{{ change.approval.communicationRequired }}</b-col>
              </b-row>
              <b-row class="mb-2">
                <b-col cols="4" class="text-sm-right"><b>Approver:</b></b-col>
                <b-col>
                  <span v-if="change.approval.approver">{{ change.approval.approver.name }}</span>
                </b-col>
              </b-row>
              <b-row class="mb-2">
                <b-col cols="4" class="text-sm-right"><b>Status:</b></b-col>
                <b-col>{{ change.approval.status }}</b-col>
              </b-row>
              <b-row class="mb-2">
                <b-col cols="4" class="text-sm-right"><b>Outcome:</b></b-col>
                <b-col>{{ change.approval.outcome }}</b-col>
              </b-row>
              <div slot="footer" class="text-center">
                <b-button @click="toggleEditApproval()" class="btn-sm mx-2">Edit</b-button>
                <b-button @click="deleteApproval()" variant="danger" class="btn-sm mx-2" v-if="!editChange">Delete</b-button>
              </div>
            </b-card>
          </div>
        </div>
        <div v-else>
          <div v-if="createApproval">
            <b-form @submit.prevent="saveApproval()">
              <b-card header-tag="header" footer-tag="footer">
                <strong slot="header">Approval</strong>
                <ApprovalForm :approval="newApproval"></ApprovalForm>
                <div slot="footer" class="text-center">
                  <b-button type="submit" class="btn-sm mx-2">Save</b-button>
                  <b-button @click="toggleCreateApproval()" class="btn-sm mx-2">Cancel</b-button>
                </div>
              </b-card>
            </b-form>
          </div>
          <div v-else>
            <b-card header-tag="header" footer-tag="footer">
              <strong slot="header">Approval</strong>
              <div class="text-center">
                <b-button @click="toggleCreateApproval()" class="btn-sm mx-2">Add Approval</b-button>
              </div>
            </b-card>
          </div>
        </div>

      </b-col>
    </b-row>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Watch } from 'vue-property-decorator';
import Multiselect from 'vue-multiselect';
import Datepicker from 'vuejs-datepicker';
import ApprovalForm from '@/components/ApprovalForm.vue';
import { mapState } from 'vuex';
import moment from 'moment';
import ChangeApiService from '@/services/change.api';

Vue.component('multiselect', Multiselect);
Vue.component('vuejs-datepicker', Datepicker);

Vue.filter('formatDate', (value: string) => {
  if (value) {
    return moment(String(value)).format('YYYY-MM-DD');
  }
});

@Component({
  components: {
    ApprovalForm,
  },
  computed: mapState(['staffMembers', 'appServices', 'servers', 'divisions']),
})
export default class Application extends Vue {
  private change: any = null;
  private message: string = '';
  private editChange: boolean = false;
  private createApproval: boolean = false;
  private editApproval: boolean = false;

  private newApproval: any = { };
  private dateOptions: any = {
    format: 'YYYY-MM-DD',
    useCurrent: false,
  };
  private dateFormat: string = 'YYYY-MM-DD';

  @Watch('$route')
  private onPropertyChanged(value: string, oldValue: string) {
    this.loadData();
  }

  private async mounted() {
    this.loadData();
  }

  private async loadData() {
    await ChangeApiService.getChange(this.$route.params.id)
      .then((response) => (this.change = response.data));
    this.$store.dispatch('fetchServers');
    this.$store.dispatch('fetchAppServices');
    this.$store.dispatch('fetchStaffMembers');
    this.$store.dispatch('fetchDivisions');
  }

  private toggleEditChange() {
    this.loadData();
    this.editChange = !this.editChange;
  }

  private toggleCreateApproval() {
    this.newApproval = { change: this.change.id, datePlanned: new Date(), status: 'Pending' };
    this.createApproval = !this.createApproval;
  }

  private toggleEditApproval() {
    this.editApproval = !this.editApproval;
  }

  private customFormatter(date: Date) {
    return moment(date).format('YYYY-MM-DD');
  }

  private async update() {
    ChangeApiService.updateChange(this.change.id, this.change)
      .then((response) => {
        this.change = response.data;
        Vue.toasted.show('Change Saved');
        this.editChange = false;
      })
      .catch((error) => {
        Vue.toasted.show(error.response);
      });
  }

  private async saveApproval() {
    this.change.approval = this.newApproval;
    ChangeApiService.updateChange(this.change.id, this.change)
        .then((response) => {
             this.change = response.data;
             Vue.toasted.show('Change Saved');
             this.createApproval = false;
        })
        .catch((error) => {
            Vue.toasted.show(error.response);
        });
  }

  private async updateApproval() {
      ChangeApiService.updateChange(this.change.id, this.change)
          .then((response) => {
               this.change = response.data;
               Vue.toasted.show('Change Saved');
               this.editApproval = false;
          })
          .catch((error) => {
              Vue.toasted.show(error.response);
          });
    }

    private async deleteApproval() {
      this.change.approval = null;
      ChangeApiService.updateChange(this.change.id, this.change)
          .then((response) => {
               this.change = response.data;
               Vue.toasted.show('Approval Deleted');
          })
          .catch((error) => {
              Vue.toasted.show(error.response);
          });
    }
}
</script>

<style src="vue-multiselect/dist/vue-multiselect.min.css"></style>

<style scoped>
ul {
  padding-inline-start: 0;
}
li {
  list-style: none;
}
pre {
    white-space: pre-wrap;       /* Since CSS 2.1 */
    white-space: -moz-pre-wrap;  /* Mozilla, since 1999 */
    white-space: -pre-wrap;      /* Opera 4-6 */
    white-space: -o-pre-wrap;    /* Opera 7 */
    word-wrap: break-word;       /* Internet Explorer 5.5+ */
}
</style>