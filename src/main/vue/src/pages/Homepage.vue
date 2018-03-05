<template>
  <div class="uk-container">
    <headNav></headNav>
    <advertise></advertise>
    <div uk-grid>
      <div class="uk-width-2-3">
        <left-home></left-home>
      </div>
      <div class="uk-width-1-3">

      </div>
    </div>
  </div>
</template>
<script>
  import headNav from '../components/Head.vue'
  import advertise from '../components/Advertise.vue'
  import leftHome from '../components/LeftHome.vue'

  export default {
    components: {
      leftHome,
      headNav,
      advertise
    },
    name: 'Homepage'
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
