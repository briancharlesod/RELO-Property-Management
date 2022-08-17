<template>
  <body>
    <form class="rent">
      <h1 class="subtitle">
        <img
          id="logo"
          src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTsdKWpDUN34PFuI52xPmmvYDI-okHioswhgA&usqp=CAU"
        />
      </h1>
      <h2 class="subtitle">Rental Payment Form</h2>
      <label>Email:</label>
      <input type="email" required v-model="email" />
      <label>Name:</label>
      <input type="name" required v-model="name" />
      <label>Address:</label>
      <input type="address" required v-model="address" />
      <p>Email: {{ email }}</p>
      <p>Name: {{ name }}</p>
      <p>Address: {{ address }}</p>
      <p class="control">
        <input class="input" type="text" placeholder="Amount of money" />
      </p>
      <div class="select">
        <select>
          <option>Select Payment</option>
          <option>Cash</option>
          <option>Check</option>
          <option>Money Order</option>
        </select>
        <div class="field has-addons">
          <p class="control">
            <span class="select"> </span>
          </p>
        </div>
        <input date="Date" type="date" />
        <p></p>
      </div>
      <div class="field is-grouped">
        <button
          class="button is-small"
          type="submit"
          style="color: rgb(105, 15, 105); margin-top: 5px"
        >
          Submit
        </button>
        <button
          class="button is-small"
          v-on:click.prevent="resetForm"
          style="color: rgb(105, 15, 105); margin-top: 5px"
          type="reset"
        >
          Cancel
        </button>
      </div>
    </form>
  </body>
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
.rent {
  max-width: 420px;
  margin: 30px auto;
  background: #00d1b2;
  text-align: left;
  padding: 40px;
  border-radius: 10px;
}

h1 {
  text-align: center;
}
h2 {
  text-align: center;
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