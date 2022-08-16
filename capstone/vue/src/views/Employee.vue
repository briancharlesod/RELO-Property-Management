<template>
  <div class="tabs">
  <ul>
    <li class="{'is-active' :submitMaintenanceRequest}" ><a @click="addProps">View Maintenance Requests</a></li>
<div v-if="submitMaintenanceRequest">
    <maintenance-list />
</div>
  </ul>
</div>

</template>

<script>
import MaintenanceList from "../components/MaintenanceList.vue";
export default {
  components: {
    MaintenanceList,
  },
};
</script>

<style>
</style>
MaintenanceList