<template>
    <div id = "container">
        <!--
  <div class="tabs">
  <ul>
    <li v-bind:class="{ 'is-active' :addPropsVar}" ><a @click="showManageProps = true" >Manage Properties</a></li>
    
    <li v-bind:class="{ 'is-active' :updatePropsVar}"><a @click="updateProps">Update Properties</a></li>
    <li v-bind:class="{ 'is-active' : viewRentsVar}"><a @click="viewRents">View Rents</a></li>
    <li v-bind:class="{ 'is-active' :assignRentersVar}"><a @click="assignRenters">Assign Renters to Property</a></li>
    <li v-bind:class="{ 'is-active' :assignMaintnanceVar}"> <a @click="assignMaintenance">Assign Maintenance Request</a></li>
    
  </ul>
</div>


--
<div v-if="addPropsVar">
    <h3>Add Properties</h3>
    <land-add-props />
    
</div>
-->
<land-add-props />

    </div>

</template>

<script>

import LandAddProps from '@/components/LandAddProps';


export default {
  components: {
       LandAddProps
       
    
  },
    data() {
        return {
            newProperty: {
                address: "",
                bedrooms: "",
                bathrooms: "",
                
                isRented: false,
                price: "",
                typeOfResidence: '',
                
            },
            addPropsVar : false,
            updatePropsVar: false,
            viewRentsVar: true,
            assignRentersVar: false,
            assignMaintenanceVar: false,
            showManageProps: false
        }
    },
    methods: {

        addProps() {
            this.addPropsVar = true;
            this.updatePropsVar = false;
            this.viewRentsVar = false;
            this.assignRentersVar = false;
            this.assignMaintenanceVar = false;
        },
        updateProps(){
            this.addPropsVar = false;
            this.updatePropsVar = true;
            this.viewRentsVar = false;
            this.assignRentersVar = false;
            this.assignMaintenanceVar = false;
        },
        viewRents(){
            this.addPropsVar = false;
            this.updatePropsVar = false;
            this.viewRentsVar = true;
            this.assignRentersVar = false;
            this.assignMaintenanceVar = false;
        },
        assignRenters() {
            this.addPropsVar = false;
            this.updatePropsVar = false;
            this.viewRentsVar = false;
            this.assignRentersVar = true;
            this.assignMaintenanceVar = false;
        },
        assignMaintenance(){
            this.addPropsVar = false;
            this.updatePropsVar = false;
            this.viewRentsVar = false;
            this.assignRentersVar = false;
            this.assignMaintenanceVar = true;
        }

    }

}
</script>

<style>

</style>