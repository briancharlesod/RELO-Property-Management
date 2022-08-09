<template>
  <div id="register" class="text-center content is-medium">
    <form class="form-register" @submit.prevent="register">
      <h3 class="h3 mb-3 font-weight-normal">Create Account</h3>
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
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
      <p>  </p>
      <input
        type="password"
        id="confirmPassword"
        class="form-control input is-rounded"
        placeholder="Confirm Password"
        v-model="user.confirmPassword"
        required
      />
      <p>  </p>
      <button class="btn btn-lg btn-primary btn-block button" type="submit">
        Create Account
      </button>
      <p>  </p>
      <router-link :to="{ name: 'login' }">Have an account?</router-link>
    </form>
  </div>
</template>

<script>
import authService from '../services/AuthService';

export default {
  name: 'register',
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style>
div#register {
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
