<template>
  <div class="uk-padding-small">
    <ul class="uk-list uk-list-divider">
      <li v-for="item in aboutmeList">

        <div v-if="item.type===0" class="uk-card uk-card-small uk-card-default uk-card-body">
          <div>
            <router-link v-bind:to="'/user/'+item.userId">{{item.username}}</router-link>
            <span v-if="item.like">赞</span>
            <span v-if="!item.like">踩</span>
            <span>了您的</span>
            <span v-if="item.articleType===0">
              评论: {{item.articleContent}}
            </span>
            <span v-if="item.articleType===1">
              动态: {{item.articleContent}}
            </span>
            <span v-if="item.articleType===2">
              博客:
              <router-link v-bind:to="'/my_blog/'+item.articleId">{{item.articleTitle}}</router-link>
            </span>
            <span v-if="item.articleType===3">
              提问: {{item.articleTitle}}
            </span>
          </div>
        </div>

        <div v-if="item.type===1" class="uk-card uk-card-small uk-card-default uk-card-body">
          <div>
            <router-link v-bind:to="'/user/'+item.userId">{{item.username}}</router-link>
            <span>浏览了您的</span>
            <span v-if="item.articleType===0">
              评论: {{item.articleContent}}
            </span>
            <span v-if="item.articleType===1">
              动态: {{item.articleContent}}
            </span>
            <span v-if="item.articleType===2">
              博客:
              <router-link v-bind:to="'/my_blog/'+item.articleId">{{item.articleTitle}}</router-link>
            </span>
            <span v-if="item.articleType===3">
              提问: {{item.articleTitle}}
            </span>
          </div>
        </div>


        <div v-if="item.type===3" class="uk-card uk-card-small uk-card-default uk-card-body">
          <div>
            <router-link v-bind:to="'/user/'+item.userId">{{item.username}}</router-link>
            关注了您
          </div>
        </div>
      </li>
      <article class="uk-comment uk-comment-primary uk-card-default">
        <header class="uk-comment-header uk-grid-medium uk-flex-middle" uk-grid>
          <div class="uk-width-auto">
            <img class="uk-comment-avatar" src="../../static/img/ironman.jpeg" style="width: 80px;height: 80px"
                 alt="">
          </div>
          <div class="uk-width-expand">
            <h4 class="uk-comment-title uk-margin-remove"><a class="uk-link-reset" href="#">Author</a></h4>
            <ul class="uk-comment-meta uk-subnav uk-subnav-divider uk-margin-remove-top uk-padding-remove-left">
              <li class="uk-padding-remove-left"><a href="#">12 days ago</a></li>
              <li><a href="#">Reply</a></li>
            </ul>
          </div>
        </header>
        <div class="uk-comment-body">
          <p>Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore
            et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum.
            Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.</p>
        </div>
      </article>
    </ul>

  </div>
</template>

<script>
  import {mapActions, mapState} from 'vuex'

  export default {
    name: 'Aboutme',
    data() {
      return {
        msg: 'Welcome to Ironman\'s world haha !'
      }
    },
    computed: {
      ...mapState({
        aboutmeList: state => state.user.aboutmeList
      })
    },
    created: function () {
      this.fetchAboutmeListAction({page: 0, size: 10})
    },
    methods: {
      ...mapActions([
        'fetchAboutmeListAction'
      ])
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
