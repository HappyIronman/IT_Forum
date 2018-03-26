<template>
  <div id="app">
    <headNav></headNav>
    <router-view/>
  </div>
</template>

<script>
  import headNav from './components/Head.vue'

  export default {
    components: {
      headNav,
    },
    name: 'App'
  }
</script>

<style>

</style>
