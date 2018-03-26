<template>
  <div class="uk-container">
    <p>{{blogDetail.title}}</p>
    <div>
      <div v-html="blogDetail.content"></div>
      <hr class="uk-divider-icon uk-margin-small">
      <div v-show="blogDetail.share">
        <p>{{blogDetail.originTitle}}</p>
        <div v-html="blogDetail.originContent"></div>
      </div>
    </div>
  </div>

</template>

<script>
  import {mapActions, mapState} from 'vuex'

  export default {
    name: 'BlogDetail',
    data() {
      return {}
    },
    computed: {
      ...mapState({
        blogDetail: state => state.blog.blogDetail
      })
    },
    created: function () {
      this.fetchBlogDetailAction(this.$route.params.uniqueId)
    },
    methods: {
      ...mapActions([
        'fetchBlogDetailAction'
      ])
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
