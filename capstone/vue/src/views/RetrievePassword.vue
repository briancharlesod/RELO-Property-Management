<template>
  <div id="login" class="text-center">
    <form class="form-signin" @submit.prevent="login">
      <h1 class="h3 mb-3 font-weight-normal has-text-info is-size-4 m-6">
        Password Retrieval
      </h1>
      <div class="alert alert-danger" role="alert" v-if="invalidCredentials">
        Invalid username and password!
      </div>
      <form class="box" v-if="q1 == '' && q2 == ''">
        <div class="field">
          <label class="label form-control">Username</label>
          <div class="control">
            <input class="input" type="text" v-model="user.username" required />
          </div>
        </div>

        <div>
          <button type="button" class="button is-primary btn" @click="retrieve">
            Get Challenge Questions
          </button>
        </div>
      </form>
      <form class="box" v-if="q1 != ''">
        <div class="field">
          <label class="label form-control">{{ q1 }}</label>
          <div class="control">
            <input class="input" type="text" v-model="user.answer1" required />
          </div>
        </div>

        <div>
          <button
            type="button"
            class="button is-primary btn"
            @click="retrieveOne"
          >
            Submit Answer
          </button>
        </div>
      </form>

      <form class="box" v-if="q2 != ''">
        <div class="field">
          <label class="label form-control">{{ q2 }}</label>
          <div class="control">
            <input class="input" type="text" v-model="user.answer2" required />
          </div>
        </div>

        <div>
          <button
            type="button"
            class="button is-primary btn"
            @click="retrieveTwo"
          >
            Submit Answer
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
        username: "",
        possibleQuestions: this.$store.state.retrievalQuestions,
        answer1: "",
        answer2: "",
      },

      role: "",
      q1: "",
      q2: "",
      invalidCredentials: false,
    };
  },
  methods: {
    retrieveOne() {
      AuthService.retrieveOne(this.user.username, this.user.answer1)
        .then((resp) => {
          if (resp.status == 202) {
            this.$store.commit("SET_USERNAME", this.user.username);
            this.$store.commit("SET_HASH", this.user.answer1);
            this.$router.push({ name: "reset" });
          }
        })
        .catch((error) => {
          if (error.status == 404) {
              console.log("error");
          }
            alert("Answers did not match, try again");
            AuthService.retrieveQ2(
              this.user.username,
              this.user.possibleQuestions
            ).then((res) => {
              if (res.status == 202) {
                this.q1 = "";
                console.log(res.data);
                this.q2 = res.data;
              }
            });
          
        });
    },
    retrieveTwo() {
      AuthService.retrieveTwo(this.user.username, this.user.answer2).then((resp) => {
        if (resp.status == 202) {
          this.$store.commit("SET_USERNAME", this.user.username);
          this.$store.commit("SET_HASH", this.user.answer2);

          this.$router.push({ name: "reset" });
        }
      }).catch( () =>{       
          alert("Could not change password due to wrong answers, try again");
          this.$router.push({ name: "login" });
      });
    },
    retrieve() {
      AuthService.retrieve(
        this.user.username,
        this.user.possibleQuestions
      ).then((resp) => {
        if (resp.status == 202) {
          this.q1 = resp.data;
        } else {
          alert("Username could not be found, try again");
        }
      });
    },
  },
};
</script>

<style>
</style>