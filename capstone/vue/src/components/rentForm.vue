<template>
  <form class = "m-6">
<h1 class="subtitle"> <img id="logo" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTsdKWpDUN34PFuI52xPmmvYDI-okHioswhgA&usqp=CAU" /></h1>
<h2 class="subtitle">Rental Payment Form</h2>
<h3 class = "subtitle has-text-white">Rent amount: ${{rentAmount}}</h3>
    <label>Email:</label>
    <input type="email" required v-model="rentalForm.email" />
    <label>Name:</label>
    <input type="name" required v-model="rentalForm.name" />
    <label>Address:</label>
    <input type="address" required v-model="rentalForm.address" />
    <p>Email: {{ rentalForm.email }}</p>
    <p>Name: {{ rentalForm.name }}</p>
    <p>Address: {{ rentalForm.address }}</p>
    <div class="select" >
      <select v-model="rentalForm.type">
        <option>Select Payment</option>
        <option>Cash</option>
        <option>Check</option>
        <option>Money Order</option>
      </select>
      <div class="field has-addons">
  <!--<p class="control">
    <span class="select">
    </span>
  </p>-->
      </div>
    </div>
      <div>
  <p class="control">
    <input class="input" type="number" placeholder="Amount of money" v-model="rentalForm.rent">
  </p>
</div>
<div>
<input date = "Date" type = "date" v-model="rentalForm.date"/>
    </div>
    <div class="field is-grouped">
 <button class="button" @click="payRent()">Submit</button>
 <button class="button" @click="clearForm()">Cancel</button>
</div>
  </form>
</template>
<script>
import ApartmentService from '../services/apartmentService'
export default {
  data() {
    return {
      rentalForm:{
      email: "",
      name: "",
      address: "",
      rent: "",
      date: "",
      type: "",
      rentalID: this.$store.state.user.id
      },
      rentAmount: "",
      due: ""
    };
  },
  methods: {
    payRent()
    {
      ApartmentService.payRent(this.rentalForm).then(resp => {
        if(resp == 200)
        {
          this.clearForm();
          alert("Payment Submitted")
          this.$router.push("/renter");
        }
      })
    },
    clearForm()
    {
        this.rentalForm.email = "";
        this.rentalForm.name = "";
        this.rentalForm.address = "";
        this.rentalForm.rent = "";
        this.rentalForm.date = "";
        this.rentalForm.type = "";
        this.rentAmount = "";
        this.due = "";
    }
  },
  created(){
    //view rent
    ApartmentService.viewRent(this.$store.state.user.id).then(resp => {
      this.rentAmount = resp.data;
    });
    ApartmentService.getDueDate().then(response => {
      this.due = response.data;
    })
  }
};
</script>
<style>

h1{
  text-align: center;
}
h2{
  text-align: center;
}
form#rentForm {
  max-width: 420px;
  margin: 30px auto;
  background: hsl(48, 26%, 48%);
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