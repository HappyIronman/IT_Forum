<template>
  <div class="uk-container">
    <advertise></advertise>
    <div uk-grid class="uk-margin-left">
      <!--<div class="uk-width-1-5">-->
        <!--<left-home></left-home>-->
      <!--</div>-->
      <div class="uk-width-4-5 uk-padding-remove">
        <middle-home></middle-home>
      </div>
      <div class="uk-width-expand uk-padding-remove">
        <right-home></right-home>
      </div>
    </div>
  </div>
</template>
<script>
  import advertise from '../components/Advertise.vue'
  import leftHome from '../components/LeftHome.vue'
  import middleHome from '../components/MiddleHome.vue'
  import rightHome from '../components/RightHome.vue'

  export default {
    components: {
      advertise,
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
