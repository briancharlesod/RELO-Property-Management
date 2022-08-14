import axios from "axios";

export default {
addApartment(rental) {
    return axios.post('http://localhost:9000/rental', rental)
},

getApartmentByLandlord(id) {
 return axios.get(`http://localhost:9000/rental/landlord/${id}`)
},

updateApartment(rental) {
    return axios.put('http://localhost:9000/rental', rental)
}

}