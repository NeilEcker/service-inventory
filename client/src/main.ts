import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store/index';
import './filters';
import BootstrapVue from 'bootstrap-vue';
import axios from 'axios';
import Toasted from 'vue-toasted';
import VueGoogleCharts from 'vue-google-charts';
import VueSweetalert2 from 'vue-sweetalert2';

Vue.config.productionTip = false;

Vue.use(BootstrapVue);
Vue.use(VueGoogleCharts);
Vue.use(Toasted , { duration : 5000 });
Vue.use(VueSweetalert2);

import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount('#app');
