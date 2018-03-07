<template>
  <div class="uk-container">
    <div uk-grid>
      <div class="uk-width-1-5">
        <left-home></left-home>
      </div>
      <div class="uk-width-3-5">
        <middle-home></middle-home>
      </div>
      <div class="uk-width-1-5 uk-padding-small">
        <right-home></right-home>
      </div>
    </div>
  </div>
</template>
<script>
  import leftHome from '../components/LeftHome.vue'
  import middleHome from '../components/MiddleHome.vue'
  import rightHome from '../components/RightHome.vue'

  export default {
    components: {
      leftHome,
      middleHome,
      rightHome
    },
    name: 'Homepage'
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
