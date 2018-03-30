<template>
  <div class="uk-padding uk-padding-remove-top">
    <div class="uk-text-center">
      <span class="uk-label uk-label-success">ta的博客</span>
    </div>
    <ul class="uk-list uk-list-divider">
      <li v-for="blog in userBlogList">
        <router-link v-bind:to="'/blog/' + blog.uniqueId"><span>{{blog.title}}</span></router-link>
        <span class="uk-align-right uk-text-small uk-text-muted uk-margin-remove">
          （{{blog.commentNum}}／{{blog.viewNum}}）
        </span>
        <span class="uk-align-right uk-text-small uk-text-muted uk-margin-right">
          {{blog.createTime | formatDate('yyyy-MM-dd hh:mm')}}
        </span>
      </li>
    </ul>
  </div>

</template>

<script>
  import {mapActions, mapState} from 'vuex'

  export default {
    name: 'UserBlog',
    data() {
      return {}
    },
    computed: {
      ...mapState({
        userBlogList: state => state.blog.userBlogList
      })
    },
    created: function () {
      this.fetchUserBlogListAction({uniqueId: this.$route.params.uniqueId, pageParam: {page: 0, size: 10}})
    },
    methods: {
      ...mapActions([
        'fetchUserBlogListAction'
      ])
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
