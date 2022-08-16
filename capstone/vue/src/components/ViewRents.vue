<template>
  <table class="table">
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
      <tr v-for="props in properties" v-bind:key="props">
        <th>{{ props.num }}</th>
        <td>{{ props.address }}</td>
        <td>{{ props.rent }}</td>
        <td>{{ props.daysTillDue }}</td>
        <td>{{ props.status }}</td>
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
      todaysDate: new Date(),
      dueDate: "",
      rentedProperties: {
        num: "",
        address: "",
        rent: "",
        daysTillDue: "",
        status: "",
      },
    };
  },
  method: {
    difference(date1, date2) {
      const date1utc = Date.UTC(
        date1.getFullYear(),
        date1.getMonth(),
        date1.getDate()
      );
      const date2utc = Date.UTC(
        date2.getFullYear(),
        date2.getMonth(),
        date2.getDate()
      );
      let day = 1000 * 60 * 60 * 24;
      return (date2utc - date1utc) / day;
    },
  },
  created() {
    let daysTillDue = new Date();
    daysTillDue.setMonth(daysTillDue.getMonth + 1);
    daysTillDue.setDate(2);
    apartmentService.getRents(this.$store.state.user.id).then((response) => {
      if (response.status == 200) {
        this.properties = response.data;
      }
    });
    apartmentService.getDueDate().then((res) => {
      if (res.status == 200) {
        this.dueDate = res.data;
      }
    });
  },
};
</script>

<style>
</style>