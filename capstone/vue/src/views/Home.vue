<template>
<div>
  <browse />
<!--
  <div class="tabs" v-show="this.$store.state.user.authorities[0].name == 'ROLE_LANDLORD'">
    
  <ul>
    <li v-bind:class="{ 'is-active' :addPropsVar}" ><a @click="showManageProps = true" >Manage Properties</a></li>
    -----
    <li v-bind:class="{ 'is-active' :updatePropsVar}"><a @click="updateProps">Update Properties</a></li>
    <li v-bind:class="{ 'is-active' : viewRentsVar}"><a @click="viewRents">View Rents</a></li>
    <li v-bind:class="{ 'is-active' :assignRentersVar}"><a @click="assignRenters">Assign Renters to Property</a></li>
    <li v-bind:class="{ 'is-active' :assignMaintenanceVar}"> <a @click="assignMaintenance">Assign Maintenance Request</a></li>
    -----
  </ul>
</div>

<land-add-props v-show="showManageProps"/>
-->

</div>
</template>

<script>
import browse from '../components/browse'

export default {
  components: { browse },

data() {
  return {
    showManageProps: false
  }
},

created() {
  this.showManageProps = false;
  console.log(this.$store.state.user.authorities[0].name)
}

}
</script>

<style>

</style>