<template>
  <div class="uk-padding uk-padding-remove-top">
    <div class="uk-text-center">
      <span class="uk-label uk-label-success">ta的博客</span>
    </div>
    <div v-if="userBlogList!=null&&userBlogList.length>0">
      <ul class="uk-list uk-list-divider">
        <li v-for="blog in userBlogList">
          <router-link v-bind:to="'/user/'+blog.userId+'/blog/' + blog.uniqueId"><span>{{blog.title}}</span>
          </router-link>
          <span class="uk-align-right uk-text-small uk-text-muted uk-margin-remove">
          （{{blog.commentNum}}／{{blog.viewNum}}）
        </span>
          <span class="uk-align-right uk-text-small uk-text-muted uk-margin-right">
          {{blog.createTime | formatDate('yyyy-MM-dd hh:mm')}}
        </span>
        </li>
      </ul>
    </div>
    <blank-icon v-else></blank-icon>
    <pageable v-bind:fetch-data-func="fetchUserBlogList" size="10"></pageable>
  </div>

</template>

<script>
  import {mapActions, mapState} from 'vuex'
  import Pageable from "./Pageable.vue";
  import BlankIcon from "./BlankIcon.vue";

  export default {
    components: {
      BlankIcon,
      Pageable
    },
    name: 'UserBlog',
    data() {
      return {}
    },
    computed: {
      ...mapState({
        userBlogList: state => state.blog.userBlogList
      })
    },
    methods: {
      ...mapActions([
        'fetchUserBlogListAction'
      ]),
      fetchUserBlogList: function (pageParam) {
        return this.fetchUserBlogListAction({uniqueId: this.$route.params.uniqueId, pageParam: pageParam})
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
