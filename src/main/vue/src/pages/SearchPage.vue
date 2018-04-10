<template>
  <div class="uk-container">
    <advertise></advertise>
    <div uk-grid>
      <div class="uk-width-1-5">
        <left-home></left-home>
      </div>
      <div class="uk-width-3-5 uk-padding-remove">
        <div class="uk-container">
          <search-blog-item></search-blog-item>
        </div>
      </div>
      <div class="uk-width-1-5 uk-padding-remove">

      </div>
    </div>
  </div>
</template>
<script>
  import advertise from '../components/Advertise.vue'
  import leftHome from '../components/LeftHome.vue'
  import searchBlogItem from '../components/SearchBlogItem.vue'

  export default {
    components: {
      advertise,
      leftHome,
      searchBlogItem,
    },
    name: 'Homepage'
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
