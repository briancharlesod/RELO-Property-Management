<template>
  <table class="table">
    <thead>
      <tr>
        <th><abbr title="Maintenance ID">Maintenance ID</abbr></th>
        <th><abbr title="Request">Request</abbr></th>
        <th><abbr title="Completed">Completed</abbr></th>
        <th><abbr title="Rental ID">Rental ID</abbr></th>
        <th><abbr title="Mark as Complete">Mark as Complete</abbr></th>
      </tr>
    </thead>
    <tfoot>
      <tr>
        <th><abbr title="Maintenance ID">Maintenance ID</abbr></th>
        <th><abbr title="Request">Request</abbr></th>
        <th><abbr title="Completed">Completed</abbr></th>
        <th><abbr title="Rental ID">Rental ID</abbr></th>
        <th><abbr title="Mark as Complete">Mark as Complete</abbr></th>
      </tr>
    </tfoot>
    <tbody>
      <tr v-for="request in requests" v-bind:key="request">
        <td>{{ request.maintenanceID }}</td>
        <td>{{ request.maintenanceRequest }}</td>
        <td>{{ request.completed }}</td>
        <td>{{ request.rentalID }}</td>
        <td><button v-if="!request.completed" @click="markTrue(request.maintenanceID)">Mark as {{ completedOrNot(request.completed) }} </button></td>
      </tr>
    </tbody>
  </table>
</template>

<script>
import maintenanceService from "../services/maintenanceService";
export default {
  data() {
    return {
      requests: [],
    };
  },
  created() {
    maintenanceService.getAllRequests().then((resp) => {
      console.log(resp.data);
      if (resp.status == 200) {
        this.requests = resp.data;
      }
    });
  },
  methods: {
    markTrue(request){
      maintenanceService.setComplete(request).then(resp => {
        if(resp.status == 201)
        {
          this.$router.go();
        }
      })
    },
    completedOrNot(request)
    {
      if(request == false)
      {
        return "Complete";
      }
      return "Incomplete";
    }
  }
};
</script>

<style>
</style>