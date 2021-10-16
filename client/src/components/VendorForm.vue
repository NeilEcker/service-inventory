<template>
    <div>
        <b-form-group label="Name" label-for="name" horizontal>
            <b-input id="name" v-model="vendor.name" :value="vendor.name" required />
        </b-form-group>
        <b-form-group label="Support URL" label-for="url" horizontal>
            <b-input type="url" id="url" v-model="vendor.url" :value="vendor.url" />
        </b-form-group>
        <b-form-group label="Contact" label-for="contact" horizontal>
            <b-input id="contact" v-model="vendor.contact" :value="vendor.contact" placeholder="Contact" required />
        </b-form-group>
    </div>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';

@Component
export default class VendorForm extends Vue {
  @Prop() private vendor?: any;
}
</script>