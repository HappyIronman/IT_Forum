<template>
  <div>
    <self-page-nav index="2"></self-page-nav>
    啥也没有呀
  </div>
</template>

<script>
  import SelfPageNav from "./SelfPageNav.vue";

  export default {
    components: {SelfPageNav},
    name: 'MyQuestion',
    data() {
      return {
        msg: 'Welcome to Ironman\'s world haha !'
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
