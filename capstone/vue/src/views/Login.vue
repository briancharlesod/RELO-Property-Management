<template>

  <div id="login" class="text-center content is-medium">
    <form class="form-signin" @submit.prevent="login">
      <h3 class="h3 mb-3 font-weight-normal">Please Sign In</h3>
      <div
        class="alert alert-danger"
        role="alert"
        v-if="invalidCredentials"
      >Invalid username and password!</div>
      <div
        class="alert alert-success"
        role="alert"
        v-if="this.$route.query.registration"
      >Thank you for registering, please sign in.</div>
      <p>  </p>
      <label for="username" class="sr-only">Username</label>
      <p>  </p>
      <input
        type="text"
        id="username"
        class="form-control input is-rounded"
        placeholder="Username"
        v-model="user.username"
        required
        autofocus
      />
      <p>  </p>
      <label for="password" class="sr-only">Password</label>
      <p>  </p>
      <input
        type="password"
        id="password"
        class="form-control input is-rounded"
        placeholder="Password"
        v-model="user.password"
        required
      />
      <p> </p>
      <button class="button" type="submit">Sign in</button>
<p> </p>
      <router-link :to="{ name: 'register' }">Need an account?</router-link>
    </form>
  </div>
  
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>
<style>


div#login {
  display: flex;
  justify-content: center;
  
}
form {
  
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 30%;
  background:limegreen;
  border-radius: 5%;
  padding: 20px;
  
 
}

</style>
