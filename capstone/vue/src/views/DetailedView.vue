<template>
<div id = "container">
    <div class="buttons">
  <button class="button is-primary m-5" @click="goBack">Back to Results</button>
    </div>
  <div class="tile is-ancestor is-vertical m-6">
    <div class="tile is-ancestor is-horizontal">
  <div class="tile is-4 is-vertical is-parent">
    <div class="tile is-child box">
      <p class="title">Property Info</p>
      <p class="is-size-4">{{property.address}}</p>
      <p class="is-size-5">Rent: ${{property.price}}/Month</p>
    </div>
    <div class="tile is-child box">
      <p class="title">Room Info</p>
      <p class="is-size-4">Bedrooms: {{property.bedroom}}</p>
      <p class="is-size-4">Bathrooms: {{property.bathroom}}</p>
    </div>
  </div>
  <div class="tile is-parent is-vertical">
    <div class="tile is-child box">
      <p class="title">Description</p>
      <p>
          {{property.description}}
      </p>
     </div>
  </div>
    </div>
    <div class="tile is-child box">
      <p class="title">Photos</p>
      <img v-bind:src="property.imgURL" alt="Placeholder image" class="is-inline-block" />

     </div>
  </div>
</div>
</template>

<script>
export default {
data() {
    return {
        property: {}
        
    }
},

created() {
    this.property = this.$store.state.house;
},
methods: {
    goBack()
    {
        this.$router.back();
    }
}
}
</script>

<style>

</style>