<template>
  <div class="container">
      <div class= "row-justify content-center">
        <div class = "col-md-8">
          <input type ="text"
          v-model.trim="search"
          placeholder="Search Properties..."
          @keyup="getHouses"
          />
          <br/>
          <ul>
            <li v-form="house in houses" : key="house.id">
              {{house.houses}}
              </li>
          </ul>
        </div>
        </div>
        </div>
</template>
<script>
export default{
 name:"searchHouses",
 data(){
   return{
     houses:[],
     search:"",
   };
 },
 mounted(){
   console.log("Component mounted.");
 },
 methods:{
   getHouses(){
     fetch("https://mashvisor-api.p.rapidapi.com/airbnb-property/newly-listed")
     .then(response=> response.json())
     .then(res=>{
       if(this.search){
         this.people = res.results.filter(houses => houses.house.toLowerCase());
       } else {
         this.houses =res.results;
       }
     });
   }
 },
 created(){
   this.getHouses();
 }
};
</script>
