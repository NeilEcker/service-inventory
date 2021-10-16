<template>
  <div class="mt-2">
    <b-row v-if="server">
      <b-col sm="12">
          <b-form @submit.prevent="update()" ref="serverForm">
        <b-card header-tag="header">
            <div slot="header">
                <b-row>
                    <b-col md="4">
                        <b-input id="name" v-model="server.name" :value="server.name" v-if="editMode" />
                        <strong v-else>{{ server.name }}</strong>
                    </b-col>
                    <b-col>
                        <div class="float-right">
                            <span v-if="editMode">
                                <b-button @click="switchMode()" variant="default" class="btn-sm mx-2">
                                  <span class="icon-close"></span> Cancel
                                </b-button>
                                <b-button type="submit" variant="primary" class="btn-sm mx-2" v-if="editMode"><span class="icon-check"></span> Save</b-button>
                            </span>
                            <span v-else>
                                <b-button @click="switchMode()" variant="primary" class="btn-sm mx-2">
                                  <span class="icon-pencil"></span> Edit
                                </b-button>
                                <b-button @click.prevent="deleteServer()" variant="danger" class="btn-sm mx-2" v-if="services.length == 0">
                                  <span class="icon-trash"></span> Delete
                                </b-button>
                            </span>
                        </div>
                    </b-col>
                </b-row>
            </div>
            <b-row>
            <b-col sm="6">
              <div v-if="!editMode">
                <b-row class="mb-2">
                  <b-col md="4" lg="3" class="text-sm-right"><b>Description:</b></b-col>
                  <b-col>{{ server.description }}</b-col>
                </b-row>
                <b-row class="mb-2">
                  <b-col md="4" lg="3" class="text-sm-right"><b>Operating System:</b></b-col>
                  <b-col>{{ server.operatingSystem }}</b-col>
                </b-row>
                <b-row class="mb-2">
                  <b-col md="4" lg="3" class="text-sm-right"><b>IP Address or Dynamic:</b></b-col>
                  <b-col>{{ server.staticAddress }}</b-col>
                </b-row>
                  <b-row class="mb-2">
                      <b-col md="4" lg="3" class="text-sm-right"><b>Division:</b></b-col>
                      <b-col><span v-if="server.division">{{ server.division.name }}</span></b-col>
                  </b-row>
                  <b-row class="mb-2">
                      <b-col md="4" lg="3" class="text-sm-right"><b>Next Review:</b></b-col>
                      <b-col>{{ server.nextReview | formatDate }}</b-col>
                  </b-row>
                  <b-row class="mb-2">
                      <b-col md="4" lg="3" class="text-sm-right"><b>End of Life:</b></b-col>
                      <b-col>{{ server.endOfLife | formatDate }}</b-col>
                  </b-row>
                <b-row class="mb-2">
                  <b-col md="4" lg="3" class="text-sm-right"><b>Status:</b></b-col>
                  <b-col>{{ server.active ? 'Active' : 'Not Active' }}</b-col>
                </b-row>
              </div>

              <div v-if="editMode">
                  <ServerForm :server="server"></ServerForm>
              </div>
            </b-col>
            <b-col sm="6">
              <b-tabs>
                <b-tab title="Notes">
                  <div v-html="server.notes" v-if="!editMode"></div>
                  <b-form v-if="editMode">
                    <quill-editor id="notesQuill" ref="notesEditor" v-model="server.notes" :options="editorOptions"></quill-editor>
                  </b-form>
                </b-tab>
                <b-tab title="Changes">
                  <template slot="title">
                      Changes
                  </template>
                  <b-table :items="changes" :fields="changeFields" :striped="true">
                      <template slot="description" slot-scope="row">
                          <router-link :to="{ path: '/change/show/' + row.item.id}">
                              {{row.value}}
                          </router-link>
                      </template>
                      <template slot="dateCompleted" slot-scope="row">
                          {{ row.value | formatDate }}
                      </template>
                  </b-table>
                </b-tab>
                <b-tab title="Services">
                  <b-table :items="services" :fields="serviceFields">
                      <template slot="name" slot-scope="row">
                        <router-link :to="{ path: '/applications/show/' + row.item.id}">
                          {{row.value}}
                        </router-link>
                      </template>
                    </b-table>
                </b-tab>
              </b-tabs>
            </b-col>
            </b-row>
        </b-card>
          </b-form>
      </b-col>
    </b-row>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Watch } from 'vue-property-decorator';
