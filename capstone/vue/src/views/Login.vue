<template>
  <div id="login" class="text-center">
    <form class="form-signin" @submit.prevent="login">
      <h1 class="h3 mb-3 font-weight-normal has-text-info is-size-3 m-6">Please Sign In</h1>
      <div
        class="alert alert-danger"
        role="alert"
        v-if="invalidCredentials"
      >Invalid username and password!</div>
      <div
        class="alert alert-success has-text-info is-size-5 m-3"
        role="alert"
        v-if="this.$route.query.registration"
      >Thank you for registering, please sign in.</div>
            <div
        class="alert alert-success has-text-info is-size-5 m-3"
        role="alert"
        v-if="this.$route.query.reset"
      >Password reset, please sign in.</div>
            <form class="box">
        <div class="field">
          <label class="label form-control">Username</label>
          <div class="control">
            <input
              class="input"
              type="text"
              v-model="user.username"
              required
            />
          </div>
        </div>

        <div class="field">
          <label class="label form-control">Password</label>
          <div class="control">
            <input class="input" type="password" placeholder="********" v-model="user.password" required/>
          </div>
        </div>

        <div>
        <button type="button" class="button is-primary btn" @click="login">
          Sign In
        </button>
        <button type="button" class="button is-primary btn" @click="forgot">
          Forgot Password
        </button>
      </div>
      </form>
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
      role : "",
      invalidCredentials: false
    };
  },
  methods: {
    forgot(){
      this.$router.push("/retrievePassword");
    },
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.role = this.$store.state.user.authorities;
            if(this.role[0].name.includes("LANDLORD"))
            {
              this.$router.push("/landlord");

            }
            if(this.role[0].name.includes("EMPLOYEE"))
            {
              this.$router.push("/employee");

            }
            if(this.role[0].name.includes("RENTER"))
            {
              this.$router.push("/renter");

            }
            //this.$router.push("/");
            
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
