<template>
  <div class="uk-padding uk-padding-remove-top">
    <div class="uk-text-center">
      <span class="uk-label uk-label-success">我的博客</span>
    </div>
    <ul class="uk-list uk-list-divider">
      <li v-for="blog in myBlogList">
        <router-link v-bind:to="'/my_blog/' + blog.uniqueId"><span>{{blog.title}}</span></router-link>
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
    name: 'MyBlog',
    data() {
      return {}
    },
    computed: {
      ...mapState({
        myBlogList: state => state.blog.myBlogList
      })
    },
    created: function () {
      this.fetchMyBlogListAction({page: 0, size: 10})
    },
    methods: {
      ...mapActions([
        'fetchMyBlogListAction'
      ])
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
