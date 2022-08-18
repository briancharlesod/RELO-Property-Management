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

getAvailableProps(){
    return axios.get('http://localhost:9000/rental')
},

addRenterToRental(userRental) {
    return axios.post('http://localhost:9000/user/set/rental', userRental)
},

getRenters(rental_id) {
    return axios.get(`http://localhost:9000/rental/renters/${rental_id}`)
},

viewRent(id)
{
    return axios.get(`http://localhost:9000/rent/${id}`)
},

payRent(form)
{
    return axios.put(`http://localhost:9000/rental/pay`, form)
},
getApartmentsOwned(userId) {
    return axios.get(`http://localhost:9000/renter/owned/${userId}`)
},

putOnMarket(rental_id) {
    return axios.put(`http://localhost:9000/rental/onmarket/${rental_id}`)
},

putOffMarket(rental_id) {
    return axios.put(`http://localhost:9000/rental/offmarket/${rental_id}`)
},

deleteRental(rental_id) {
    return axios.delete(`http://localhost:9000/rental/${rental_id}`)
},

deleteRenterFromProperty(user_id, rental_id) {
    return axios.delete(`http://localhost:9000/rental/del/${user_id}/${rental_id}`)
}


}