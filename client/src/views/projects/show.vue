<template>
  <div class="mt-2">
    <b-row v-if="project">
      <b-col sm="12">
          <b-form @submit.prevent="update()">
        <b-card header-tag="header" footer-tag="footer">
            <div slot="header">
                <b-row>
                    <b-col md="4">
                        <b-input id="name" v-model="project.name" :value="project.name" v-if="editMode" />
                        <strong v-else>{{ project.name }}</strong>
                    </b-col>
                    <b-col>
                        <div class="float-right">
                            <span v-if="editMode">
                                <b-button @click.prevent="switchMode()" variant="default" class="btn-sm mx-2">
                                  <span class="icon-close"></span> Cancel
                                </b-button>
                                <b-button type="submit" variant="primary" class="btn-sm mx-2" v-if="editMode"><span class="icon-check"></span> Save</b-button>
                            </span>
                            <span v-else>
                                <b-button @click.prevent="switchMode()" variant="primary" class="btn-sm mx-2">
                                  <span class="icon-pencil"></span> Edit
                                </b-button>
                                <b-button @click.prevent="deleteProject()" variant="danger" class="btn-sm mx-2">
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
                  <b-col>{{ project.description }}</b-col>
                </b-row>
                <b-row class="mb-2">
                  <b-col md="4" lg="3" class="text-sm-right"><b>Project Type:</b></b-col>
                  <b-col>{{ project.projectType.name }}</b-col>
                </b-row>
                <b-row class="mb-2">
                  <b-col md="4" lg="3" class="text-sm-right"><b>Status:</b></b-col>
                  <b-col>{{ project.status }}</b-col>
                </b-row>
                  <b-row class="mb-2">
                      <b-col md="4" lg="3" class="text-sm-right"><b>Completion:</b></b-col>
                      <b-col>{{ project.completion }}%</b-col>
                  </b-row>
                <b-row class="mb-2">
                  <b-col md="4" lg="3" class="text-sm-right"><b>Assigned to:</b></b-col>
                  <b-col>
                    <ul>
                      <li v-for="staff in project.assignedTo" :key="staff.id">{{ staff.name }}</li>
                    </ul>
                  </b-col>
                </b-row>
                  <b-row class="mb-2">
                      <b-col md="4" lg="3" class="text-sm-right"><b>Division:</b></b-col>
                      <b-col><span v-if="project.division">{{ project.division.name }}</span></b-col>
                  </b-row>
                <b-row class="mb-2">
                  <b-col md="4" lg="3" class="text-sm-right"><b>Date Created:</b></b-col>
                  <b-col>{{ project.dateCreated| formatDate }}</b-col>
                </b-row>
                <b-row class="mb-2">
                  <b-col md="4" lg="3" class="text-sm-right"><b>Start Date:</b></b-col>
                  <b-col>{{ project.startDate | formatDate }}</b-col>
                </b-row>
                <b-row class="mb-2">
                  <b-col md="4" lg="3" class="text-sm-right"><b>End Date:</b></b-col>
                  <b-col>{{ project.endDate | formatDate }}</b-col>
                </b-row>
                <b-row class="mb-2">
                  <b-col md="4" lg="3" class="text-sm-right"><b>Under Prioritization:</b></b-col>
                  <b-col>{{ project.underPrioritization }}</b-col>
                </b-row>
                  <b-row class="mb-2">
                      <b-col md="4" lg="3" class="text-sm-right"><b>Capital Project:</b></b-col>
                      <b-col>{{ project.capitalProject }}</b-col>
                  </b-row>
                <b-row class="mb-2">
                  <b-col md="4" lg="3" class="text-sm-right"><b>Project Form:</b></b-col>
                  <b-col>{{ project.projectForm }}</b-col>
                </b-row>
              </div>

                <div v-if="editMode">
                    <ProjectForm :project="project"></ProjectForm>
                </div>
          </b-col>

          <b-col sm="6">
              <b-tabs>
                <b-tab title="Notes">
                  <div v-html="project.notes" v-if="!editMode"></div>
                  <b-form v-if="editMode">
                    <quill-editor id="notesQuill" ref="notesEditor" v-model="project.notes" :options="editorOptions"></quill-editor>
                  </b-form>
                </b-tab>
                  <b-tab title="Related Services">
                      <div v-if="!editMode">
                          <b-table :items="project.servicesAffected" :fields="affectedFields">
                              <template slot="name" slot-scope="row">
                                  <router-link :to="{ path: '/applications/show/' + row.item.id}">
                                      {{row.value}}
                                  </router-link>
                              </template>
                          </b-table>
                      </div>
                      <div v-else>
                          <multiselect v-model="project.servicesAffected" :options="appServices" :multiple="true" placeholder="Select Services" label="name" track-by="id" :close-on-select="false"></multiselect>
                      </div>
                  </b-tab>
                  <b-tab title="Related Servers">
                      <div v-if="!editMode">
                          <b-table :items="project.serversAffected" :fields="affectedFields">
                              <template slot="name" slot-scope="row">
                                  <router-link :to="{ path: '/servers/show/' + row.item.id}">
                                      {{row.value}}
                                  </router-link>
                              </template>
                          </b-table>
                      </div>
                      <div v-else>
                          <multiselect v-model="project.serversAffected" :options="servers" :multiple="true" placeholder="Select Servers" label="name" track-by="id" :close-on-select="false"></multiselect>
                      </div>
                  </b-tab>
                <b-tab title="Business Units">
                    <div v-if="!editMode">
                        <ul>
                            <li v-for="businessUnit in project.businessUnits" :key="businessUnit.id">{{ businessUnit.name }}</li>
                        </ul>
                    </div>
                    <div v-else>
                        <b-form-group label="Business Units" horizontal>
                            <multiselect v-model="project.businessUnits" :options="businessUnits" :multiple="true" placeholder="Select Business Units" label="name" track-by="id" :close-on-select="false"></multiselect>
                        </b-form-group>
                    </div>
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
import References from '@/components/References.vue';
import ProjectForm from '@/components/ProjectForm.vue';
import { quillEditor } from 'vue-quill-editor';
import moment from 'moment';
import ProjectApiService from '@/services/project.api';
import { mapState } from 'vuex';

