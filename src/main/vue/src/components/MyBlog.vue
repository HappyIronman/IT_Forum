<template>
  <div>
    <self-page-nav index="1"></self-page-nav>

    <div class="uk-padding uk-padding-remove-top">
      <div class="uk-text-center">
        <span class="uk-label uk-label-success">我的博客</span>
      </div>
      <ul class="uk-list uk-list-divider">
        <li v-for="blog in myBlogList">
          <router-link v-bind:to="'/self/my_blog/' + blog.uniqueId"><span>{{blog.title}}</span></router-link>
          <span class="uk-align-right uk-text-small uk-text-muted uk-margin-remove">
          （{{blog.commentNum}}／{{blog.viewNum}}）
        </span>
          <span class="uk-align-right uk-text-small uk-text-muted uk-margin-right">
          {{blog.createTime | formatDate('yyyy-MM-dd hh:mm')}}
        </span>
        </li>
      </ul>
      <pageable v-bind:fetch-data-func="fetchMyBlogListAction" size=10></pageable>
    </div>
  </div>
</template>

<script>
  import {mapActions, mapState} from 'vuex'
  import SelfPageNav from "./SelfPageNav";
  import Pageable from "./Pageable.vue";

  export default {
    components: {
      Pageable,
      SelfPageNav
    },
    name: 'MyBlog',
    data() {
      return {}
    },
    computed: {
      ...mapState({
        myBlogList: state => state.blog.myBlogList
      })
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
