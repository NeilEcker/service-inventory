<template>
  <div class="mt-2">
    <b-row v-if="application">
      <b-col sm="12">
        <b-form @submit.prevent="update()">
        <b-card header-tag="header" footer-tag="footer">
            <div slot="header">
                <b-row>
                    <b-col md="4">
                        <b-input id="name" v-model="application.name" :value="application.name" v-if="editMode" />
                      <strong v-else><strong>{{ application.name }}</strong></strong>
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
                                <b-button @click="switchMode()" variant="primary" class="btn-sm mx-2">
                                  <span class="icon-pencil"></span> Edit
                                </b-button>
                                <b-button @click="deleteApplication()" variant="danger" class="btn-sm mx-2">
                                  <span class="icon-pencil"></span> Delete
                                </b-button>
                            </span>
                        </div>
                    </b-col>
                </b-row>
              </div>

              <b-tabs>
                <b-tab title="Details">
                  <div v-if="!editMode">
                    <b-row>
                      <b-col class="col-sm m-1">
                        <b-card header="Details" bg-variant="light">
                          <b-row>
                            <b-col md="4"><b>Description</b></b-col>
                            <b-col>{{ application.description }}</b-col>
                          </b-row>
                          <b-row>
                            <b-col md="4"><b>Type</b></b-col>
                            <b-col>{{ application.type }}</b-col>
                          </b-row>
                          <b-row>
                            <b-col md="4"><b>URL</b></b-col>
                            <b-col><a :href="application.url" target="_blank">{{ application.url }}</a></b-col>
                          </b-row>
                          <b-row>
                            <b-col md="4"><b>Location</b></b-col>
                            <b-col>{{ application.location }}</b-col>
                          </b-row>
                          <b-row>
                            <b-col md="4"><b>Tag</b></b-col>
                            <b-col>{{ application.tag }}</b-col>
                          </b-row>
                        </b-card>
                      </b-col>

                      <b-col class="col-sm m-1">
                      <b-card header="Environment" bg-variant="light">
                        <b-row>
                          <b-col md="4"><b>Platform</b></b-col>
                          <b-col>{{ application.platform }}</b-col>
                        </b-row>
                        <b-row>
                          <b-col md="4"><b>Production Server(s)</b></b-col>
                          <b-col>
                              <span v-for="server in application.productionServers" :key="server.id">
                                <router-link :to="{ path: '/servers/show/' + server.id}">{{ server.name }}</router-link><br />
                              </span>
                          </b-col>
                        </b-row>
                        <b-row>
                          <b-col md="4" ><b>Staging Server(s)</b></b-col>
                          <b-col>
                            <span v-for="server in application.stagingServers" :key="server.id">
                              <router-link :to="{ path: '/servers/show/' + server.id}">{{ server.name }}</router-link><br />
                            </span>
                          </b-col>
                        </b-row>
                        <b-row>
                          <b-col md="4" ><b>Database Name(s)</b></b-col>
                          <b-col>
                            {{ application.databaseNames.join(', ') }}
                          </b-col>
                        </b-row>
                        <b-row>
                          <b-col md="4" ><b>Service Account(s)</b></b-col>
                          <b-col>
                            {{ application.serviceAccounts.join(', ') }}
                          </b-col>
                        </b-row>
                      </b-card>
                      </b-col>
                    </b-row>

                    <b-row>
                      <b-col class="col-sm m-1">
                      <b-card header="Support" bg-variant="light">
                        <b-row>
                          <b-col md="4"><b>Support Staff</b></b-col>
                          <b-col>
                            <span v-for="staff in application.supportStaff" :key="staff.id">{{ staff.name }}<br /></span>
                          </b-col>
                        </b-row>
                        <b-row>
                          <b-col md="4" ><b>Division</b></b-col>
                          <b-col><span v-if="application.division">{{ application.division.name }}</span></b-col>
                        </b-row>
                        <b-row>
                          <b-col md="4" ><b>Business Units</b></b-col>
                          <b-col>
                            <span v-for="businessUnit in application.businessUnits" :key="businessUnit.id">{{ businessUnit.name }}<br /></span>
                          </b-col>
                        </b-row>
                        <b-row>
                          <b-col md="4" ><b>Vendor</b></b-col>
                          <b-col><span v-if="application.vendor">{{ application.vendor.name }}</span></b-col>
                        </b-row>
                      </b-card>
                      </b-col>

                      <b-col class="col-sm m-1">
                        <b-card header="Status" bg-variant="light">
                          <b-row>
                            <b-col md="4"><b>Status</b></b-col>
                            <b-col>{{ application.active ? 'Active' : 'Not Active' }}</b-col>
                          </b-row>
                          <b-row>
                            <b-col md="4"><b>Decomission</b></b-col>
                            <b-col>{{ application.decommission ? 'Decommission / Replace' : '' }}</b-col>
                          </b-row>
                          <b-row>
                            <b-col md="4"><b>Next Review</b></b-col>
                            <b-col>{{ application.nextReview | formatDate }}</b-col>
                          </b-row>
                          <b-row>
                            <b-col md="4"><b>Implemented</b></b-col>
                            <b-col>{{ application.implemented | formatDate }}</b-col>
                          </b-row>
                          <b-row>
                            <b-col md="4"><b>Decommissioned</b></b-col>
                            <b-col>{{ application.decommissioned | formatDate }}</b-col>
                          </b-row>
                        </b-card>
                      </b-col>

                    </b-row>
                  </div>

                  <div v-if="editMode">
                    <ApplicationForm :application="application"></ApplicationForm>
                  </div>
                </b-tab>
                <b-tab title="Notes">
                  <div v-html="application.notes" v-if="!editMode"></div>
                  <b-form v-if="editMode">
                    <quill-editor id="notesQuill" ref="notesEditor" v-model="application.notes" :options="editorOptions"></quill-editor>
                  </b-form>
                </b-tab>
                <b-tab title="Documents">
                  <b-row>
                    <b-col sm="3">
                      <b-list-group>
                        <b-list-group-item v-for="document in documents" :key="document.id">
                          <span v-if="document.content.mimeType === 'text/html'">
                            <a  href="#" @click="loadWiki(document.id)" title="Preview"><small>{{ document.name }}</small></a>
                            <a :href="'https://docs.grey.ca/share/page/document-details?nodeRef=workspace://SpacesStore/' + document.id" target="_blank" class="float-right" title="Open in Alfresco"><i class="icons cui-file"></i></a>
                          </span>
                          <span v-else>
                            <small>{{ document.name }}</small>
                            <a :href="'https://docs.grey.ca/share/page/document-details?nodeRef=workspace://SpacesStore/' + document.id" target="_blank" class="float-right" title="Open in Alfresco"><i class="icons cui-file"></i></a>
                          </span>
                        </b-list-group-item>
                      </b-list-group>
                    </b-col>
                    <b-col sm="9">
                      <b-card>
                        <div id="preview" v-html="document"></div>
                      </b-card>
                    </b-col>
                  </b-row>
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
                <b-tab>
                  <template slot="title">
                    Dependent On <b-badge>{{ application.dependsOn ? application.dependsOn.filter(item => { return item.active }).length : '0' }}</b-badge>
                  </template>
                    <div v-if="!editMode">
                      <b-table :items="application.dependsOn.filter(item => { return item.active })" :fields="dependentFields" :striped="true">
                          <template slot="name" slot-scope="row">
                            <router-link :to="{ path: '/applications/show/' + row.item.id}">
                              {{row.value}}
                            </router-link>
                          </template>
                          <template slot="productionServers" slot-scope="row">
                            <span v-for="server in row.item.productionServers" :key="server.id">
                              <router-link :to="{ path: '/servers/show/' + server.id}">{{ server.name }}</router-link><br />
                            </span>
                          </template>
                        </b-table>
                    </div>
                    <div v-else>
                        <multiselect v-model="application.dependsOn" :options="appServices" :multiple="true" placeholder="Select Dependencies" label="name" track-by="id" :close-on-select="false"></multiselect>
                        <br />
                    </div>
                </b-tab>
                <b-tab>
                  <template slot="title">
                    Dependents <b-badge>{{ dependents ? dependents.filter(item => { return item.active }).length : '0' }}</b-badge>
                  </template>
                  <b-table :items="dependents.filter(item => { return item.active })" :fields="dependentFields" :striped="true">
                    <template slot="name" slot-scope="row">
                      <router-link :to="{ path: '/applications/show/' + row.item.id}">
                        {{row.value}}
                      </router-link>
                    </template>
                    <template slot="productionServers" slot-scope="row">
                       <span v-for="server in row.item.productionServers" :key="server.id">
                         <router-link :to="{ path: '/servers/show/' + server.id}">{{ server.name }}</router-link><br />
                       </span>
                    </template>
                  </b-table>
                </b-tab>
                <b-tab>
                  <template slot="title">
                    References <b-badge>{{ application.references ? application.references.length : '0' }}</b-badge>
                  </template>
                  <References :application="application" @loadApplication="loadApplication"></References>
                </b-tab>
              </b-tabs>
        </b-card>
        </b-form>
      </b-col>
    </b-row>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Watch } from 'vue-property-decorator';
