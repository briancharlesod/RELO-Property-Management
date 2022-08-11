import axios from "axios";

export default {
addApartment(rental) {
    return axios.post('http://localhost:9000/rental', rental)
}

}