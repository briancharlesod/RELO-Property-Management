<template>
  <div id="app">
    <div id="nav">

      <nav
        class="navbar"
        role="navigation"
        aria-label="main navigation"
      >
        <div class="navbar-brand">
          <a class="navbar-item">
            <router-link :to="{ name: 'home' }">
              <img id="logo" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTsdKWpDUN34PFuI52xPmmvYDI-okHioswhgA&usqp=CAU" />
              <br />
            </router-link>
          </a>

          <a
            role="button"
            class="navbar-burger"
            aria-label="menu"
            aria-expanded="false"
            data-target="navbarBasicExample"
          >
            <span aria-hidden="true"></span>
            <span aria-hidden="true"></span>
            <span aria-hidden="true"></span>
          </a>
        </div>
 
        <div id="navbarBasicExample" class="navbar-menu">
          <div class="navbar-start">
            <a class="navbar-item">
               <router-link :to="{ name: 'home' }">
              <strong>J-LO Property Management</strong>
               </router-link>
            </a>

            <!--<div class="navbar-item has-dropdown is-hoverable">
              <a class="navbar-link"> More </a>

              <div class="navbar-dropdown">
                <a class="navbar-item"> About </a>
                <a class="navbar-item"> Jobs </a>
                <a class="navbar-item"> Contact </a>
                <hr class="navbar-divider" />
                <a class="navbar-item"> Report an issue </a>
              </div>
            </div> -->
          </div>

          <div class="navbar-end">
            <div class="navbar-item">
              <div class="buttons">
                <a class="button is-primary">
                  <router-link :to="{ name: 'register' }">
                    Sign up
                  </router-link>
                </a>
                <router-link :to="{ name: 'login' }" v-if="Object.keys(this.$store.state.user).length === 0">
                  <a class="button is-light"> Log in </a>
                </router-link>
                <router-link :to="{ name: 'logout' }" v-else>
                  <a class="button is-light" :style="{color: '#00d1b2'}"> Log out </a>
                </router-link>
              </div>
            </div>
          </div>
        </div>
      </nav>
      <!--<router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <router-link
        v-bind:to="{ name: 'logout' }"
        v-if="$store.state.token != ''"
        >Logout</router-link
      >-->

      <div class="tabs" >
  <ul v-if="Object.keys(this.$store.state.user).length != 0">
    <li v-bind:class="{ 'is-active' :addPropsVar}"><a @click="$router.push('/landlord')">Manage Properties</a></li>
    <li v-bind:class="{ 'is-active' :addPropsVar}"><a @click="$router.push('/renter')">Your Apartments</a></li>
    <!--
    <li v-bind:class="{ 'is-active' :updatePropsVar}"><a @click="updateProps">Update Properties</a></li>
    <li v-bind:class="{ 'is-active' : viewRentsVar}"><a @click="viewRents">View Rents</a></li>
    <li v-bind:class="{ 'is-active' :assignRentersVar}"><a @click="assignRenters">Assign Renters to Property</a></li>
    <li v-bind:class="{ 'is-active' :assignMaintenanceVar}"> <a @click="assignMaintenance">Assign Maintenance Request</a></li>
    -->
  </ul>
</div>


    </div>
    <router-view />

 


  </div>
</template>
<script>


</script>

<style >
a:link{
  color:rgb(105, 15, 105)
}

.navbar-brand {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.navbar{
    background: rgb(243, 133, 151);
}

</style>