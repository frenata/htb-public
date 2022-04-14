<template>
  <div id="app">
    <navbar/>
    <home/>
    <footer class="gradient">
      <div class="container-fluid text-center">
        <span>Made by
          <a href="https://horizontall.htb">Horizontall.htb</a></span
        >
      </div>
    </footer>
  </div>
</template>

<script>
import axios from 'axios'
import Navbar from './components/Navbar.vue'
import Home from './components/Home.vue'
export default {
  name: 'App',
  components: {
    Navbar,
    Home
  },
  data(){
    return {
      reviews:[],
    }
  },
  methods:{
    getReviews(){
      axios.get('http://api-prod.horizontall.htb/reviews')
      .then(response => this.reviews = response.data)
    }
  }
}
</script>

<style>
html,
body {
  height: 100%;
  font-size: 1rem;
  font-family: "Montserrat", sans-serif;
}
</style>
