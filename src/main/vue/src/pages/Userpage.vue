<template>
  <div class="uk-container">
    <advertise></advertise>
    <div uk-grid>
      <div class="uk-width-1-5">
        <user-left-home></user-left-home>
      </div>
      <div class="uk-width-3-5 uk-padding-remove">
        <user-middle-home v-bind:unique-id="uniqueId"></user-middle-home>
      </div>
      <div class="uk-width-1-5 uk-padding-remove">
        <user-right-home></user-right-home>
      </div>
    </div>
  </div>
</template>
<script>
  import advertise from '../components/Advertise.vue'
  import userLeftHome from '../components/UserLeftHome.vue'
  import userMiddleHome from '../components/UserMiddleHome.vue'
  import userRightHome from '../components/UserRightHome.vue'

  export default {
    components: {
      advertise,
      userLeftHome,
      userMiddleHome,
      userRightHome
    },
    props: ['uniqueId'],
    name: 'Userpage',
    created: function () {
      console.log(this.uniqueId)
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>

