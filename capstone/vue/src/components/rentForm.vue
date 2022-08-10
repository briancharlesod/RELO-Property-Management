

<template>
  <form>
<h1 class="title">RELO</h1>
<h2 class="subtitle">Maintenance Request Form</h2>
    <label>Email:</label>
    <input type="email" required v-model="email" />
    <label>Name:</label>
    <input type="name" required v-model="name" />
    <label>Address:</label>
    <input type="address" required v-model="address" />
    <p>Email: {{ email }}</p>
    <p>Name: {{ name }}</p>
    <p>Address: {{ address }}</p>
<textarea class="textarea" placeholder="What needs fixed?" rows="10"></textarea>
<input date = "Date" type = "date" />
<p></p>
 <button class="button">Submit</button>
 <button class="button">Cancel</button>
  </form>
</template>
<script>
export default {
  data() {
    return {
      email: "",
      name: "",
      address: "",
    };
  },
};
</script>
<style>
textarea{
  height: 176px;
  width: 415px;
}
h1{
  text-align: center;
}
h2{
  text-align: center;
}
form {
  max-width: 420px;
  margin: 30px auto;
  background: hsl(48, 100%, 29%);
  text-align: left;
  padding: 40px;
  border-radius: 10px;
}
label {
  color: whitesmoke;
  display: inline-block;
  margin: 25px 0 15px;
  font-size: 0.6em;
  text-transform: uppercase;
  letter-spacing: 1px;
  font-weight: bold;
}
input {
  display: block;
  width: 100%;
  box-sizing: border-box;
  border: none;
  border-bottom: 1px solid#ddd;
  color: #555;
}
</style>





