<template>
  <div id="app">
    <headNav></headNav>
    <advertise></advertise>
    <router-view/>
  </div>
</template>

<script>
  import headNav from './components/Head.vue'
  import advertise from './components/Advertise.vue'

  export default {
    components: {
      headNav,
      advertise
    },
    name: 'App'
  }
</script>

<style>

</style>
