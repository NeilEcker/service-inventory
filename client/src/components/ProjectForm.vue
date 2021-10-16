<template>
  <div>
    <b-form-group label="Name" label-for="name" horizontal>
      <b-input id="name" v-model="project.name" :value="project.name" required />
    </b-form-group>
    <b-form-group label="Description" label-for="description" horizontal>
      <b-input id="description" v-model="project.description" :value="project.description" required />
    </b-form-group>
    <b-form-group id="type" label="Project Type" label-for="projectTypeSelect" horizontal>
      <select class="form-control" name="projectTypeSelect" v-model="project.projectType" required >
        <option v-for="projectType in projectTypes" :key="projectType.id" :value="projectType">
          {{ projectType.name }}
        </option>
      </select>
    </b-form-group>
    <b-form-group label="Completion" label-for="completion" horizontal>
      <b-input type="number" v-model="project.completion" min="0" max="100" required />
    </b-form-group>
    <b-form-group id="completion" label="Status" label-for="completion" horizontal>
      <b-form-select id="statusSelect" v-model="project.status" :options="statusOptions" required />
    </b-form-group>
    <b-form-group label="Assigned to" horizontal>
      <multiselect v-model="project.assignedTo" :options="staffMembers" :multiple="true" placeholder="Select Staff" label="name" track-by="id" :close-on-select="false"></multiselect>
    </b-form-group>
    <b-form-group id="division" label="Division" label-for="divisionSelect" horizontal>
      <select class="form-control" name="divisionSelect" v-model="project.division">
        <option value=""></option>
        <option v-for="division in divisions" :key="division.id" :value="division">
          {{ division.name }}
        </option>
      </select>
    </b-form-group>
    <b-form-group label="Business Units" horizontal>
      <multiselect v-model="project.businessUnits" :options="businessUnits" :multiple="true" placeholder="Select Business Units" label="name" track-by="id" :close-on-select="false"></multiselect>
    </b-form-group>
    <b-form-group id="startDate" label="Start Date" label-for="startDatePicker" horizontal>
      <vuejs-datepicker id="startDatePicker" name="startDate" v-model="project.startDate" :format="customFormatter"></vuejs-datepicker>
    </b-form-group>
    <b-form-group id="endDate" label="End Date" label-for="endDatePicker" horizontal>
      <vuejs-datepicker id="endDatePicker" name="endDate" v-model="project.endDate" :format="customFormatter"></vuejs-datepicker>
      <b-form-text id="password-help-block">
        Actual or Estimated
      </b-form-text>
    </b-form-group>
    <b-form-group id="underPrioritization" label="Under Prioritization" label-for="underPrioritizationCheckbox" horizontal>
      <b-form-checkbox id="underPrioritizationCheckbox" v-model="project.underPrioritization">
      </b-form-checkbox>
    </b-form-group>
    <b-form-group id="capitalProject" label="Capital Project" label-for="capitalProjectCheckbox" horizontal>
      <b-form-checkbox id="capitalProjectCheckbox" v-model="project.capitalProject">
      </b-form-checkbox>
    </b-form-group>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import moment from 'moment';
import { mapState } from 'vuex';

@Component({
  computed: mapState(['businessUnits', 'divisions', 'projectTypes', 'staffMembers']),
})
export default class ProjectForm extends Vue {
  @Prop() private project?: any;

  private statusOptions: string[] = ['Not Started', 'In Progress', 'Complete'];

  private async mounted() {
    this.loadData();
  }

  private async loadData() {
    this.$store.dispatch('fetchBusinessUnits');
    this.$store.dispatch('fetchStaffMembers');
    this.$store.dispatch('fetchProjectTypes');
    this.$store.dispatch('fetchDivisions');
  }

  private customFormatter(date: Date) {
      return moment(date).format('YYYY-MM-DD');
    }
}
</script>

<style src="vue-multiselect/dist/vue-multiselect.min.css"></style>