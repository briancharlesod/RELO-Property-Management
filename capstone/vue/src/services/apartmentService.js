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
},

getRents(id){
    return axios.get(`http://localhost:9000/rental/get/rents/${id}`)
},

getDueDate(){
    return axios.get('http://localhost:9000/rent')
},

addRenterToRental(userRental) {
    return axios.post('http://localhost:9000/user/set/rental', userRental)
}
}