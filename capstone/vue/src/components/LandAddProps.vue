<template>
<div id="container">
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
        <input class="input" type="number" placeholder="Number of Bedrooms" v-model="newProperty.bedrooms">
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
        <input class="input" type="number" placeholder="Number of Bathrooms" v-model="newProperty.bathrooms">
      </div>
    </div>
  </div>
</div>

<div class="field is-horizontal">
  <div class="field-label is-normal">
    <label class="label">Description</label>
  </div>
  <div class="field-body">
    <div class="field">
      <div class="control">
        <input class="input" type="text" placeholder="Enter a description of the property" v-model="newProperty.description">
      </div>
    </div>
  </div>
</div>
</div>
<button type="button" class="button is-primary" @click="landlordHome">Submit Property</button>
</div>
</template>

<script>
export default {
data() {
    return {
        test: true,
        falseTest: false,
        newProperty: {
                address: "",
                bedrooms: "",
                bathrooms: "",
                description: "",
                isRented: "",
                price: ""
            }
    }
},
methods: {
    landlordHome()
    {
        this.$router.push("/");
    }
}
}
</script>

<style>

</style>