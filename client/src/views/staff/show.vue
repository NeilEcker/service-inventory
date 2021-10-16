<template>
  <div class="mt-2">
    <b-row v-if="staff">
      <b-col sm="12">
          <b-form @submit.prevent="update()" ref="staffForm">
        <b-card header-tag="header">
            <div slot="header">
                <b-row>
                    <b-col md="4">
                        <b-input id="name" v-model="staff.name" :value="staff.name" v-if="editMode" />
                        <strong v-else>{{ staff.name }}</strong>
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
                                <b-button @click.prevent="deleteStaff()" variant="danger" class="btn-sm mx-2">
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
                  <b-col md="4" lg="3" class="text-sm-right"><b>Title:</b></b-col>
                  <b-col>{{ staff.title }}</b-col>
                </b-row>
                <b-row class="mb-2">
                  <b-col md="4" lg="3" class="text-sm-right"><b>Username:</b></b-col>
                  <b-col>{{ staff.username }}</b-col>
                </b-row>
                  <b-row class="mb-2">
                      <b-col md="4" lg="3" class="text-sm-right"><b>Email:</b></b-col>
                      <b-col>{{ staff.email }}</b-col>
                  </b-row>
              </div>

              <div v-if="editMode">
                  <StaffForm :staff="staff"></StaffForm>
              </div>
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
import StaffForm from '@/components/StaffForm.vue';
import { mapState } from 'vuex';
import StaffApiService from '@/services/staff.api';

@Component({
 components: {
    StaffForm,
  },
})
export default class StaffShow extends Vue {
  private staff: any = null;
  private editMode: boolean = false;

  @Watch('$route')
  private onPropertyChanged(value: string, oldValue: string) {
    this.loadData();
  }

  private async mounted() {
    this.loadData();
  }

  private async loadData() {
    await StaffApiService.getStaff(this.$route.params.id)
       .then((response) => (this.staff = response.data));
  }

  private switchMode() {
    this.loadData();
    this.editMode = !this.editMode;
  }

  private async update() {
    StaffApiService.updateStaff(this.staff.id, this.staff)
      .then((response) => {
        this.staff = response.data;
        Vue.toasted.show('Staff Member Saved');
        this.editMode = false;
      })
      .catch((error) => {
        Vue.toasted.show(error.response);
      });
  }

  private deleteStaff() {
    this.$swal({
      title: 'Confirm',
      text: 'Are you sure you want to delete this staff member?',
      showCancelButton: true,
      confirmButtonText: 'Delete',
      cancelButtonText: 'Cancel',
      showCloseButton: true,
    }).then((result) => {
      if (result.value) {
         StaffApiService.deleteStaff(this.$route.params.id)
        .then( (response) => {
            Vue.toasted.show('Staff Deleted');
            this.$router.push({ path: '/staff' });
        })
        .catch((error) => {
            Vue.toasted.show(error.response);
        });
      }
    });
  }
}
</script>