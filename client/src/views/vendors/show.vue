<template>
  <div class="mt-2">
    <b-row v-if="vendor">
      <b-col sm="12">
          <b-form @submit.prevent="update()" ref="vendorForm">
        <b-card header-tag="header">
            <div slot="header">
                <b-row>
                    <b-col md="4">
                        <b-input id="name" v-model="vendor.name" :value="vendor.name" v-if="editMode" />
                        <strong v-else>{{ vendor.name }}</strong>
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
                                <b-button @click.prevent="deleteVendor()" variant="danger" class="btn-sm mx-2" v-if="services.length == 0">
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
                  <b-col md="4" lg="3" class="text-sm-right"><b>URL:</b></b-col>
                  <b-col>{{ vendor.url }}</b-col>
                </b-row>
                <b-row class="mb-2">
                  <b-col md="4" lg="3" class="text-sm-right"><b>Contact:</b></b-col>
                  <b-col>{{ vendor.contact }}</b-col>
                </b-row>
              </div>

              <div v-if="editMode">
                  <VendorForm :vendor="vendor"></VendorForm>
              </div>
            </b-col>
            <b-col sm="6">
              <b-tabs>
                <b-tab title="Notes">
                  <div v-html="vendor.notes" v-if="!editMode"></div>
                  <b-form v-if="editMode">
                    <quill-editor id="notesQuill" ref="notesEditor" v-model="vendor.notes" :options="editorOptions"></quill-editor>
                  </b-form>
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
import VendorForm from '@/components/VendorForm.vue';
import { mapState } from 'vuex';
import { quillEditor } from 'vue-quill-editor';
import ApplicationApiService from '@/services/application.api';
import VendorApiService from '@/services/vendor.api';

Vue.component('multiselect', Multiselect);

@Component({
 components: {
    VendorForm,
    quillEditor,
  },
})
export default class VendorShow extends Vue {
  private vendor: any = null;
  private services: any = [];
  private serviceFields: any[] = [
    { key: 'name', sortable: true },
    { key: 'description', sortable: true },
  ];
  private editMode: boolean = false;
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
    await VendorApiService.getVendor(this.$route.params.id)
       .then((response) => (this.vendor = response.data));
    await ApplicationApiService.getServicesByVendor(this.$route.params.id)
      .then((response) => (this.services = response.data));
  }

  private switchMode() {
    this.loadData();
    this.editMode = !this.editMode;
  }

  private async update() {
    VendorApiService.updateVendor(this.vendor.id, this.vendor)
      .then((response) => {
        this.vendor = response.data;
        Vue.toasted.show('Vendor Saved');
        this.editMode = false;
      })
      .catch((error) => {
        Vue.toasted.show(error.response);
      });
  }

  private deleteVendor() {
    this.$swal({
      title: 'Confirm',
      text: 'Are you sure you want to delete this vendor?',
      showCancelButton: true,
      confirmButtonText: 'Delete',
      cancelButtonText: 'Cancel',
      showCloseButton: true,
    }).then((result) => {
      if (result.value) {
         VendorApiService.deleteVendor(this.$route.params.id)
        .then( (response) => {
            Vue.toasted.show('Vendor Deleted');
            this.$router.push({ path: '/vendors' });
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