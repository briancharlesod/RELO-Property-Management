
<template>
  <body>
    <form id="maintenance">
      <h1 class="subtitle">
        <img
          id="logo"
          src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTsdKWpDUN34PFuI52xPmmvYDI-okHioswhgA&usqp=CAU"
        />
      </h1>
      <h2 class="subtitle">Maintenance Request Form</h2>
      <label>Email:</label>
      <input type="email" required v-model="email" />
      <label>Name:</label>
      <input type="name" required v-model="name" />
      <label>Address:</label>
      <input type="address" required v-model="address" />
      <p>Email: {{ email }}</p>
      <p>Name: {{ name }}</p>
      <p>Address: {{ address }}</p>
      <textarea
        class="textarea"
        placeholder="What needs fixed?"
        rows="10"
        v-model="newMaintenanceRequest.maintenanceRequest"
      ></textarea>
      <input date="Date" type="date" />
      <p></p>
      <button class="button" @click="submitRequest()">Submit</button>
      <button v-on:click.prevent="resetForm" type="cancel">Cancel</button>
    </form>
  </body>
</template>
<script>
import maintenanceService from '../services/maintenanceService';
export default {

  data() {
    return {
      newMaintenanceRequest: {
      rentalID: this.$store.state.user.id,
      maintenanceRequest: "",
      completed: false
    },
    name:"",
    email:"",
    address:""
  };
},
methods:{
  submitRequest()
  {
      maintenanceService.request(this.newMaintenanceRequest).then(resp => {
        if(resp == 201)
        {
          this.clearForm();
          alert("Request Submitted")
          this.$router.push("/renter");
        }
      })
  }
}
};
</script>
<style>
textarea {
  height: 176px;
  width: 415px;
}

body {
  background-color: #80d0f0;
}

h1 {
  text-align: center;
}
h2 {
  text-align: center;
}

form#maintenance {
  max-width: 420px;
  margin: 30px auto;
  background: #00d1b2;
  text-align: left;
  padding: 40px;
  border-radius: 10px;
}
label {
  color: whitesmoke;
  display: inline-block;
  margin: 25px 0 15px;
  font-size: 0.6em;
  text-transform: uppercase;
  letter-spacing: 1px;
  font-weight: bold;
}
input {
  display: block;
  width: 100%;
  box-sizing: border-box;
  border: none;
  border-bottom: 1px solid#ddd;
  color: #555;
}
</style>