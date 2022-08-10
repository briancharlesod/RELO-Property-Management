<template>
  <div class="home">
    <!--<div id="banner">
      <img id="logo" src="../assets/LogoMakr-9Gar3F.png" />
      <router-link id="login" tag="button" v-bind:to="{ name: 'login' }">Sign In / Sign Up</router-link>
    </div>-->
    <div id="page">
    <div id="cardPond">
    <div id="houseCard" v-for="house in houses" v-bind:key="house.id" class="card" v-on:click="viewCardDetails(house.id)">
      <p>{{house.name}}</p>
      <img v-bind:src="house.thumbnail_url" alt="Placeholder image" class="is-inline-block" />
      <p class="content">{{house.address}}</p>
      <p class="content">${{house.price * 10}}.00</p>
    </div>
    </div>
    </div>
  </div>
</template>
<script>
import ApiService from '../services/newapiservice'
export default {
  name: "home",
  data() {
    return {
    houses: []
    }
  },
   methods: {
    viewCardDetails(cardID) {
      this.$store.commit("SET_HOUSES", this.houses);
      this.$router.push(`/${cardID}`);

    },
   },
created() {
 ApiService.getHouses().then((response => {
   this.houses = response.data.content.list;
 }));
}
};
</script>
<style>
div#banner {
  display: flex;
  justify-content: space-between;
  background: limegreen;
}
div#cardPond {
  display: flex;
  flex-wrap: wrap;
  margin-right: 200px;
}
div#page {
  display: flex;
}
div#houseCard {
  font-weight: bold;
  text-align: center;
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  width: 300px;
  margin-right: 40px;
  margin-left: 40px;
  margin-top: 15px;
  margin-bottom: 15px;
  border: solid black 2px;
}
nav#search {
  width: 250px;
  background: chocolate;
  height: 100%;
}
#login {
}
</style>