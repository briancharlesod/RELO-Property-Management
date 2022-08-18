<template>
  <table class="table m-auto">
    <thead>
      <tr>
        <th><abbr title="Position">Num</abbr></th>
        <th>Address</th>
        <th><abbr title="Played">Rent Amount</abbr></th>
        <th><abbr title="Won">Days Till Rent is Due</abbr></th>
        <th><abbr title="Drawn">Status</abbr></th>
      </tr>
    </thead>
    <tfoot>
      <tr>
        <th><abbr title="Position">Num</abbr></th>
        <th>Address</th>
        <th><abbr title="Played">Rent Amount</abbr></th>
        <th><abbr title="Won">Days Till Rent is Due</abbr></th>
        <th><abbr title="Drawn">Status</abbr></th>
      </tr>
    </tfoot>
    <tbody>
      <tr v-for="(props, count) in properties" v-bind:key="props">
        <th>{{ count + 1 }}</th>
        <td>{{ props.address }}</td>
        <td>${{ props.rentAmount }}</td>
        <td>{{ dueOrOverdue(props.rent) }} Days</td>
        <td>{{ status(props.rent) }}</td>
      </tr>
    </tbody>
  </table>
</template>

<script>
import apartmentService from "../services/apartmentService";
export default {
  data() {
    return {
      properties: [],
      rentedProperties: {
        num: "",
        address: "",
        rent: "",
        daysTillDue: "",
        status: "",
      },
    };
  },
  methods: {
    status(date1){
      if(this.dueOrOverdue(date1) > -1)
      {
        return "PAID";
      }
      return "OVERDUE"
    },
    dueOrOverdue(date1) {
      let Date1 = new Date(date1);
     let Date2 = new Date();
     let newDate = Date2;
      newDate.setMonth(newDate.getMonth() + 1);
      newDate.setDate(0);
      const diffTime = Math.abs(newDate - Date1);
      const diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24));
      let daysInMonth = newDate.getDate();
      if(diffDays < daysInMonth)
      {
          return Math.floor(Math.abs(newDate - new Date()) / (1000 * 60 * 60 * 24));
      }
      //let daysInPastMonth = newDate;
      //daysInPastMonth.setMonth(newDate.getMonth() - 1);
      //return  Math.floor(Math.abs(newDate - new Date()) / (1000 * 60 * 60 * 24)) - daysInPastMonth.getDate();
      let daysOverdue = new Date();
      return -daysOverdue.getDate();
    },
  },
  created() {
    apartmentService.getRents(this.$store.state.user.id).then((response) => {
      if (response.status == 200) {
        this.properties = response.data;
      }
    });
  },
};
</script>

<style>
</style>