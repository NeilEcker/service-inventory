<template>
  <div>
    <b-form-group id="datePlanned" label="Date Planned" label-for="datePlannedPicker" label-cols="4" horizontal>
      <vuejs-datepicker id="datePlannedPicker" name="datePlanned" v-model="approval.datePlanned" :format="customFormatter"></vuejs-datepicker>
    </b-form-group>
    <b-form-group label="Priority" label-cols="4" horizontal>
      <b-form-select id="prioritySelect" v-model="approval.priority" :options="priorityOptions" required />
    </b-form-group>
    <b-form-group label="Communication" label-cols="4" horizontal>
      <b-form-select id="communicationSelect" v-model="approval.communicationRequired" :options="communicationOptions" required />
    </b-form-group>
    <b-form-group label="Approver" label-cols="4" horizontal>
      <multiselect v-model="approval.approver" :options="staffMembers" placeholder="Select Staff" label="name" track-by="id"></multiselect>
    </b-form-group>
    <b-form-group label="Status" label-cols="4" horizontal>
      <b-form-select id="statusSelect" v-model="approval.status" :options="statusOptions" required />
    </b-form-group>
    <b-form-group label="Outcome" label-cols="4" horizontal>
      <b-form-select id="outcomeSelect" v-model="approval.outcome" :options="outcomeOptions" />
    </b-form-group>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Watch, Prop } from 'vue-property-decorator';
import axios from 'axios';
import moment from 'moment';
import { mapState } from 'vuex';

@Component({
    computed: mapState(['staffMembers']),
})
export default class ApprovalForm extends Vue {
  @Prop() private approval?: any;
  private priorityOptions: string[] = ['Low', 'Medium', 'High'];
  private statusOptions: string[] = ['Pending', 'Approved', 'Denied'];
  private communicationOptions: string[] = ['None', 'All Employees', 'Affected Users'];
  private outcomeOptions: string[] = ['', 'Success', 'Rolled Back', 'Rescheduled'];

  private async mounted() {
    this.loadData();
  }

  private async loadData() {
    this.$store.dispatch('fetchStaffMembers');
  }

  private customFormatter(date: Date) {
    return moment(date).format('YYYY-MM-DD');
  }
}
</script>

<style src="vue-multiselect/dist/vue-multiselect.min.css"></style>
