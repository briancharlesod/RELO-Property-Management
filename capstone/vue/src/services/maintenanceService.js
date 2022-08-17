import axios from "axios";


export default {

    list(id) {
      return axios.get(`http://localhost:9000/maintenance/all/${id}`);
    },
  
    get(id) {
      return axios.get(`http://localhost:9000/rental/maintenance/${id}`)
    },
  

    delete(id) {
      return axios.delete(`http://localhost:9000/maintenance/${id}`);
    },

    request(form)
    {
      return axios.post(`http://localhost:9000/maintenance/`, form)
    }
  
  }
  