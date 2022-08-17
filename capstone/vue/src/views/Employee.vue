<template>
<div>
  <div class="tabs">
  <ul>
    <li class="{'is-active' :submitMaintenanceRequest}" ><a @click="addProps">View Maintenance Requests</a></li>
<div v-if="submitMaintenanceRequest">
    <maintenance-list />
</div>
  </ul>
</div>
<employee-comp />
</div>
</template>

<script>
import EmployeeComp from '../components/employeeComp.vue';
import MaintenanceList from "../components/MaintenanceList.vue";
export default {
  components: {
    MaintenanceList,
    EmployeeComp,
  },
};
</script>

<style>
</style>
MaintenanceList