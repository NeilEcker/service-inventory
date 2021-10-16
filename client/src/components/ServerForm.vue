<template>
    <div>
        <b-form-group label="Description" label-for="description" horizontal>
            <b-input id="description" v-model="server.description" :value="server.description" required />
        </b-form-group>
        <b-form-group label="Operating System" label-for="operatingSystem" horizontal>
            <b-input id="operatingSystem" v-model="server.operatingSystem" :value="server.operatingSystem" required />
        </b-form-group>
        <b-form-group label="Address" label-for="staticAddress" horizontal>
            <b-input id="staticAddress" v-model="server.staticAddress" :value="server.staticAddress" placeholder="IP Address or Dynamic" />
        </b-form-group>
        <b-form-group id="division" label="Division" label-for="divisionSelect" horizontal>
            <select class="form-control" name="divisionSelect" v-model="server.division">
                <option value=""></option>
                <option v-for="division in divisions" :key="division.id" :value="division">
                    {{ division.name }}
                </option>
            </select>
        </b-form-group>
        <b-form-group id="nextReview" label="Next Review" label-for="nextReviewPicker" horizontal>
            <vuejs-datepicker id="nextReviewPicker" name="nextReview" v-model="server.nextReview" :format="customFormatter"></vuejs-datepicker>
        </b-form-group>
        <b-form-group id="endOfLife" label="End of Life" label-for="endOfLifePicker" horizontal>
            <vuejs-datepicker id="endOfLifePicker" name="endOfLife" v-model="server.endOfLife" :format="customFormatter"></vuejs-datepicker>
        </b-form-group>
        <b-form-group id="active" label="Status" label-for="activeCheckbox" horizontal>
            <b-form-checkbox id="activeCheckbox" v-model="server.active">Active
            </b-form-checkbox>
        </b-form-group>
    </div>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import { mapState } from 'vuex';
import moment from 'moment';

@Component({
  computed: mapState(['divisions']),
})
export default class ServerForm extends Vue {
  @Prop() private server?: any;

  private async mounted() {
    this.loadData();
  }

  private async loadData() {
    this.$store.dispatch('fetchDivisions');
  }

  private customFormatter(date: Date) {
    return moment(date).format('YYYY-MM-DD');
  }
}
</script>