Vue.component('multiselect', Multiselect);
Vue.component('vuejs-datepicker', Datepicker);

@Component({
  components: {
    References,
    ProjectForm,
    quillEditor,
  },
  computed: mapState(['statusOptions', 'businessUnits', 'staffMembers', 'appServices', 'servers', 'projectTypes']),
})
export default class Project extends Vue {
  private project: any = null;
  private editMode: boolean = false;

  private affectedFields: any[] = [
    { key: 'name', sortable: true },
    { key: 'description', sortable: true },
  ];
  private dateOptions: any = {
    format: 'YYYY-MM-DD',
    useCurrent: false,
  };
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
  private dateFormat: string = 'YYYY-MM-DD';

  @Watch('$route')
  private onPropertyChanged(value: string, oldValue: string) {
    this.loadData();
  }

  private async mounted() {
    this.loadData();
  }

  private async loadData() {
    ProjectApiService.getProject(this.$route.params.id).then( (response) => { this.project = response.data; });
    this.$store.dispatch('fetchBusinessUnits');
    this.$store.dispatch('fetchServers');
    this.$store.dispatch('fetchAppServices');
    this.$store.dispatch('fetchStaffMembers');
    this.$store.dispatch('fetchProjectTypes');
  }

  private switchMode() {
    this.loadData();
    this.editMode = !this.editMode;
  }

  private customFormatter(date: Date) {
    return moment(date).format('YYYY-MM-DD');
  }

  private async update() {
     ProjectApiService.updateProject(this.$route.params.id, this.project)
        .then( (response) => {
            this.project = response.data;
            Vue.toasted.show('Project Saved');
            this.editMode = false;
        })
        .catch((error) => {
            Vue.toasted.show(error.response);
        });

  }

  private deleteProject() {
    this.$swal({
      title: 'Confirm',
      text: 'Are you sure you want to delete this project?',
      showCancelButton: true,
      confirmButtonText: 'Delete',
      cancelButtonText: 'Cancel',
      showCloseButton: true,
    }).then((result) => {
      if (result.value) {
         ProjectApiService.deleteProject(this.$route.params.id)
        .then( (response) => {
            Vue.toasted.show('Project Deleted');
            this.$router.push({ path: '/projects' });
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
.tab-content {
    height: 100%;
}
</style>