import Vue from 'vue';
import Vuex from 'vuex';
import axios from 'axios';
import decode from 'jwt-decode';
import ApiService from '@/services/api';
import ApplicationApiService from '@/services/application.api';
import ServerApiService from '@/services/server.api';
import ProjectApiService from '@/services/project.api';
import ChangeApiService from '@/services/change.api';
import StaffApiService from '@/services/staff.api';
import VendorApiService from '@/services/vendor.api';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    token: localStorage.getItem('token') || '',
    username: localStorage.getItem('username') || '',
    status: '',
    statusOptions: ['Not Started', 'In Progress', 'Complete'],
    businessUnits: [],
    divisions: [],
    divisionOptions: [],
    staffMembers: [],
    servers: [],
    appServices: [],
    projects: [],
    projectTypes: [],
    changes: [],
    vendors: [],
  },
  getters: {
    isLoggedIn: (state) => !!state.token,
    authStatus: (state) => state.status,
    username: (state) => state.username,
    isExpired: (state) => {
        const t = decode(state.token);
        if (!t.exp) { return null; }
        const date = new Date(0);
        date.setUTCSeconds(t.exp);
        return date < new Date();
    },
    appServices: (state) => state.appServices,
    projects: (state) => state.projects,
    servers: (state) => state.servers,
    divisions: (state) => state.divisions,
    vendors: (state) => state.vendors,
  },
  mutations: {
    AUTH_REQUEST(state) {
        state.status = 'loading';
    },
    AUTH_SUCCESS(state, params) {
        state.status = 'success';
        state.token = params.token;
        state.username = params.username;
        localStorage.setItem('token', params.token);
        localStorage.setItem('username', params.username);
    },
    AUTH_ERROR(state) {
        state.status = 'error';
        state.token = '';
        state.username = '';
        localStorage.removeItem('token');
        localStorage.removeItem('username');
    },
    AUTH_LOGOUT(state) {
        state.status = '';
        state.token = '';
        state.username = '';
        localStorage.removeItem('token');
        localStorage.removeItem('username');
    },
    FETCH_BUSINESS_UNITS(state, businessUnits) {
        state.businessUnits = businessUnits;
    },
    FETCH_DIVISIONS(state, divisions) {
        state.divisions = divisions;
        const options = [{ text: '', value: null}] as any;
        for (const division of divisions) {
          options.push({ text: division.name, value: division.id });
        }
        state.divisionOptions = options;
    },
    FETCH_STAFF_MEMBERS(state, staffMembers) {
        state.staffMembers = staffMembers;
    },
    FETCH_SERVERS(state, servers) {
        state.servers = servers;
    },
    FETCH_APP_SERVICES(state, appServices) {
        state.appServices = appServices;
    },
    FETCH_PROJECTS(state, projects) {
        state.projects = projects;
    },
    FETCH_PROJECT_TYPES(state, projectTypes) {
        state.projectTypes = projectTypes;
    },
    FETCH_CHANGES(state, changes) {
        state.changes = changes;
    },
    FETCH_VENDORS(state, vendors) {
        state.vendors = vendors;
    },
  },
  actions: {
    login({commit}, user) {
        return new Promise((resolve, reject) => {
            commit('AUTH_REQUEST');
            ApiService.login(user)
            .then((resp: any) => {
                const authHeader = 'Authorization';
                axios.defaults.headers.common[authHeader] = `Bearer ${resp.data.access_token}`;
                commit('AUTH_SUCCESS', { token: resp.data.access_token, username: resp.data.username});
                resolve(resp);
            })
            .catch((err: any) => {
                commit('AUTH_ERROR');
                reject(err);
            });
        });
    },
    logout({commit}) {
        commit('AUTH_LOGOUT');
    },
    fetchBusinessUnits({ commit }) {
        ApiService.getBusinessUnits().then( (response) => {
            commit('FETCH_BUSINESS_UNITS', response.data);
        });
    },
    fetchDivisions({ commit }) {
        ApiService.getDivisions().then( (response) => {
            commit('FETCH_DIVISIONS', response.data);
        });
    },
    fetchStaffMembers({ commit }) {
        StaffApiService.getStaffMembers().then( (response) => {
            commit('FETCH_STAFF_MEMBERS', response.data);
        });
    },
    fetchServers({ commit }) {
        ServerApiService.getServers().then( (response) => {
            commit('FETCH_SERVERS', response.data);
        });
    },
    fetchAppServices({ commit }) {
        ApplicationApiService.getAppServices().then( (response) => {
            commit('FETCH_APP_SERVICES', response.data);
        });
    },
    fetchProjects({ commit }) {
        ProjectApiService.getProjects().then( (response) => {
            commit('FETCH_PROJECTS', response.data);
        });
    },
    fetchProjectTypes({ commit }) {
        ProjectApiService.getProjectTypes().then( (response) => {
            commit('FETCH_PROJECT_TYPES', response.data);
        });
    },
    fetchChanges({ commit }) {
        ChangeApiService.getChanges().then( (response) => {
            commit('FETCH_CHANGES', response.data);
        });
    },
    fetchVendors({ commit }) {
        VendorApiService.getVendors().then( (response) => {
            commit('FETCH_VENDORS', response.data);
        });
    },
  },
});
