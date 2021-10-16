<template>
    <div>
        <b-form-group label="Name" label-for="name" horizontal>
            <b-input id="name" v-model="staff.name" :value="staff.name" required />
        </b-form-group>
        <b-form-group label="Title" label-for="title" horizontal>
            <b-input id="title" v-model="staff.title" :value="staff.title" />
        </b-form-group>
        <b-form-group label="Username" label-for="staff" horizontal>
            <b-input id="staff" v-model="staff.username" :value="staff.username" placeholder="Active Directory Username" required />
        </b-form-group>
        <b-form-group label="Email" label-for="email" horizontal>
            <b-input type="email" id="email" v-model="staff.email" :value="staff.email" required />
        </b-form-group>
    </div>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';

@Component
export default class StaffForm extends Vue {
  @Prop() private staff?: any;
}
</script>