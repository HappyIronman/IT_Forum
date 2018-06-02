<template>
  <div class="uk-padding-small">
    <div v-if="aboutmeList!=null&&aboutmeList.length>0">
      <ul class="uk-list uk-list-divider">
        <li v-for="item in aboutmeList">

          <div v-if="item.logType===0" class="uk-card uk-card-small uk-card-default uk-card-body">
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
              <router-link v-bind:to="'/self/my_blog/'+item.articleId">{{item.articleTitle}}</router-link>
            </span>
              <span v-if="item.articleType===3">
              提问: {{item.articleTitle}}
            </span>
            </div>
            <div class="uk-position-center-right uk-margin-small-right">
              <span>{{item.createTime | formatDate('yyyy-MM-dd hh:mm')}}</span>
            </div>
          </div>

          <div v-if="item.logType===1" class="uk-card uk-card-small uk-card-default uk-card-body uk-width-3-4">
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
              <router-link v-bind:to="'/self/my_blog/'+item.articleId">{{item.articleTitle}}</router-link>
            </span>
              <span v-if="item.articleType===3">
              提问: {{item.articleTitle}}
            </span>
            </div>
            <div class="uk-position-center-right uk-margin-small-right">
              <span>{{item.createTime | formatDate('yyyy-MM-dd hh:mm')}}</span>
            </div>
          </div>

          <div v-if="item.logType===2">
            <article class="uk-comment uk-visible-toggle uk-background-muted uk-padding-small">
              <header class="uk-comment-header uk-position-relative">
                <div class="uk-grid-medium uk-flex-middle" uk-grid>
                  <div class="uk-width-auto">
                    <img class="uk-comment-avatar" v-bind:src="item.profileUrl" style="width: 52px;height: 52px">
                  </div>
                  <div class="uk-width-expand" style="padding-left: 10px;">
                    <div>
                      <h4 class="uk-comment-title uk-margin-remove uk-display-inline">
                        <router-link v-bind:to="'/user/'+item.userId">{{item.username}}
                        </router-link>
                      </h4>
                      <span v-if="item.type===0">
                      回复了您的评论:{{item.replyTitle}}...
                    </span>
                      <span v-if="item.type===1">
                      评论了您的动态:{{item.replyTitle}}...
                    </span>
                      <span v-if="item.type===2">
                      评论了您的博客:
                      <router-link v-bind:to="'/self/my_blog/'+item.replyId">{{item.replyTitle}}</router-link>
                    </span>
                      <span v-if="item.type===3">
                      回答了您的问题:
                      <router-link v-bind:to="'/self/my_question/'+item.replyId">{{item.replyTitle}}</router-link>
                    </span>
                    </div>
                    <div class="uk-comment-meta uk-subnav uk-subnav-divider uk-margin-remove-top uk-padding-remove">
                      <span>{{item.createTime | formatDate('yyyy-MM-dd hh:mm')}}</span>
                    </div>
                  </div>
                </div>
              </header>
              <div class="uk-comment-body">
                <p>
                  {{item.content}}
                </p>
              </div>
            </article>
          </div>


          <div v-if="item.logType===3" class="uk-card uk-card-small uk-card-default uk-card-body">
            <div>
              <router-link v-bind:to="'/user/'+item.userId">{{item.username}}</router-link>
              关注了您
            </div>
            <div class="uk-position-center-right uk-margin-small-right">
              <span>{{item.createTime | formatDate('yyyy-MM-dd hh:mm')}}</span>
            </div>
          </div>
        </li>
      </ul>
    </div>
    <blank-icon v-else></blank-icon>
    <pageable v-bind:fetch-data-func="fetchAboutmeListAction" size="5"></pageable>
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
