<template>
  <b-container fluid class="mt-2">
    <b-row>
      <b-col sm="6">
        <b-card header="New Staff Member" footer-tag="footer">
          <b-form @submit.prevent="save()">
            <StaffForm :staff="staff"></StaffForm>
            <div slot="footer" class="text-center">
              <b-button type="Submit" variant="primary" class="btn-sm mx-2">Save</b-button>
            </div>
          </b-form>
        </b-card>
      </b-col>
    </b-row>
  </b-container>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import StaffForm from '@/components/StaffForm.vue';
import StaffApiService from '@/services/staff.api';

@Component({
  components: {
    StaffForm,
  },
})
export default class StaffCreate extends Vue {
  public staff: any = new Object({active: true});
  public message: string = '';

  private async save() {
    await StaffApiService.saveStaff(this.staff)
      .then((response) => {
        this.staff = response.data;
        Vue.toasted.show('Staff Member Saved');
        this.$router.push({ path: '/staff/show/' + this.staff.id });
      })
      .catch((error) => {
        Vue.toasted.show(error.response);
      });
  }
}
</script>