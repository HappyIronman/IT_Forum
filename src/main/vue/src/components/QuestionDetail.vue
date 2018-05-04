<template>
  <div class="uk-padding">
    <div style="min-height: 200px">
      <div class="uk-card uk-card-default uk-margin-left uk-margin-right uk-margin-top">
        <div class="uk-card-header uk-padding-remove">
          <div class="uk-grid-small uk-flex-middle uk-margin-remove uk-text-center" uk-grid>
            <p class="uk-margin-small uk-width-1-1\@l uk-text-lead">
              {{question.title}}
            </p>
          </div>
        </div>
        <div class="uk-card-body uk-padding-small">
          <div>
            <p class="uk-margin-small-bottom" v-html="question.content"></p>
          </div>
          <div class="uk-text-right">
            <div>
              <img v-bind:src="question.profileUrl" style="width: 14px;height: 14px"/>
              <span class="uk-text-muted uk-text-small">{{question.username}}</span>
            </div>
            <div>
              <span class="uk-text-muted uk-text-small">
                {{question.createTime | formatDate('yyyy-MM-dd hh:mm')}}
              </span>
            </div>
          </div>
        </div>
        <div class="uk-card-footer uk-padding-remove uk-text-right">
          <span class="uk-text-small uk-margin-right">
              <span>回答</span>
              <span>({{question.commentNum}})</span>
          </span>

          <span class="uk-text-small uk-margin-right">
            <span>浏览</span>
            <span>({{question.viewNum}})</span>
          </span>
        </div>
      </div>
    </div>

    <div class="uk-margin-top">
      <div class="uk-text-center">
        <span class="uk-label uk-label-success">回答列表({{question.commentNum}})</span>
      </div>
      <div>
        <comment-list v-bind:type="3"
                      v-bind:articleId="$route.params.questionId">
        </comment-list>
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
        question: state => state.question.questionDetail
      })
    },
    created: function () {
      this.fetchQuestionDetailAction(this.$route.params.questionId)
    },
    methods: {
      ...mapActions([
        'fetchQuestionDetailAction'
      ]),
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
