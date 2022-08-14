<template>
  <div id="register" class="text-center">
    <form class="form-register" @submit.prevent="register">
      <h1 class="h3 mb-3 font-weight-normal has-text-info is-size-3 m-6">
        Reset Password
      </h1>
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <form class="box">
        <div class="field">
          <label class="label form-control">Password</label>
          <div class="control">
            <input
              class="input"
              type="password"
              placeholder="********"
              v-model="user.password"
              required
            />
          </div>
        </div>

        <div class="field">
          <label class="label form-control">Confirm Password</label>
          <div class="control">
            <input
              class="input"
              type="password"
              placeholder="********"
              required
              v-model="user.confirmPassword"
            />
          </div>
        </div>

        <div>
          <button type="button" class="button is-primary btn" @click="reset">
            Reset Password
          </button>
        </div>
      </form>
    </form>
  </div>
</template>

<script>
import AuthService from "../services/AuthService";
export default {
  data() {
    return {
      user: {
        username: this.$store.state.username,
        password: "",
        confirmPassword: "",
      },
      passwordRetrieval: {
          password:"",
          hash: this.$store.state.hash
      },
      registrationErrors: false,
      registrationErrorMsg: "There were problems resetting the password.",
    };
  },
  methods: {
    reset() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = "Password & Confirm Password do not match.";
      } else {
          this.passwordRetrieval.password = this.user.password;
        AuthService.reset(this.passwordRetrieval, this.user.username).then((res) => {
          if (res.status == 201) {
            this.$router.push({
              path: "/login",
              query: { reset: "success" },
            });
          }
        }).catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = "Bad Request: Validation Errors";
            }
          });
      }
    },
  },
  computed: {
    quesitonArray() {
      return this.retrievalQuestions;
    },
  },
};
</script>

<style>
</style>