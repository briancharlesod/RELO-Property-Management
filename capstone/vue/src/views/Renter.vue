<template>
<div>
  <div class="tabs">
  <ul>
    <li v-bind:class="{ 'is-active' :viewRentsVar}" ><a @click="viewRents">Pay Rent</a></li>
    <li v-bind:class="{ 'is-active' :browsePropsVar}"><a @click="browseProps">Browse Properties</a></li>
    <li v-bind:class="{ 'is-active' : submitMaintenanceVar}"><a @click="submitMaintenance">Submit Maintenance Request</a></li>
  </ul>
</div>
<div v-if="browsePropsVar">
    <browse />
</div>
<div v-if="viewRentsVar">
    <rent-form />
</div>
<div v-if="submitMaintenanceVar">
    <maintenance-form />
</div>
</div>
</template>

<script>
import browse from '../components/browse.vue';
import RentForm from '../components/rentForm.vue';
import MaintenanceForm from '../components/maintenanceForm.vue';

export default {
  components: { browse, RentForm, MaintenanceForm },
    data() {
        return {
            viewRentsVar: false,
            browsePropsVar: true,
            submitMaintenanceVar: false,
        }
    },
    methods: {

        browseProps() {
            this.viewRentsVar = false;
            this.browsePropsVar = true;
            this.submitMaintenanceVar = false;
        },
        submitMaintenance(){
            this.viewRentsVar = false;
            this.browsePropsVar = false;
            this.submitMaintenanceVar = true;
        },
        viewRents(){
            this.viewRentsVar = true;
            this.browsePropsVar = false;
            this.submitMaintenanceVar = false;
        },
    }
}
</script>

<style>
ul{
    background: white;
}
</style>