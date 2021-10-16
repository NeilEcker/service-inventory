<template>
    <div>
        <b-row>
            <b-col class="col-sm m-1">
                <b-card header="Details" bg-variant="light">
                    <b-form-group label="Name" label-for="name" horizontal>
                        <b-input id="name" v-model="application.name" required />
                    </b-form-group>
                    <b-form-group label="Description" label-for="description" horizontal>
                        <b-input id="description" v-model="application.description" placeholder="Short description" />
                    </b-form-group>
                    <b-form-group id="type" label="Type" label-for="typeSelect" horizontal>
                        <b-form-select id="typeSelect" v-model="application.type" :options="typeOptions" required />
                    </b-form-group>
                    <b-form-group label="URL" label-for="url" horizontal>
                        <b-input type="url" id="url" v-model="application.url" />
                    </b-form-group>
                    <b-form-group id="location" label="Location" label-for="locationSelect" horizontal>
                        <b-form-select id="locationSelect" v-model="application.location" :options="locationOptions" required />
                    </b-form-group>
                    <b-form-group label="Tag" label-for="tag" horizontal>
                        <b-input id="tag" v-model="application.tag" placeholder="Match an Alfresco tag" />
                    </b-form-group>
                </b-card>
            </b-col>

            <b-col class="col-sm m-1">
                <b-card header="Environment" bg-variant="light">
                    <b-form-group id="platform" label="Platform" label-for="platformSelect" horizontal>
                        <b-form-select id="platformSelect" v-model="application.platform" :options="platformOptions" required />
                    </b-form-group>
                    <b-form-group label="Production Server" horizontal>
                        <multiselect v-model="application.productionServers" :options="servers" :multiple="true" placeholder="Select Production Server(s)" label="name" track-by="id" :close-on-select="false"></multiselect>
                    </b-form-group>
                    <b-form-group label="Staging Server" horizontal>
                        <multiselect v-model="application.stagingServers" :options="servers" :multiple="true" placeholder="Select Staging Server(s)" label="name" track-by="id" :close-on-select="false"></multiselect>
                    </b-form-group>
                    <b-form-group label="Depends On" horizontal>
                        <multiselect v-model="application.dependsOn" :options="appServices" :multiple="true" placeholder="Select Dependencies" label="name" track-by="id" :close-on-select="false"></multiselect>
                    </b-form-group>
                    <b-form-group label="Database Names" label-for="databaseNames" horizontal>
                        <multiselect v-model="application.databaseNames" :multiple="true" :taggable="true" :options="application.databaseNames" tag-placeholder="Add this as new tag" @tag="addTag" />
                    </b-form-group>
                    <b-form-group label="Service Accounts" label-for="serviceAccounts" horizontal>
                        <multiselect v-model="application.serviceAccounts" :multiple="true" :taggable="true" :options="application.serviceAccounts" tag-placeholder="Add this service account" @tag="addServiceAccount" />
                    </b-form-group>
                </b-card>
            </b-col>
        </b-row>

        <b-row>
            <b-col class="col-sm m-1">
                <b-card header="Support" bg-variant="light">
                    <b-form-group label="Support Staff" horizontal>
                        <multiselect v-model="application.supportStaff" :options="staffMembers" :multiple="true" placeholder="Select Staff" label="name" track-by="id" :close-on-select="false"></multiselect>
                    </b-form-group>
                    <b-form-group id="division" label="Division" label-for="divisionSelect" horizontal>
                        <select class="form-control" name="divisionSelect" v-model="application.division">
                            <option value=""></option>
                            <option v-for="division in divisions" :key="division.id" :value="division">
                                {{ division.name }}
                            </option>
                        </select>
                    </b-form-group>
                    <b-form-group label="Business Units" horizontal>
                        <multiselect v-model="application.businessUnits" :options="businessUnits" :multiple="true" placeholder="Select Business Units" label="name" track-by="id" :close-on-select="false"></multiselect>
                    </b-form-group>
                    <b-form-group id="vendor" label="Vendor" label-for="vendorSelect" horizontal>
                        <select class="form-control" name="vendorSelect" v-model="application.vendor">
                            <option value=""></option>
                            <option v-for="vendor in vendors" :key="vendor.id" :value="vendor">
                                {{ vendor.name }}
                            </option>
                        </select>
                    </b-form-group>
                </b-card>
            </b-col>

            <b-col class="col-sm m-1">
                <b-card header="Status" bg-variant="light">
                    <b-form-group id="active" label="Status" label-for="activeCheckbox" horizontal>
                        <b-form-checkbox id="activeCheckbox" v-model="application.active">Active
                        </b-form-checkbox>
                    </b-form-group>
                    <b-form-group id="decommission" label="Decommission" label-for="decommissionCheckbox" horizontal>
                        <b-form-checkbox id="decommissionCheckbox" v-model="application.decommission">Decommission / Replace
                        </b-form-checkbox>
                    </b-form-group>
                    <b-form-group id="nextReview" label="Next Review" label-for="nextReviewPicker" horizontal>
                        <vuejs-datepicker id="nextReviewPicker" name="nextReview" v-model="application.nextReview" :format="customFormatter"></vuejs-datepicker>
                    </b-form-group>
                    <b-form-group id="implemented" label="Implemented" label-for="implementedPicker" horizontal>
                        <vuejs-datepicker id="implementedPicker" name="implemented" v-model="application.implemented" :format="customFormatter"></vuejs-datepicker>
                    </b-form-group>
                    <b-form-group id="decommissioned" label="Decommissioned" label-for="decommissionedPicker" horizontal>
                        <vuejs-datepicker id="decommissionedPicker" name="decommissioned" v-model="application.decommissioned" :format="customFormatter"></vuejs-datepicker>
                    </b-form-group>
                </b-card>
            </b-col>
        </b-row>

    </div>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import Multiselect from 'vue-multiselect';
import Datepicker from 'vuejs-datepicker';
import { mapState } from 'vuex';
import moment from 'moment';

Vue.component('multiselect', Multiselect);
Vue.component('vuejs-datepicker', Datepicker);

@Component({
  computed: mapState(['businessUnits', 'divisions', 'staffMembers', 'appServices', 'servers', 'vendors']),
})
export default class ApplicationForm extends Vue {
  @Prop() private application?: any;
  private locationOptions: string[] = ['Local', 'Cloud'];
  private typeOptions: string[] = ['Commercial', 'Custom'];
  private platformOptions: string[] = ['Web', 'Desktop', 'Service'];
  private dateOptions: any = {
    format: 'YYYY-MM-DD',
    useCurrent: false,
  };
  private dateFormat: string = 'YYYY-MM-DD';

  private async mounted() {
    this.loadData();
  }

  private async loadData() {
    this.$store.dispatch('fetchBusinessUnits');
    this.$store.dispatch('fetchDivisions');
    this.$store.dispatch('fetchServers');
    this.$store.dispatch('fetchAppServices');
    this.$store.dispatch('fetchStaffMembers');
    this.$store.dispatch('fetchVendors');
  }

  private customFormatter(date: Date) {
    return moment(date).format('YYYY-MM-DD');
  }

  private addTag(newTag: string) {
    this.application.databaseNames.push(newTag);
  }

  private addServiceAccount(newServiceAccount: string) {
    this.application.serviceAccounts.push(newServiceAccount);
  }

}
</script>