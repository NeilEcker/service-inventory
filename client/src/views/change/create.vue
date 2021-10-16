<template>
  <div class="mt-2">
    <b-row>
      <b-col sm="6">
        <b-card header="New Change" footer-tag="footer">
            <b-form @submit.prevent="save()">
                <b-form-group label="Description" label-for="description" horizontal>
                  <b-input id="description" v-model="change.description" required />
                </b-form-group>
                <b-form-group label="Details" label-for="details" horizontal>
                    <b-form-textarea id="details" v-model="change.details" rows="4" />
                </b-form-group>
                <b-form-group label="Staff" horizontal>
                  <b-form-select id="staffSelect" v-model="change.staff" :options="staffMembers" text-field="name" value-field="id" required />
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
                <b-form-group id="dateCompleted" label="Date Planned / Completed" label-for="dateCompletedPicker" horizontal>
                    <vuejs-datepicker id="dateCompletedPicker" name="dateCompleted" v-model="change.dateCompleted" :format="customFormatter"></vuejs-datepicker>
                </b-form-group>
                <div slot="footer" class="text-center">
                    <b-button type="submit" variant="primary" class="btn-sm mx-2">Save</b-button>
                </div>
          </b-form>
        </b-card>
      </b-col>
    </b-row>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import Multiselect from 'vue-multiselect';
import Datepicker from 'vuejs-datepicker';
import moment from 'moment';
import { mapState } from 'vuex';
import ChangeApiService from '@/services/change.api';

Vue.component('multiselect', Multiselect);
Vue.component('vuejs-datepicker', Datepicker);

Vue.filter('formatDate', (value: string) => {
  if (value) {
    return moment(String(value)).format('YYYY-MM-DD');
  }
});

@Component({
    computed: mapState(['appServices', 'servers', 'staffMembers', 'divisions']),
})
export default class ApplicationCreate extends Vue {
  private change: any = new Object();
  private priorityOptions: string[] = ['Low', 'Medium', 'High'];
  private statusOptions: string[] = ['Pending', 'Approved', 'Denied'];
  private communicationOptions: string[] = ['None', 'All Employees', 'Affected Users'];
  private outcomeOptions: string[] = ['Success', 'Rolled Back', 'Rescheduled'];
  private dateOptions: any = {
    format: 'YYYY-MM-DD',
    useCurrent: false,
  };
  private dateFormat: string = 'YYYY-MM-DD';

  private async mounted() {
    this.loadData();
  }

  private async loadData() {
    this.$store.dispatch('fetchStaffMembers');
    this.$store.dispatch('fetchAppServices');
    this.$store.dispatch('fetchServers');
    this.$store.dispatch('fetchDivisions');
  }

  private customFormatter(date: Date) {
    return moment(date).format('YYYY-MM-DD');
  }

  private async save() {
    ChangeApiService.saveChange(this.change)
      .then((response) => {
        this.change = response.data;
        Vue.toasted.show('Change Request Saved');
        this.$router.push({ path: '/change/show/' + this.change.id });
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
</style>