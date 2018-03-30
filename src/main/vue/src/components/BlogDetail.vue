<template>
  <div class="uk-padding">
    <p class="uk-text-center uk-text-lead">{{blogDetail.title}}</p>
    <div>
      <div v-html="blogDetail.content"></div>
      <div v-show="blogDetail.share">
        <hr class="uk-divider-icon uk-margin-small">
        <p class="uk-text-bold">{{blogDetail.originTitle}}</p>
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
      this.fetchBlogDetailAction(this.$route.params.blogUniqueId)
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
