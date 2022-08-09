<template>
  <div id="app">
    <div id="nav">
      <nav
        class="navbar has-background-grey-lighter"
        role="navigation"
        aria-label="main navigation"
      >
        <div class="navbar-brand">
          <a class="navbar-item">
            <router-link :to="{ name: 'home' }">
              <i class="fa-solid fa-building-user fa-4x"></i>
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
              <strong>Insert Company Name Here!</strong>
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
                    <strong>Sign up</strong>
                  </router-link>
                </a>
                <router-link :to="{ name: 'login' }">
                  <a class="button is-light"> Log in </a>
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
    </div>
    <router-view />
  </div>
</template>


<style >
.navbar-brand {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
</style>