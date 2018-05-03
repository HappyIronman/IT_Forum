<template>
  <div class="uk-padding">
    <div style="min-height: 300px">
      <p class="uk-text-center uk-text-lead">{{question.title}}</p>
      <div>
        <div v-html="question.content"></div>
        <div class="uk-text-muted uk-text-right">
          <p class="uk-margin-small-bottom">作者:&nbsp;{{question.username}}</p>
          <p class="uk-margin-small-top">发表于:&nbsp;{{question.createTime | formatDate('yyyy-MM-dd hh:mm')}}</p>
        </div>

        <div v-show="question.share">
          <hr class="uk-divider-icon uk-margin-small">
          <div class="uk-text-center">
            <span class="uk-label">原博文</span>
          </div>
          <p class="uk-text-bold uk-text-lead">{{question.originTitle}}</p>

          <div v-html="question.originContent"></div>

          <div class="uk-text-muted uk-text-right">
            <p class="uk-margin-small-bottom">原作者:&nbsp;{{question.originUsername}}</p>
            <p class="uk-margin-small-top">
              发表于:&nbsp;{{question.originCreateTime | formatDate('yyyy-MM-dd hh:mm')}}</p>
          </div>
        </div>
      </div>
    </div>

    <div class="uk-width-1-1\@l uk-margin-large-top">
      <div class="uk-text-right">
        <div class="uk-text-bold">
          <moment-like-btn v-bind:article="question" type="2"></moment-like-btn>
        </div>
        <div class="uk-margin-small-right">
          <span class="uk-text-bold uk-text-small uk-margin-small-right">
            分享:&nbsp;{{question.shareNum}}
          </span>
          <span class="uk-text-muted uk-text-small">浏览:&nbsp;{{question.viewNum}}</span>
        </div>
      </div>

      <div class="uk-margin-top">
        <div class="uk-text-center">
          <span class="uk-label uk-label-success">评论列表({{question.commentNum}})</span>
        </div>
        <div>
          <comment-list v-bind:type="2"
                        v-bind:articleId="$route.params.blogId">
          </comment-list>
        </div>
      </div>
    </div>

  </div>

</template>

<script>
  import {mapActions, mapState} from 'vuex'
  import CommentList from "./CommentList.vue";
  import {requestApi} from "../api/requestUtils";
  import MomentLikeBtn from "./MomentLikeBtn.vue";

  export default {
    components: {CommentList, MomentLikeBtn},
    name: 'QuestionDetail',
    data() {
      return {
        commentList: []
      }
    },
    computed: {
      ...mapState({
        question: state => state.blog.question
      })
    },
    created: function () {
      this.fetchquestionAction(this.$route.params.blogId)
    },
    methods: {
      ...mapActions([
        'fetchQuestionAction'
      ]),
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
