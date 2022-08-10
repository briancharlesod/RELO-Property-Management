<template>
<div id = "container">
    <div class="buttons">
  <button class="button is-info m-5" @click="goBack">Back to Results</button>
    </div>
  <div class="tile is-ancestor is-vertical m-6">
    <div class="tile is-ancestor is-horizontal">
  <div class="tile is-4 is-vertical is-parent">
    <div class="tile is-child box">
      <p class="title">Property Info</p>
      <p class="is-size-4">Address: 123 Test Street {{property.city}} {{property.state}}, {{property.zipcode}}</p>
      <p class="is-size-5">Rent: ${{property.price * 10}}/Month</p>
    </div>
    <div class="tile is-child box">
      <p class="title">Room Info</p>
      <p class="is-size-4">Bedrooms: {{property.bedrooms}}</p>
      <p class="is-size-4">Bathrooms: {{property.bathrooms}}</p>
    </div>
  </div>
  <div class="tile is-parent is-vertical">
    <div class="tile is-child box">
      <p class="title">Description</p>
      <p>
          {{property.description}}
      </p>
      <p>
          {{property.summary}}
      </p>
      <p>
          {{property.space}}
      </p>
     </div>
  </div>
    </div>
    <div class="tile is-child box">
      <p class="title">Photos</p>
      <img v-bind:src="property.picture_url" alt="Placeholder image" class="is-inline-block" />

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
    this.property = this.$store.state.house.find(prop => {
        return prop.id === this.$route.params.id;
    });
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