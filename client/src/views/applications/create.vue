<template>
  <div>
    <b-row>
      <b-col>
        <b-form @submit.prevent="save()">
          <b-card header="New Application" footer-tag="footer">
            <ApplicationForm :application="application"></ApplicationForm>
            <div slot="footer" class="text-center">
              <b-button type="submit" variant="primary" class="btn-sm mx-2">Save</b-button>
            </div>
          </b-card>
        </b-form>
      </b-col>
    </b-row>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import ApplicationForm from '@/components/ApplicationForm.vue';
import ApplicationApiService from '@/services/application.api';

@Component({
  components: {
    ApplicationForm,
  },
})
export default class ApplicationCreate extends Vue {
  private application: any = new Object({active: true, databaseNames: [], serviceAccounts: []});
  private async save() {
    await ApplicationApiService.saveApplication(this.application)
      .then((response) => {
        this.application = response.data;
        Vue.toasted.show('Application Saved');
        this.$router.push({ path: '/applications/show/' + this.application.id });
      })
      .catch((error) => {
        Vue.toasted.show(error.response);
      });
  }
}
</script>

<style src="vue-multiselect/dist/vue-multiselect.min.css"></style>