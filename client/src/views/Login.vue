<template>
  <div class="row justify-content-center">
    <div class="col-md-4">
      <div class="card-group">
        <div class="card p-4">
          <form class="login" @submit.prevent="login">
            <div class="card-body">
              <p class="text-muted">Sign in to your account</p>
              <div class="input-group mb-3">
                <div class="input-group-prepend">
                  <span class="input-group-text">
                  <i class="icon-user"></i>
                  </span>
                </div>
                <input class="form-control" type="text" v-model="username" placeholder="Username">
              </div>
              <div class="input-group mb-4">
                <div class="input-group-prepend">
                  <span class="input-group-text">
                  <i class="icon-lock"></i>
                  </span>
                </div>
                <input class="form-control" type="password" v-model="password" placeholder="Password">
              </div>
              <div class="row">
                <div class="col-12 text-center">
                  <button class="btn btn-primary px-4" type="submit">Login</button>
                </div>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class Login extends Vue {
  private username: string = '';
  private password: string = '';

  private login() {
    const username = this.username;
    const password = this.password;
    this.$store.dispatch('login', { username, password })
      .then(() => this.$router.push('/'));
      // .catch(err: any => console.log(err))
  }
}
</script>
