import axios from 'axios';

export default {

  login(user) {
    return axios.post('/login', user)
  },

  register(user) {
    return axios.post('/register', user)
  },

  reset(user, username) {
    return axios.post(`user/retrieve/password/${username}/`, user)
  },

  retrieve(user, possibleQuestions)
  {
    return axios.post(`user/retrieval/question/${user}`, possibleQuestions)
  },

  retrieveQ2(user, possibleQuestions)
  {
    return axios.post(`user/retrieval/questionTwo/${user}`, possibleQuestions)
  },

  retrieveOne(username, user)
  {
    return axios.post(`user/retrieval/answer/${username}/${user}`)
  },

  retrieveTwo(username, user)
  {
    return axios.post(`user/retrieval/answerTwo/${username}/${user}`)
  }
}
