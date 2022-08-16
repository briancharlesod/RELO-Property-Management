<template>
<div>
  <table>
    <thead>
      <tr>
        <th>&nbsp;</th>
        <th>Document Name</th>
        <th>Author</th>
        <th>Last Opened by me</th>
        <th>Actions</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="doc in requests" :key="doc.id">
        <!--<td class="docs-icon">
          <img src="../assets/icons8-google-docs-48.png" />
        </td>-->
        <td class="name">{{ doc.request }}</td>
        <td>
          <span class="ownedby">{{ doc.name }}</span>
        </td>
        <td>{{ doc.lastOpened }}</td>
        <td>
          <button v-on:click="viewRequest(doc.id)">View</button>&nbsp;
          <button v-on:click="deleteRequest(doc.id)">Delete</button>
        </td>
      </tr>
    </tbody>
  </table>
</div>
</template>

<script>
import maintenanceService from "../services/maintenanceService";

export default {
  name: "maintenance-list",
  data(){
    return{
      response: "",
      requests: []
    }
  },
  methods: {
    viewMaintenanceRequest(id) {
      this.$router.push(`/maintenance/all/${id}`);
    },
    deleteMaintenanceRequest(id) {
      maintenanceService
        .delete(id)
        .then(response => {
          if (response.status === 200) {
            this.getMaintenanceRequest();
          }
        })
        .catch(error => {
          if (error.response.status === 404) {
            this.$router.push("/404");
          } else {
            console.error(error);
          }
        });
    },
    getMaintenanceRequests() {
      maintenanceService.list(this.$store.state.user.id).then(response => {
        this.$store.commit("SET_MAINTENANCE_REQUESTS", response.data);
        //this.requests = response.data
      });
    }
  },
  created() {
          maintenanceService.list(this.$store.state.user.id).then((response) => {
            console.log(response)
            if(response.status == 200)
            {
              this.response = response;
              this.$store.commit("SET_MAINTENANCE_REQUESTS", response.data);
            }
      });
    this.getMaintenanceRequests();
  },
  computed: {
    sortedMaintenanceRequests() {
      return this.$store.state.maintenanceRequests
        .slice()
        .sort(
          (a, b) =>
            new Date(b.lastOpened.replace("th", "")) -
            new Date(a.lastOpened.replace("th", ""))
        );
    }
  }
};
</script>

<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
  margin: 0;
  padding: 0;
}
th {
  font-family: "Work Sans", sans-serif;
  font-weight: 500;
  text-align: left;
}

tr {
  margin: 20px;
  padding: 10px;
}
td {
  padding: 8px;
  font-family: "Work Sans", sans-serif;
}

td.name {
  font-weight: 400;
}
.docs-icon img {
  height: 32px;
}
.avatar {
  border-radius: 20px;
  width: 32px;
  vertical-align: middle;
  padding-right: 5px;
}
.ownedby {
  vertical-align: middle;
}
</style>
