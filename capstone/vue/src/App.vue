<template>
<body>
  <div id="app">
    <div id="nav">
      
      
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
      <router-view />
      

    </div>
    
  </div>
  </body>
</template>

<script>


export default {
components: {
  
}
}
</script>

<style>

</style>

