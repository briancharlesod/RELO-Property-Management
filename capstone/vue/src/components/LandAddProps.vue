<template>
<body>
<div id="container">
 
  <div v-show="showAddForm">
    <!--Nav Buttons  -->
    <div id="editButtons">
  <button class="button is-primary" v-on:click="getRenters(); showAddForm = false; showAssignRenter = true;">Assign Renters</button>
<button class="button is-primary" v-on:click="getMaintenanceRequests(); showAddForm = false; showMaintenance = true">View Maintenance Requests</button>
<button class="button is-primary">View Rents</button>
<button class="button is-primary" v-if="newProperty.isRented" @click="putOnMarket()">Put On The Market</button>
<button class="button is-primary" v-else @click="takeOffMarket()" >Take Off The Market</button>
<button class="button is-primary">Delete Property</button>
<button class="button is-primary" v-on:click="clearForm(); showAddForm = false; showLandlordApts = true; showAssignRenter = false ">Back</button>
</div>

<!--Update or Add Form -->
  <form id="manageProps" v-on:submit.prevent="submitRental(); landlordHome">

    <div id = "container">
    <div class="buttons">
  
    </div>
  <div class="tile is-ancestor is-vertical m-6">
    <div class="tile is-ancestor is-horizontal">
  <div class="tile is-4 is-vertical is-parent">
    <div class="tile is-child box">
      <p class="title">Property Info</p>
      <p class="is-size-4">Address: <input class="is-size-4" type="text" v-model="newProperty.address" /></p>
      <p class="is-size-4">Rent: <input class="is-size-4" type="text" v-model="newProperty.price"/></p>
    </div>
    <div class="tile is-child box">
      <p class="title">Room Info</p>
      <p class="is-size-4">Bedrooms: <input  type="number" class="is-size-4" v-model="newProperty.bedroom"/></p>
      <p class="is-size-4">Bathrooms: <input type="number" class="is-size-4" v-model="newProperty.bathroom"/></p>
    </div>
  </div>
  <div class="tile is-parent is-vertical">
    <div class="tile is-child box">
      <p class="title">Description</p>
      <p>
          <textarea class="textarea is-large is-size-6 " type="text" v-model="newProperty.description" />
      </p>
     </div>
  </div>
    </div>
    <div class="tile is-child box">
      <p class="title">Photos</p>
      <img v-bind:src="newProperty.imgURL" alt="Placeholder image" class="is-inline-block" />
      <label for="imageInput" class=" is-size-4">Image URL:</label>
      <input id="imageInput" class="is-size-4" type="text" v-model="newProperty.imgURL" />

     </div>
  </div>
</div>

<input id="saveButton" type="submit" class="button is-primary" value="SAVE" />
  </form>
  </div>


  <!--List Apartments by landlord -->
<div id="listApartments" v-show="showLandlordApts">
<div id="houseCard" v-for="apartment in apartments" v-bind:key="apartment.id" class="card" >
      <p>{{apartment.address}}</p>
      <img id="cardImg" v-bind:src="apartment.imgURL" alt="Placeholder image" class="is-inline-block" />
      <p class="content">Rent - ${{apartment.price}}.00</p>
      <button class="button" v-on:click="editForm(apartment); showAddForm = true; showLandlordApts = false">EDIT</button>
    </div>
<div id="houseCard" class="card" v-on:click="showAddForm = true; showLandlordApts = false">
  <p class="is-size-1">+</p>
<p class="is-size-4">Add New Property</p>
</div>
</div>

<!--Assign Renters -->
<div id="assignRenter" v-show="showAssignRenter">&nbsp;


<div id="renterBox" v-for="user in users" v-bind:key="user.user_id" class="box has-text-weight-bold">
  {{ user.username}}          {{user.last_paid}}
</div>&nbsp;
<button v-show="!addRenter" class="button is-primary" v-on:click="addRenter = true;">Add new Renter +</button>

