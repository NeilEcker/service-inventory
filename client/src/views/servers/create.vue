<template>
  <b-container fluid class="mt-2">
    <b-row>
      <b-col sm="6">
        <b-card header="New Server" footer-tag="footer">
          <b-form @submit.prevent="save()">
            <b-form-group label="Name" label-for="name" horizontal>
              <b-input id="name" v-model="server.name" :value="server.name" required />
            </b-form-group>
            <ServerForm :server="server"></ServerForm>
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
import ServerForm from '@/components/ServerForm.vue';
import ServerApiService from '@/services/server.api';

@Component({
  components: {
    ServerForm,
  },
})
export default class ServerCreate extends Vue {
  public server: any = new Object({active: true});
  public message: string = '';

  private async save() {
    await ServerApiService.saveServer(this.server)
      .then((response) => {
        this.server = response.data;
        Vue.toasted.show('Server Saved');
        this.$router.push({ path: '/servers/show/' + this.server.id });
      })
      .catch((error) => {
        Vue.toasted.show(error.response);
      });
  }
}
</script>