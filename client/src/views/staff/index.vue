<template>
  <div class="mt-2">
      <div class="card card-accent-primary">
        <div class="card-header">
          <b-row>
            <b-col md="8" class="my-2">
              <b-button size="sm" :to="{ path: '/staff/create' }"><span class="icon icon-plus"></span> New Staff Member</b-button>
            </b-col>
            <b-col md="4" class="my-2">
                <b-input-group>
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
            :items="staffMembers"
            :fields="fields"
            :filter="filter">
            <template #cell(name)="data">
              <router-link :to="{ path: '/staff/show/' + data.item.id}">
                {{data.value}}
              </router-link>
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
  computed: mapState(['staffMembers']),
})
export default class StaffIndex extends Vue {

  private sortBy: string = 'name';
  private sortDesc: boolean = false;
  private fields: any[] = [
    { key: 'name', sortable: true },
    { key: 'title', sortable: false },
    { key: 'username', sortable: true },
    { key: 'email', sortable: true },
  ];
  private filter: string = '';

  private async mounted() {
    this.$store.dispatch('fetchStaffMembers');
  }
}
</script>
