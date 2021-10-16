<template>
  <div>
    <table class="table table-striped">
      <thead>
        <tr>
          <th>Type</th>
          <th>URL</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="reference in application.references" :key="reference.id">
          <td>{{ reference.referenceType.name }}</td>
          <td>{{ reference.description }}</td>
          <td><a :href="reference.url" target="_blank">{{ reference.url }}</a></td>
          <td>
            <b-button @click="remove(reference.id)" class="btn-sm mx-2">
              Remove
            </b-button>
          </td>
        </tr>
      </tbody>
    </table>
    
    <div class="text-center">
      <b-button class="btn-sm" variant="primary" :pressed.sync="showAdd" v-if="!showAdd">Add Reference</b-button>
    </div>
    <b-form @submit.prevent="onSubmit" v-if="showAdd">
      <h4>Add Reference</h4>
      <b-form-group id="referenceType" label="Type" label-for="referenceTypeSelect" horizontal>
        <b-form-select id="referenceTypeSelect" v-model="newReference.referenceType" :options="referenceTypeOptions" text-field="name" value-field="id" />
      </b-form-group>
      <b-form-group label="Description" label-for="description" horizontal>
        <b-input id="description" v-model="newReference.description" :value="newReference.description" />
      </b-form-group>
      <b-form-group label="URL" label-for="url" horizontal>
        <b-input id="url" v-model="newReference.url" :value="newReference.url" required />
      </b-form-group>
      <div class="text-center">
        <b-button class="btn-sm" :pressed.sync="showAdd">Cancel</b-button>
        <b-button type="submit" class="btn-sm mx-2" variant="primary">Submit</b-button>
      </div>
    </b-form>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Watch, Prop } from 'vue-property-decorator';
import axios from 'axios';
import ApplicationServiceApi from '@/services/application.api';
import ReferenceServiceApi from '@/services/reference.api';

@Component
export default class References extends Vue {
  @Prop() private application?: any;
  private newReference: any = { };
  private message: string = '';
  private referenceTypeOptions: any[] = [];
  private showAdd: boolean = false;

  private async mounted() {
    this.loadData();
  }

  private async loadData() {
    ReferenceServiceApi.getReferenceTypes()
       .then((response) => (this.referenceTypeOptions = response.data));
  }

  private async onSubmit() {
    ReferenceServiceApi.saveReference(this.application.id, this.newReference)
      .then((response) => {
        this.message = 'Reference Saved';
        this.showAdd = false;
        this.newReference = {};
        this.$emit('loadApplication');
      })
      .catch((error) => {
        this.message = error.response;
      });
  }

  private async remove(id: number) {
    ReferenceServiceApi.deleteReference(`${id}`)
      .then((response) => {
        this.$emit('loadApplication');
        this.message = 'Reference Removed';
      })
      .catch((error) => {
        this.message = error.response;
      });
  }
}
</script>

<style src="vue-multiselect/dist/vue-multiselect.min.css"></style>
