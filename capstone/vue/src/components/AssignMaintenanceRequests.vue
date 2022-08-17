<template>
<div class="m-6">
   <form id="Assign Maintenance Request">
       <h2 class="title">Employee Assignment Form</h2>
       <label>Employee Name</label>
       <input type="name" required v-model="name"/>
       <label>Employee ID</label>
       <input type="employee id" required v-model="userID">
       <label>Maintenance ID</label>
       <input type="maintenance id" required v-model="maintenanceID"/>
       <label>Rental ID</label>
       <input type="rental id" required v-model="rentalID"/>
       <label>Address</label>
       <input type="address" required v-model="address"/>
       <div class = "select is-primary">
           <select>
               <option>Priority</option>
               <option>High</option>
               <option>Moderate</option>
               <option>Low</option>
           </select>
               <button class ="button is-primary">Submit</button>
               
       </div>
   </form>
</div>
</template>
<script>
import maintenanceService from '../services/maintenanceService';
export default {
  data() {
    return {
      form:{
      name: " ",
      userID: " ",
      maintenanceID: " ",
      rentalID: " ",
      address: " ",
      }
    };
  },
methods:{
  submitForm(){
    maintenanceService.assignMaintenance(this.form).then(resp => {
      if(resp.status == 201)
      {
        alert("Successfully assigned the maintenance request");
        this.$router.push('/landlord');
      }
    })
  }
}
};
</script>
<style>
col h1 {
  text-align: center;
}
h2 {
  text-align: center;
}
form#rentalPay {
  max-width: 420px;
  margin: 30px auto;
  background: hsl(48, 100%, 29%);
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