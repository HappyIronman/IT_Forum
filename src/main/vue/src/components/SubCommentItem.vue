<template>
  <div>
    <article class="uk-comment uk-visible-toggle uk-comment-primary uk-padding-small">
      <div class="uk-comment-body">
        <div>
          <div class="uk-flex">
            <div class="uk-width-auto">
              <img class="uk-comment-avatar" v-bind:src="comment.profile" style="width: 34px;height: 34px">
              <span class="uk-text-bold">{{comment.username}}</span>
              <span class="uk-text-muted">回复:&nbsp;</span>
              <span>{{comment.content}}</span>
            </div>
            <div class="uk-width-expand uk-text-right">
              <a class="uk-link-muted uk-hidden-hover" href="javascript:void(0);" v-on:click="showReplyComp()">回复</a>
            </div>
          </div>
        </div>
        <div class="uk-inline uk-width-1-1\@l">
          <div class="uk-text-left uk-width-auto">
            <span class="uk-text-muted">{{comment.createTime | formatDate('yyyy-MM-dd hh:mm')}}</span>
          </div>
          <div class="uk-position-right">
            <ul class="uk-comment-meta uk-subnav uk-subnav-divider uk-margin-remove">
              <li><a href="javascript:void(0);" v-on:click="fetchConversationList">对话列表{{comment.replyNum}}</a></li>
              <li><a class="uk-link-muted" href="#">赞{{comment.likeNum}}</a></li>
              <li><a class="uk-link-muted" href="#">踩{{comment.dislikeNum}}</a></li>
            </ul>
          </div>
        </div>
      </div>
    </article>
    <reply-comment-comp
      v-show="isShowReplyComp"
      v-bind:reply-id="comment.uniqueId"
      v-bind:type="0"
      v-bind:rows="1"
      v-on:refresh-conversation-list="refreshConversationList">
    </reply-comment-comp>

    <div v-if="isShowConversationList">
      <div v-for="comment in conversationList" class="uk-margin-small-left uk-margin-small-top">
        <sub-comment-item v-bind:comment="comment"></sub-comment-item>
      </div>
    </div>
  </div>
</template>

<script>
  import {requestApi} from '../api/requestUtils'
  import ReplyCommentComp from "./ReplyCommentComp.vue";

  export default {
    name: 'SubCommentItem',
    components: {ReplyCommentComp},
    props: ['comment'],
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
            'replyId': this.comment.uniqueId,
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
          'replyId': this.comment.uniqueId,
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
