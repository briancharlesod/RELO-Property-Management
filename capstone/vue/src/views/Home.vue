<template>
  <div class="home">
    <div id="banner">
      <img id="logo" src="../assets/LogoMakr-9Gar3F.png" />
      <router-link id="login" tag="button" v-bind:to="{ name: 'login' }">Sign In / Sign Up</router-link>
    </div>


    
  </div>
</template>

<script>
export default {
  name: "home"
};
</script>
<style>
div#banner {
  display: flex;
  justify-content: space-between;
  background: limegreen;
}



</style>