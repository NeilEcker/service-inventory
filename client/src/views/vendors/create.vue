<template>
  <b-container fluid class="mt-2">
    <b-row>
      <b-col sm="6">
        <b-card header="New Vendor" footer-tag="footer">
          <b-form @submit.prevent="save()">
            <VendorForm :vendor="vendor"></VendorForm>
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
import VendorForm from '@/components/VendorForm.vue';
import VendorApiService from '@/services/vendor.api';

@Component({
  components: {
    VendorForm,
  },
})
export default class VendorCreate extends Vue {
  public vendor: any = new Object({active: true});
  public message: string = '';

  private async save() {
    await VendorApiService.saveVendor(this.vendor)
      .then((response) => {
        this.vendor = response.data;
        Vue.toasted.show('Vendor Saved');
        this.$router.push({ path: '/vendors/show/' + this.vendor.id });
      })
      .catch((error) => {
        Vue.toasted.show(error.response);
      });
  }
}
</script>