<form v-on:submit.prevent="addRenterToRental(); addRenter = false" v-show="addRenter">
<input class="input" v-model="renterToAdd.userID" type="text"  />
<input class="button is-primary" type="submit" value="Submit" />

</form>
<button class="button is-primary" v-on:click="showAssignRenter = false; showAddForm = true; renters = []; addRenter = false">Cancel</button>
</div>




<!--Maintenance Form -->
<div v-show="showMaintenance">
 <table class="table">
    <thead>
      <tr>
       
        <th>Issue</th>
       <!-- <th>Author</th>-->
        <!--<th>Actions</th>-->
      </tr>
    </thead>
    <tbody>
      <tr v-for="doc in requests" :key="doc.id">
        <!--<td class="docs-icon">
          <img src="../assets/icons8-google-docs-48.png" />
        </td>-->
        <td class="name">{{ doc.maintenanceRequest }}</td>
        <!--<td>
          <span class="ownedby">{{ doc.completed }}</span>
        </td>-->
        <td>{{ doc.completion_date }}</td>
        <td>
          <button v-on:click="viewRequest(doc.id)">View</button>&nbsp;
          <button v-on:click="deleteDocument(doc.id)">Delete</button>
          
        </td>
      </tr>
    </tbody>
  </table>
  <button class="back" v-on:click="showMaintenance = false; showAddForm = true">Back</button>
</div>

</div>
</body>
</template>

<script>
import ApartmentService from '../services/apartmentService'
import maintenanceService from "../services/maintenanceService";



