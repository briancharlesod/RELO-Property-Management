import axios from "axios";
const options = {
  method: 'GET',
  url: 'https://mashvisor-api.p.rapidapi.com/airbnb-property/newly-listed',
  params: { state: 'PA', city: 'Pittsburgh', page: '1' },
  headers: {
    'X-RapidAPI-Key': '8541321054msh76f317c3cac5b10p15809ajsn731307344be9',
    'X-RapidAPI-Host': 'mashvisor-api.p.rapidapi.com'
  }
};
export default {
  getHouses() {
    return axios.request(options)
  }
}