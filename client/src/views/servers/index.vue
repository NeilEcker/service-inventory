<template>
  <div class="mt-2">
      <div class="card card-accent-primary">
        <div class="card-header">
          <b-row>
            <b-col md="8" class="my-2">
              <b-button size="sm" :to="{ path: '/servers/create' }"><span class="icon icon-plus"></span> New Server</b-button>
            </b-col>
            <b-col md="4" class="my-2">
                <b-input-group>
                  <b-form-checkbox
                    v-model="showInactive"
                  >
                    Show Inactive
                  </b-form-checkbox>
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
            :items="filteredServers"
            :fields="fields"
            :filter="filter">
            <template #cell(name)="data">
              <router-link :to="{ path: '/servers/show/' + data.item.id}">
                {{data.value}} <b-badge v-if="data.item.active === false">Inactive</b-badge>
              </router-link>
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
                    <b-row class="mb-2">
                      <b-col sm="3" class="text-sm-right"><b>End of Life:</b></b-col>
                      <b-col>{{ data.item.endOfLife }}</b-col>
                    </b-row>
                    <b-row class="mb-2">
                      <b-col sm="3" class="text-sm-right"><b>Next Review:</b></b-col>
                      <b-col>
                        {{ data.item.nextReview }}
                      </b-col>
                    </b-row>
                  </b-col>
                </b-row>

              </b-card>
            </template>
          </b-table>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { mapState } from 'vuex';

@Component({
  computed: mapState(['servers']),
})
export default class ServerIndex extends Vue {

  private sortBy: string = 'name';
  private sortDesc: boolean = false;
  private fields: any[] = [
    { key: 'name', sortable: true },
    { key: 'division.name', sortable: true },
    { key: 'description', sortable: false },
    { key: 'operatingSystem', sortable: true },
    { key: 'staticAddress', sortable: true },
    'details',
  ];
  private filter: string = '';
  private showData: boolean = false;
  private showInactive: boolean = false;

  private async mounted() {
    this.$store.dispatch('fetchServers');
  }

  // computed
  get filteredServers() {
    let filtered: any[] = this.$store.getters.servers;
    if (!this.showInactive) {
      filtered = filtered.filter(
        (m: any) => m.active === true,
      );
    }
    return filtered;
  }
}
</script>
