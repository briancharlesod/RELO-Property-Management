import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));

if(currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    token: currentToken || '',
    user: currentUser || {},
    house: [],
    maintenance: [],
    username: "",
    hash: "",
    retrievalQuestions: [
      "What is your mother's maiden name?",
      "What is your favorite color?",
      "What city were you born?",
      "What is your favorite sports team?",
      "What was your high school mascot?",
      "What is the name of your first significant other?",
      "What was your first car?",
      "What is your favorite book?",
      "What was the model of your first car?",
      "What is the name of this boot camp?",
    ]
  },
  mutations: {
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user',JSON.stringify(user));
    },
    SET_USERNAME(state, user) {
      state.username = user;
    },
    SET_MAINTENANCE_REQUESTS(state, maintenance) {
      state.maintenance = maintenance;
    },
    SET_HASH(state, user) {
      state.hash = user;
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      state.token = '';
      state.user = {};
      axios.defaults.headers.common = {};
    },
    SET_HOUSES(state, house)
    {
      state.house = house;
    }
  }
})