import Multiselect from 'vue-multiselect';
import References from '@/components/References.vue';
import ApplicationForm from '@/components/ApplicationForm.vue';
import moment from 'moment';
import { quillEditor } from 'vue-quill-editor';
import { mapState } from 'vuex';
import ApiService from '@/services/api';
import ApplicationApiService from '@/services/application.api';
import ChangeApiService from '@/services/change.api';
import DocumentApiService from '@/services/document.api';

Vue.component('multiselect', Multiselect);

@Component({
  components: {
    References,
    ApplicationForm,
    quillEditor,
  },
  computed: mapState(['appServices']),
})
export default class Application extends Vue {
  private application: any = null;
  private editMode: boolean = false;
  private dependents: any[] = [];
  private changes: any[] = [];
  private documents: any[] = [];
  private document: string = '';
  private dependentFields: any[] = [
      { key: 'name', sortable: true },
      { key: 'description', sortable: true },
      { key: 'productionServers' },
    ];
  private changeFields: any[] = [
    { key: 'description' },
    { key: 'dateCompleted', sortable: true },
    { key: 'staff.name' },
  ];
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
    this.$store.dispatch('fetchAppServices');
    ApplicationApiService.getDependents(this.$route.params.id)
      .then((response) => (this.dependents = response.data));
    ChangeApiService.getChangesByAppService(this.$route.params.id)
      .then((response) => (this.changes = response.data));
    this.loadApplication();
  }

  private async loadApplication() {
    await ApplicationApiService.getApplication(this.$route.params.id)
      .then((response) => {
          this.application = response.data;
          this.loadWikiEntries();
        });
  }

  private async loadWikiEntries() {
    // DocumentApiService.search(this.application.name)
    // .then((response) => (this.documents = response.data));
      DocumentApiService.searchByTag(this.application.tag)
      .then((response) => (this.documents = response.data));
  }

  private async loadWiki(id: string) {
    DocumentApiService.content(id)
      .then((response) => (this.document = response.data));
  }

  private switchMode() {
    this.loadApplication();
    this.editMode = !this.editMode;
  }

  private async update() {
    await ApplicationApiService.updateApplication(this.application.id, this.application)
      .then((response) => {
        this.application = response.data;
        Vue.toasted.show('Application Saved');
        this.editMode = false;
      })
      .catch((error) => {
        Vue.toasted.show(error.response);
      });
  }

  private async deleteApplication() {
    this.$swal({
      title: 'Confirm',
      text: 'Are you sure you want to delete this application?',
      showCancelButton: true,
      confirmButtonText: 'Delete',
      cancelButtonText: 'Cancel',
      showCloseButton: true,
    }).then((result) => {
      if (result.value) {
        ApplicationApiService.deleteApplication(this.application.id);
        Vue.toasted.show('Application Deleted');
        this.$router.push({ path: '/applications'});
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