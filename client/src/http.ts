import axios from 'axios';
import store from '@/store/index';
import router from '@/router';

const http = axios.create ({
  baseURL: process.env.VUE_APP_SERVER_URL,
  timeout: 1000,
  headers: {'Content-Type': 'application/json'},
});

http.interceptors.request.use (
    (config) => {
        const token = store.state.token;
        if (token) { // && !store.isExpired
            config.headers.Authorization = `Bearer ${token}`;
        } else {
            store.dispatch('logout');
        }
        return config;
    },
    (error) => {
        return Promise.reject (error);
    },
);

http.interceptors.response.use(
    (response) => {
        return response;
    }, (err) => {
        if (err.response.status === 401) {
          store.dispatch('logout');
          router.push('/login');
        }
        return Promise.reject(err);
    },
);

export default http;