import Multiselect from 'vue-multiselect';
import Datepicker from 'vuejs-datepicker';
import ServerForm from '@/components/ServerForm.vue';
import moment from 'moment';
import { mapState } from 'vuex';
import { quillEditor } from 'vue-quill-editor';
import ApplicationApiService from '@/services/application.api';
import ChangeApiService from '@/services/change.api';
import ServerApiService from '@/services/server.api';

Vue.component('multiselect', Multiselect);
Vue.component('vuejs-datepicker', Datepicker);

Vue.filter('formatDate', (value: string) => {
  if (value) {
    return moment(String(value)).format('YYYY-MM-DD');
  }
});

@Component({
 components: {
    ServerForm,
    quillEditor,
  },
})
export default class ServerIndex extends Vue {
  private server: any = null;
  private services: any = [];
  private serviceFields: any[] = [
    { key: 'name', sortable: true },
    { key: 'description', sortable: true },
  ];
  private changes: any[] = [];
  private changeFields: any[] = [
    { key: 'description' },
    { key: 'dateCompleted', sortable: true },
    { key: 'staff.name' },
  ];
  private editMode: boolean = false;
  private dateOptions: any = {
    format: 'YYYY-MM-DD',
    useCurrent: false,
  };
  private dateFormat: string = 'YYYY-MM-DD';
  private editorOptions: any = {
      theme: 'snow',
      modules: {
        toolbar: [
          ['bold', 'italic', 'underline', 'strike'],
          [{ list: 'ordered'}, { list: 'bullet' }],
          [{ header: [1, 2, 3, 4, 5, 6, false] }],
          [{ color: [] }, { background: [] }],
          [{ font: [] }],
          [{ align: [] }],
          ['link', 'image'],
          ['clean'],
        ],
      },
    };

  @Watch('$route')
  private onPropertyChanged(value: string, oldValue: string) {
    this.loadData();
  }

  private async mounted() {
    this.loadData();
  }

  private async loadData() {
    await ServerApiService.getServer(this.$route.params.id)
       .then((response) => (this.server = response.data));
    await ApplicationApiService.getServicesByServer(this.$route.params.id)
      .then((response) => (this.services = response.data));
    await ChangeApiService.getChangesByServer(this.$route.params.id)
      .then((response) => (this.changes = response.data));
  }

  private switchMode() {
    this.loadData();
    this.editMode = !this.editMode;
  }

  private customFormatter(date: Date) {
    return moment(date).format('YYYY-MM-DD');
  }

  private async update() {
    ServerApiService.updateServer(this.server.id, this.server)
      .then((response) => {
        this.server = response.data;
        Vue.toasted.show('Server Saved');
        this.editMode = false;
      })
      .catch((error) => {
        Vue.toasted.show(error.response);
      });
  }

  private deleteServer() {
    this.$swal({
      title: 'Confirm',
      text: 'Are you sure you want to delete this server?',
      showCancelButton: true,
      confirmButtonText: 'Delete',
      cancelButtonText: 'Cancel',
      showCloseButton: true,
    }).then((result) => {
      if (result.value) {
         ServerApiService.deleteServer(this.$route.params.id)
        .then( (response) => {
            Vue.toasted.show('Server Deleted');
            this.$router.push({ path: '/servers' });
        })
        .catch((error) => {
            Vue.toasted.show(error.response);
        });
      }
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