<template>
  <div>
    <article class="uk-comment uk-visible-toggle uk-background-muted uk-padding-small">
      <header class="uk-comment-header uk-position-relative">
        <div class="uk-grid-medium uk-flex-middle" uk-grid>
          <div class="uk-width-auto">
            <img class="uk-comment-avatar" v-bind:src="commentItem.profile" style="width: 52px;height: 52px">
          </div>
          <div class="uk-width-expand" style="padding-left: 10px;">
            <h4 class="uk-comment-title uk-margin-remove">
              <router-link class="uk-link-reset" v-bind:to="'/user/'+commentItem.userId">{{commentItem.username}}
              </router-link>
            </h4>
            <ul class="uk-comment-meta uk-subnav uk-subnav-divider uk-margin-remove-top">
              <li><span>{{commentItem.createTime | formatDate('yyyy-MM-dd hh:mm')}}</span></li>
              <li><a href="javascript:void(0);" v-on:click="fetchConversationList">对话列表{{commentItem.commentNum}}</a></li>
            </ul>
          </div>
        </div>
        <div class="uk-position-top-right">
          <div>
            <a class="uk-link-muted" href="#">赞{{commentItem.likeNum}}</a>
            <a class="uk-link-muted" href="#">踩{{commentItem.dislikeNum}}</a>
          </div>
          <a class="uk-link-text uk-hidden-hover" href="javascript:void(0);" v-on:click="showReplyComp">回复</a>
        </div>
      </header>
      <div class="uk-comment-body">
        <p>
          {{commentItem.content}}
        </p>
      </div>
    </article>
    <reply-comment-comp
      v-show="isShowReplyComp"
      v-bind:reply-id="commentItem.uniqueId"
      v-bind:type="0"
      v-on:refresh-conversation-list="refreshConversationList"
      v-bind:rows="1"></reply-comment-comp>
    <ul class="uk-margin-small-top" v-if="isShowConversationList">
      <li class="uk-margin-small-top" v-for="comment in conversationList">
        <sub-comment-item v-bind:comment="comment"></sub-comment-item>
      </li>
    </ul>
  </div>
</template>

<script>
  import {requestApi} from '../api/requestUtils'
  import SubCommentItem from "./SubCommentItem.vue";
  import ReplyCommentComp from "./ReplyCommentComp.vue";

  export default {
    components: {SubCommentItem, ReplyCommentComp},
    name: 'CommentItem',
    props: ['commentItem'],
    data() {
      return {
        isShowConversationList: false,
        isShowReplyComp: false,
        conversationList: []
      }
    },
    methods: {
      fetchConversationList: function () {
        if (this.conversationList.length === 0) {
          var payload = {
            'replyId': this.commentItem.uniqueId,
            'type': 0
          }
          requestApi('get', 'comments', payload, (res) => {
            this.conversationList = this.conversationList.concat(res.responseVO)
          })
        }
        this.isShowConversationList = !this.isShowConversationList
      },
      refreshConversationList: function () {
        var payload = {
          'replyId': this.commentItem.uniqueId,
          'type': 0
        }
        requestApi('get', 'comments', payload, (res) => {
          this.conversationList = res.responseVO
          this.isShowConversationList = true
        })
      },

      showReplyComp: function () {
        this.isShowReplyComp = !this.isShowReplyComp
        return this.isShowReplyComp
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