export default {
data() {
    return {
        test: true,
        falseTest: false,
        newProperty: {
                rentalID: 0,
                address: "",
                bedroom: "",
                bathroom: "",
                typeOfResidence: "house",
                isRented: false,
                price: "",
                description: '',
                imgURL: 'placeholder',
                landlord_id: this.$store.state.user.id
            },
        showAddForm: false,
        showLandlordApts: true,
        showAssignRenter: false,
        apartments: [],
        renters: [],
        renterToAdd: {
          userID: '',
          rentalID: ''
        },
        addRenter: false,
         response: "",
      requests: [],
      users: [],
      showMaintenance: false
    }
},

created() {
      this.getApartments();
      this.clearForm();
      this.showAddForm = false;
      this.showLandlordApts = true;
      this.showAssignRenter = false;
      console.log(this.$store.state.user);
      
       maintenanceService.list(this.$store.state.user.id).then((response) => {
            console.log(response)
            if(response.status == 200)
            {
              this.response = response;
              this.$store.commit("SET_MAINTENANCE_REQUESTS", response.data);
            }
      });
    
    },



methods: {
    landlordHome()
    {
        this.$router.push("/");
    },

    putOnMarket() {
      ApartmentService.putOnMarket(this.newProperty.rentalID).then(response => {
        if (response.status == 200) {
          this.newProperty.isRented = false;
          this.getApartments()
        }
      })
      
    },

    takeOffMarket() {
      ApartmentService.putOffMarket(this.newProperty.rentalID).then(response => {
        if (response.status == 200) {
          this.newProperty.isRented = true;
          this.getApartments()
        }
      })
    },
   
    editForm(rental) {
      this.newProperty.rentalID = rental.rentalID;
      this.newProperty.bathroom = rental.bathroom;
      this.newProperty.price = rental.price;
      this.newProperty.bedroom = rental.bedroom;
      this.newProperty.address = rental.address;
      this.newProperty.isRented = rental.rented;
      //this.newProperty.typeOfResidence = rental.typeOfResidence;
      this.newProperty.description = rental.description;
      this.newProperty.imgURL = rental.imgURL;
    },

    submitRental() {
      if (this.newProperty.rentalID == 0) {
        this.newProperty.rentalID = '';
        this.addRental();
        this.showAddForm = false;
        this.showLandlordApts = true;
      } else {
        this.updateForm();
        this.showAddForm = false;
        this.showLandlordApts = true;
      }
      
    },

    addRenterToRental() {
      this.renterToAdd.rentalID = parseInt(this.newProperty.rentalID);
      

        try {
          ApartmentService.addRenterToRental(this.renterToAdd, this.newProperty.rentalID).then(response => {
            if (response.status == 201) {
              this.renterToAdd.rentalID = '';
              this.renterToAdd.userID = '';
              this.getRenters();
            }
          })
        } catch (e) {
          console.log(e)
        }


    },

    updateForm() {
      try{
        ApartmentService.updateApartment(this.newProperty).then(response => {
        if (response.status == 200) {
          this.clearForm();
          
          this.getApartments();
        }}
        );
      } catch (e) {
        console.log(e)
      }
    },

    getRenters() {
      try {
      ApartmentService.getRenters(this.newProperty.rentalID).then(response => {
        this.users = response.data
      })
      } catch (e) {
        console.log(e)
      }
    },

    clearForm() {
      this.newProperty.bathroom = '';
      this.newProperty.price = '';
      this.newProperty.bedroom = '';
      this.newProperty.address = '';
      this.newProperty.isRented = false;
      //this.newProperty.typeOfResidence = '';
      this.newProperty.description = '';
      this.newProperty.imgURL = 'placeholder',
      this.newProperty.rentalID = 0
    },
    getApartments() {
      try {
      ApartmentService.getApartmentByLandlord(this.$store.state.user.id).then(response => {
        this.apartments = response.data;
      })
      } catch (e) {
        console.log(e);
      }

    },
    addRental() {
      this.newProperty.price = parseFloat(this.newProperty.price)
      this.newProperty.bedroom = parseInt(this.newProperty.bedroom)
      this.newProperty.bathroom = parseInt(this.newProperty.bathroom)
      try {
      ApartmentService.addApartment(this.newProperty).then(response => {
        if (response.status == 201) {
          this.clearForm();
          this.getApartments();
         
        }
      })
      } catch (e) {
        console.log(e)
      }
    },
    viewMaintenanceRequest(id) {
      this.$router.push(`/maintenance/all/${id}`);
    },
    deleteMaintenanceRequest(id) {
      maintenanceService
        .delete(id)
        .then(response => {
          if (response.status === 200) {
            this.getMaintenanceRequest();
          }
        })
        .catch(error => {
          if (error.response.status === 404) {
            this.$router.push("/404");
          } else {
            console.error(error);
          }
        });
    },
    getMaintenanceRequests() {
      maintenanceService.get(this.newProperty.rentalID).then(response => {
        //this.$store.commit("SET_MAINTENANCE_REQUESTS", response.data);
        this.requests = response.data
      });
    }
  },
  /*created() {
          maintenanceService.list(this.$store.state.user.id).then((response) => {
            console.log(response)
            if(response.status == 200)
            {
              this.response = response;
              this.$store.commit("SET_MAINTENANCE_REQUESTS", response.data);
            }
      });
    this.getMaintenanceRequests();
  },*/
  computed: {
    sortedMaintenanceRequests() {
      return this.$store.state.maintenanceRequests
        .slice()
        .sort(
          (a, b) =>
            new Date(b.lastOpened.replace("th", "")) -
            new Date(a.lastOpened.replace("th", ""))
        );
    }
}
};
</script>

<style>
div#listApartments {
  display: flex;
  flex-wrap: wrap;
  margin-right: 200px;
}

div#addCard {
  width: 300px;
}

div#renterBox {
  margin-top: 25px;
  background: rgb(243, 133, 151);
  width: 500px;
}
.table {
  padding: 100%;
    background: rgb(243, 133, 151);
margin: 0;
}

.view{
  display: flex;
  justify-content: flex-end;
}

img#cardImg {
  height: 200px;
}





</style>