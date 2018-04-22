<template>
  <div>
    <div class="uk-card uk-card-default uk-margin-left uk-margin-right uk-margin-top">
      <div class="uk-card-header uk-padding-remove">
        <div class="uk-grid-small uk-flex-middle uk-margin-remove" uk-grid>
          <div>
            <img class="uk-border-circle" width="40" height="40" src="../assets/logo.png">
          </div>
          <div class="uk-width-auto">
            <h5 class="uk-card-title uk-margin-remove-bottom">
              <router-link v-bind:to="'/user/'+blog.userId">{{blog.username}}</router-link>
            </h5>
            <p class="uk-text-meta uk-margin-remove-top">
              {{blog.createTime | formatDate('yyyy-MM-dd hh:mm')}}
            </p>
          </div>
          <div class="uk-width-expand uk-text-right uk-text-small">
            <p v-if="blog.share" class="uk-margin-right">转发了
              <router-link v-bind:to="'/user/'+blog.originUserId">
                <span class="uk-text-success">{{blog.originUsername}}</span>
              </router-link>
              的博客
            </p>
            <p v-if="!blog.share" class="uk-margin-right">发表了新博客</p>
          </div>
        </div>
      </div>
      <div class="uk-card-body uk-padding-small">
        <div v-if="!blog.share">
          <p class="uk-margin-small">
            <router-link v-bind:to="'/user/'+blog.userId+'/blog/'+blog.uniqueId">
              {{blog.title}}
            </router-link>
          </p>
          <div class="uk-text-small uk-text-muted">
            <p class="uk-margin-small" v-html="blog.content"></p>
            <span v-if="blog.abstract">......</span>
          </div>
        </div>
        <div v-if="blog.share">
          <p class="uk-margin-small uk-text-bold">转发理由</p>
          <div class="uk-text-small uk-text-muted">
            <p class="uk-margin-small" v-html="blog.content"></p>
            <span v-if="blog.abstract">......</span>
          </div>
          <hr class="uk-divider-icon uk-margin-small">
          <div class="uk-margin-small uk-flex">
            <p class="uk-margin-remove-bottom"><span class="uk-text-bold">原博客:</span>
              &nbsp;&nbsp;&nbsp;
              {{blog.originTitle}}
            </p>
            <p class="uk-margin-remove uk-text-small  uk-text-muted uk-text-right uk-width-expand">
              发表于{{blog.originCreateTime | formatDate('yyyy-MM-dd hh:mm')}}
            </p>
          </div>
        </div>
      </div>
      <div class="uk-card-footer uk-padding-remove uk-text-right">
        <moment-like-btn v-bind:article="blog" type="2"></moment-like-btn>
        <button class="uk-button uk-button-text uk-margin-small-right" v-on:click="fetchCommentList">
          <span>评论</span>
          <span>({{blog.commentNum}})</span>
        </button>
        <router-link class="uk-button uk-button-text uk-margin-small-right" v-bind:to="'/share_blog/' + blog.uniqueId">
          <span>转发</span>
          <span>({{blog.shareNum}})</span>
        </router-link>
        <button class="uk-button uk-button-text uk-margin-right">
          <span>浏览</span>
          <span>({{blog.viewNum}})</span>
        </button>
      </div>
    </div>
    <comment-list v-if="isShowComment" class="uk-width-2-3 uk-align-center uk-margin-right"
                  v-bind:type="2"
                  v-bind:comment-list="commentList"
                  v-bind:article="blog"
                  v-on:refresh-comment-list="refreshCommentList">
    </comment-list>
  </div>
</template>

<script>
  import {mapActions} from 'vuex'
  import MomentLikeBtn from "./MomentLikeBtn.vue";
  import CommentList from "./CommentList.vue";
  import {requestApi} from "../api/requestUtils";

  export default {
    components: {MomentLikeBtn, CommentList},
    props: ['blog'],
    name: 'blogItem',
    data() {
      return {
        isShowComment: false,
        commentList: []
      }
    },
    methods: {
      fetchCommentList: function () {
        if (this.commentList.length === 0) {
          var payload = {
            'replyId': this.blog.uniqueId,
            'type': 2
          }
          requestApi('get', 'comments', payload, (res) => {
            this.commentList = this.commentList.concat(res.responseVO)
          })
        }
        this.isShowComment = !this.isShowComment
      },
      refreshCommentList: function () {
        var payload = {
          'replyId': this.blog.uniqueId,
          'type': 2
        }
        requestApi('get', 'comments', payload, (res) => {
          this.commentList = res.responseVO
        })
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
