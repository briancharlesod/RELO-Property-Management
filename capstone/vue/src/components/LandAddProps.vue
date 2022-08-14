<template>
<div id="container">
  <form v-on:submit.prevent="submitRental(); landlordHome">
  <div class="field is-vertical">


<div class="field is-horizontal">
  <div class="field-label is-normal">
    <label class="label">Address</label>
  </div>
  <div class="field-body">
    <div class="field">
      <div class="control">
        <input class="input" type="text" placeholder="Enter the Address" v-model="newProperty.address">
      </div>
    </div>
  </div>
</div>
<div class="field is-horizontal">
  <div class="field-label is-normal">
    <label class="label">Rent</label>
  </div>
  <div class="field-body">
    <div class="field">
      <div class="control">
        <input class="input" type="number" placeholder="Enter the Rent" v-model="newProperty.price">
      </div>
    </div>
  </div>
</div>

<div class="field is-horizontal">
  <div class="field-label is-normal">
    <label class="label">Bedrooms</label>
  </div>
  <div class="field-body">
    <div class="field">
      <div class="control">
        <input class="input" type="number" placeholder="Number of Bedrooms" v-model="newProperty.bedroom">
      </div>
    </div>
  </div>
</div>

<div class="field is-horizontal">
  <div class="field-label is-normal">
    <label class="label">Bathrooms</label>
  </div>
  <div class="field-body">
    <div class="field">
      <div class="control">
        <input class="input" type="number" placeholder="Number of Bathrooms" v-model="newProperty.bathroom">
      </div>
    </div>
  </div>
</div>

<div class="field is-horizontal">
  <div class="field-label is-normal">
    <label class="label">description</label>
  </div>
  <div class="field-body">
    <div class="field">
      <div class="control">
        <input class="input" type="text" placeholder="Description" v-model="newProperty.description">
      </div>
    </div>
  </div>
</div>

<div class="field is-horizontal">
  <div class="field-label is-normal">
    <label class="label">Type of Residence</label>
  </div>
  <div class="field-body">
    <div class="field">
      <div class="control">
        <input class="input" type="text" placeholder="Enter the type of residence" v-model="newProperty.typeOfResidence">
      </div>
    </div>
  </div>
</div>
</div>
<input type="submit" class="button is-primary" value="Submit Property" />
  </form>
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
                address: "",
                bedroom: "",
                bathroom: "",
                typeOfResidence: "",
                isRented: false,
                price: "",
                description: '',
                imgURL: 'placeholder',
                landlord_id: '3'
            }
    }
},
methods: {
    landlordHome()
    {
        this.$router.push("/");
    },
    clearForm() {
      this.newProperty.bathroom = '';
      this.newProperty.price = '';
      this.newProperty.bedroom = '';
      this.newProperty.address = '';
      this.newProperty.isRented = false;
      this.newProperty.typeOfResidence = '';
      this.newProperty.description = '';
    },
    submitRental() {
      this.newProperty.price = parseFloat(this.newProperty.price)
      this.newProperty.bedroom = parseInt(this.newProperty.bedroom)
      this.newProperty.bathroom = parseInt(this.newProperty.bathroom)
      try {
      ApartmentService.addApartment(this.newProperty).then(response => {
        console.log(response.status)
        if (response.status == 201) {
          this.clearForm();
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

</style>