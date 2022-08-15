<template>
<div id="container">
 
  <div v-show="showAddForm">
    <div id="editButtons">
  <button class="button is-primary">Assign Renters</button>
<button class="button is-primary">View Maintenance Requests</button>
<button class="button is-primary">View Rents</button>
<button class="button is-primary">Delete Property</button>
<button class="button is-primary" v-on:click="clearForm(); showAddForm = false">Back</button>
</div>


  <form id="manageProps" v-on:submit.prevent="submitRental(); landlordHome">

    <div id = "container">
    <div class="buttons">
  
    </div>
  <div class="tile is-ancestor is-vertical m-6">
    <div class="tile is-ancestor is-horizontal">
  <div class="tile is-4 is-vertical is-parent">
    <div class="tile is-child box">
      <p class="title">Property Info</p>
      <p class="is-size-4">Address: <input class="is-size-4" type="text" v-model="newProperty.address" /></p>
      <p class="is-size-4">Rent: <input class="is-size-4" type="text" v-model="newProperty.price"/></p>
    </div>
    <div class="tile is-child box">
      <p class="title">Room Info</p>
      <p class="is-size-4">Bedrooms: <input  type="number" class="is-size-4" v-model="newProperty.bedroom"/></p>
      <p class="is-size-4">Bathrooms: <input type="number" class="is-size-4" v-model="newProperty.bathroom"/></p>
    </div>
  </div>
  <div class="tile is-parent is-vertical">
    <div class="tile is-child box">
      <p class="title">Description</p>
      <p>
          <textarea class="textarea is-large is-size-6 " type="text" v-model="newProperty.description" />
      </p>
     </div>
  </div>
    </div>
    <div class="tile is-child box">
      <p class="title">Photos</p>
      <img v-bind:src="newProperty.imgURL" alt="Placeholder image" class="is-inline-block" />
      <input class="is-size-4" type="text" v-model="newProperty.imgURL" />

     </div>
  </div>
</div>

<input id="saveButton" type="submit" class="button is-primary" value="SAVE" />

  </form>

  


  </div>


  <!--List Apartments by landlord -->
<div id="listApartments" v-show="!showAddForm">
<div id="houseCard" v-for="apartment in apartments" v-bind:key="apartment.id" class="card" >
      <p>{{apartment.address}}</p>
      <img v-bind:src="apartment.imgURL" alt="Placeholder image" class="is-inline-block" />
      <p class="content">{{apartment.typeOfResidence}}</p>
      <p class="content">${{apartment.price}}.00</p>
      <button class="button" v-on:click="editForm(apartment); showAddForm = true">EDIT</button>
    </div>
<div id="houseCard" class="card" v-on:click="showAddForm = true">
  <p class="is-size-1">+</p>
<p class="is-size-4">Add New Property</p>
</div>
</div>
</div>

</template>

<script>
import ApartmentService from '../services/apartmentService'

export default {
data() {
    return {
        test: true,
        falseTest: false,
        newProperty: {
                rentalID: 0,
                address: "",
                bedroom: "",
                bathroom: "",
                typeOfResidence: "house",
                isRented: false,
                price: "",
                description: '',
                imgURL: 'placeholder',
                landlord_id: this.$store.state.user.id
            },
        showAddForm: false,
        apartments: []
    }
},

created() {
      this.getApartments();
      this.clearForm();
      this.showAddForm = false;
      
    },

methods: {
    landlordHome()
    {
        this.$router.push("/");
    },
   
    editForm(rental) {
      this.newProperty.rentalID = rental.rentalID;
      this.newProperty.bathroom = rental.bathroom;
      this.newProperty.price = rental.price;
      this.newProperty.bedroom = rental.bedroom;
      this.newProperty.address = rental.address;
      //this.newProperty.isRented = rental.rented;
      //this.newProperty.typeOfResidence = rental.typeOfResidence;
      this.newProperty.description = rental.description;
      this.newProperty.imgURL = rental.imgURL;
    },

    submitRental() {
      if (this.newProperty.rentalID == 0) {
        this.newProperty.rentalID = '';
        this.addRental();
        this.showAddForm = false;
      } else {
        this.updateForm();
        this.showAddForm = false;
      }
      
    },

    updateForm() {
      try{
        ApartmentService.updateApartment(this.newProperty).then(response => {
        if (response.status == 200) {
          this.clearForm();
          alert("Worked edit");
          this.getApartments();
        }}
        );
      } catch (e) {
        console.log(e)
      }
    },


    clearForm() {
      this.newProperty.bathroom = '';
      this.newProperty.price = '';
      this.newProperty.bedroom = '';
      this.newProperty.address = '';
      this.newProperty.isRented = false;
      //this.newProperty.typeOfResidence = '';
      this.newProperty.description = '';
      this.newProperty.imgURL = 'placeholder',
      this.newProperty.rentalID = 0
    },
    getApartments() {
      try {
      ApartmentService.getApartmentByLandlord(this.$store.state.user.id).then(response => {
        this.apartments = response.data;
      })
      } catch (e) {
        console.log(e);
      }

    },
    addRental() {
      this.newProperty.price = parseFloat(this.newProperty.price)
      this.newProperty.bedroom = parseInt(this.newProperty.bedroom)
      this.newProperty.bathroom = parseInt(this.newProperty.bathroom)
      try {
      ApartmentService.addApartment(this.newProperty).then(response => {
        if (response.status == 201) {
          this.clearForm();
          this.getApartments();
          alert("Worked")
        }
      })
      } catch (e) {
        console.log(e)
      }
    }
}
}
</script>

<style>
div#listApartments {
  display: flex;
  flex-wrap: wrap;
  margin-right: 200px;
}

div#addCard {
  width: 300px;
}




</style>