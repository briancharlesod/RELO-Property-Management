<template>
  <div id="register" class="text-center">
    <form class="form-register" @submit.prevent="register">
      <h1 class="h3 mb-3 font-weight-normal has-text-info is-size-3 m-6">
        Create Account
      </h1>
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <!--
      Username: 
      <label for="username" class="sr-only">Username</label>
      <input
        type="text"
        id="username"
        class="form-control"
        placeholder="Username"
        v-model="user.username"
        required
        autofocus
      />
      <br>
      Password: 
      <label for="password" class="sr-only">Password</label>
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      />
      <br> 
      Confirm Password: 
      <input
        type="password"
        id="confirmPassword"
        class="form-control"
        placeholder="Confirm Password"
        v-model="user.confirmPassword"
        required
      />-->
      <form class="box">
        <div class="field">
          <label class="label form-control">Username</label>
          <div class="control">
            <input class="input" type="text" v-model="user.username" required />
          </div>
        </div>

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

      <div class = "field">
        <label class="label form-control">Challenge Question One </label>
        <div class="select" v-if="user.questionOne == ''">
          <select v-model="user.questionOne" @change="filterRetrieval(user.questionOne)"> 
            <option v-for="question in quesitonArray" v-bind:key="question">{{question}}</option>
          </select>
        </div>
          <div v-else @click="pushQuestion1(user.questionOne)">
            <label class="label form-control"> {{user.questionOne}}</label>
        </div>
      </div>

        <div class="field">
          <label class="label form-control">Challenge Answer One</label>
          <div class="control">
            <input
              class="input"
              type="text"
              required
              v-model="user.answerOne"
            />
          </div>
        </div>

      <div class = "field">
        <label class="label form-control">Challenge Question Two </label>
        <div class="select" v-if="user.questionTwo == ''">
          <select v-model="user.questionTwo" @change="filterRetrieval(user.questionTwo)"> 
            <option v-for="question in quesitonArray" v-bind:key="question" >{{question}}</option>
          </select>
        </div>
        <div v-else @click="pushQuestion2(user.questionTwo)">
          <label class="label form-control"> {{user.questionTwo}}</label>
        </div>
      </div>

        <div class="field">
          <label class="label form-control">Challenge Answer Two</label>
          <div class="control">
            <input
              class="input"
              type="text"
              required
              v-model="user.answerTwo"
            />
          </div>
        </div>

        <div>
          <button
            type="button"
            class="button is-primary btn"
            @click="register('renter')"
          >
            Register as a renter
          </button>
        </div>
        <div>
          <button
            type="button"
            class="button is-primary btn"
            @click="register('landlord')"
          >
            Register as a landlord
          </button>
        </div>
        <div>
          <button
            type="button"
            class="button is-primary btn"
            @click="register('employee')"
          >
            Register as an employee
          </button>
        </div>
      </form>

      <router-link :to="{ name: 'login' }">Have an account?</router-link>
    </form>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "register",
  data() {
    return {
      user: {
        username: "",
        password: "",
        confirmPassword: "",
        role: "",
        questionOne:"",
        questionTwo: "",
        answerOne: "",
        answerTwo: "",
      },
      retrievalQuestions: this.$store.state.retrievalQuestions,
      registrationErrors: false,
      registrationErrorMsg: "There were problems registering this user.",
    };
  },
  computed: {
    quesitonArray()
    {
      return this.retrievalQuestions
    }
  },
  methods: {
    pushQuestion1(x){
      if(!this.retrievalQuestions.includes(this.questionOne))
      {
        if(x != this.questionTwo){
          console.log(x)
          this.retrievalQuestions.push(x);
        }
      }
      this.user.questionOne = "";
    },
    pushQuestion2(x){
      if(!this.retrievalQuestions.includes(this.questionTwo))
      {
        if(x != this.questionOne){
          console.log(x)
          this.retrievalQuestions.push(x);
        }
      }
      this.user.questionTwo = "";
    },
    filterRetrieval(x) {
      this.retrievalQuestions = this.retrievalQuestions.filter(response =>  response != x);
    },
    register(x) {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = "Password & Confirm Password do not match.";
      } else {
        this.user.role = x;
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: "/login",
                query: { registration: "success" },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = "Bad Request: Validation Errors";
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = "There were problems registering this user.";
    },
  },
};
</script>

<style>
.register {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 100px;
}

.is-primary {
  margin: 5px;
}
</style>
