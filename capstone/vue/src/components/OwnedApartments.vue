<template>
  <div>
      <!--List of owned Apartments -->
<div v-show="showAllOwned" id="ownedPond">
<div id="houseCard" v-for="apartment in ownedApartments" v-bind:key="apartment.id" class="card" v-on:click="detailedView(apartment); showAllOwned = false; showDetailed = true">
      <p>{{apartment.address}}</p>
      <img v-bind:src="apartment.imgURL" alt="Placeholder image" class="is-inline-block" />
      
      <p class="content">Rent - ${{apartment.price}}.00</p>
    </div>
</div>


<!--Detailed View -->
<div v-show="showDetailed">
 <div class="buttons">
    <button class="button is-primary">Maintenance Request </button>
    <button class="button is-primary">Make a Payment</button>
    <button class="button is-primary" @click="showDetailed = false; showAllOwned = true;">Back</button>
    </div>
  <div class="tile is-ancestor is-vertical m-6">
    <div class="tile is-ancestor is-horizontal">
  <div class="tile is-4 is-vertical is-parent">
    <div class="tile is-child box">
      <p class="title">Property Info</p>
      <p class="is-size-4">Address: {{apartment.address}}</p>
      <p class="is-size-5">Rent: ${{apartment.price}}</p>
    </div>
    <div class="tile is-child box">
      <p class="title">Room Info</p>
      <p class="is-size-4">Bedrooms: {{apartment.bedrooms}}</p>
      <p class="is-size-4">Bathrooms: {{apartment.bathrooms}}</p>
    </div>
  </div>
  <div class="tile is-parent is-vertical">
    <div class="tile is-child box">
      <p class="title">Description</p>
      <p>
          {{apartment.description}}
      </p>
     </div>
  </div>
    </div>
    <div class="tile is-child box">
      <p class="title">Photos</p>
      <img v-bind:src="apartment.imgURL" alt="Placeholder image" class="is-inline-block" />

     </div>
  </div>
</div>

  </div>
</template>

<script>
import ApartmentService from '../services/apartmentService'

export default {
data() {
    return {
        ownedApartments: [],
        showAllOwned: true,
        apartment: {
                rentalID: '',
                address: "",
                bedroom: "",
                bathroom: "",
                typeOfResidence: "",
                isRented: '',
                price: "",
                description: '',
                imgURL: '',
                landlord_id: ''
    },
    showDetailed: false
}
},

created() {
this.getOwnedApartments();
console.log(this.$store.state.user.id);
},

methods: {

    getOwnedApartments() {
        ApartmentService.getApartmentsOwned(this.$store.state.user.id).then(response => {
            this.ownedApartments = response.data;
        })
    },
    detailedView(apt) {
        this.apartment.rentalID = apt.rentalID
        this.apartment.address = apt.address
        this.apartment.bedroom = apt.bedroom
        this.apartment.bathroom = apt.bathroom
        this.apartment.typeOfResidence = apt.typeOfResidence
        this.apartment.isRented = apt.isRented
        this.apartment.price = apt.description
        this.apartment.imgURL = apt.imgURL
        this.apartment.description = apt.description
        this.apartment.landlord_id = apt.landlord_id
    },
    clearApt() {
        this.apartment.rentalID ='';
        this.apartment.address = '';
        this.apartment.bedroom = '';
        this.apartment.bathroom = '';
        this.apartment.typeOfResidence = '';
        this.apartment.isRented = '';
        this.apartment.price = '';
        this.apartment.imgURL = '';
        this.apartment.description = '';
        this.apartment.landlord_id = '';
    }
}
}
</script>

<style>
div#ownedPond {
  display: flex;
  flex-wrap: wrap;
  margin-right: 200px;
}
</style>