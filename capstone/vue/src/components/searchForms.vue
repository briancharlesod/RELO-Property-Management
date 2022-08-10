<template>
  <div>
      <nav class="navbar" role="navigation" aria-label="main navigation">
  <div class="navbar-brand">
    <a role="button" class="navbar-burger" aria-label="menu" aria-expanded="false" data-target="navbarBasicExample">
      <span aria-hidden="true"></span>
      <span aria-hidden="true"></span>
      <span aria-hidden="true"></span>
    </a>
  </div>
  <div id="navbarBasicExample" class="navbar-menu">
    <div class="navbar-start">
      <a class="navbar-item">
        Home
      </a>
      <a class="navbar-item">
        Request a Tour
      </a>
      <div class="navbar-item has-dropdown is-hoverable">
        <a class="navbar-link">
          More
        </a>
        <div class="navbar-dropdown">
          <a class="navbar-item">
           Request Maintenance
          </a>
          <a class="navbar-item">
           Pay Rent
          </a>
          <a class="navbar-item">
            Contact Landlord
          </a>
          <hr class="navbar-divider">
          <a class="navbar-item">
            Report an issue
          </a>
        </div>
      </div>
    </div>
    <div class="navbar-end">
      <div class="navbar-item">
        <div class="buttons">
          <a class="button is-primary">
            <strong>Sign up</strong>
          </a>
          <a class="button is-light">
            Log in
          </a>
        </div>
      </div>
    </div>
  </div>
</nav>
<div id="BrowseProperties" class="text-center content is-medium">
    <h1 class="title">Browse Properties</h1>
    <h2 class="subtitle">Take a look at all we have to offer!</h2>
    <div class="control">
      <input class="input" type="text" placeholder="Type City or Zip Code" />
    </div>
    <div class="control">
      <div class="select">
        <select>
          <option>Select Bedrooms</option>
          <option>1</option>
          <option>2</option>
          <option>3+</option>
        </select>
      </div>
      <div class="control">
        <div class="select">
          <select>
            <option>Select Bathrooms</option>
            <option>1</option>
            <option>2</option>
            <option>3+</option>
          </select>
        </div>
      </div>
    </div>
    <select>
      <option>Select Rental Type</option>
      <option>House</option>
      <option>Apartment</option>
    </select>
  </div>
</template>
<style>
div#BrowseProperties {
  display: flex;
  justify-content: center;
}
form {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 30%;
  border-radius: 5%;
  padding: 20px;
}
</style>



