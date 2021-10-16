<template>
  <b-container fluid class="mt-2">
    <b-row>
      <b-col sm="6">
        <b-card header="New Project" footer-tag="footer">
          <b-form @submit.prevent="save()">
            <ProjectForm :project="project"></ProjectForm>
            <div slot="footer" class="text-center">
              <b-button type="submit" variant="primary" class="btn-sm mx-2">Save</b-button>
            </div>
          </b-form>
        </b-card>
      </b-col>
    </b-row>
  </b-container>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import Multiselect from 'vue-multiselect';
import Datepicker from 'vuejs-datepicker';
import ProjectForm from '@/components/ProjectForm.vue';
import moment from 'moment';
import ProjectApiService from '@/services/project.api';

Vue.component('multiselect', Multiselect);
Vue.component('vuejs-datepicker', Datepicker);

@Component({
  components: {
    ProjectForm,
  },
})
export default class ProjectCreate extends Vue {
  private project: any = { completion: 0 };

  private async save() {
    await ProjectApiService.saveProject(this.project)
      .then((response) => {
        this.project = response.data;
        Vue.toasted.show('Project Saved');
        this.$router.push({ path: '/projects/show/' + this.project.id });
      })
      .catch((error) => {
        Vue.toasted.show(error.response);
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
