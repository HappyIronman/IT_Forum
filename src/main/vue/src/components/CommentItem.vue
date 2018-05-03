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
              <li><a href="javascript:void(0);"
                     v-on:click="isShowConversationList = !isShowConversationList">对话列表{{commentItem.commentNum}}</a>
              </li>
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
    <div v-if="isShowConversationList" class="uk-margin-small-top">
      <div class="uk-margin-left uk-margin-small-bottom" v-for="comment in conversationList">
        <sub-comment-item v-bind:comment="comment"></sub-comment-item>
      </div>
      <pageable v-bind:fetch-data-func="fetchConversationList" size="5"></pageable>
    </div>
  </div>
</template>

<script>
  import {requestApi} from '../api/requestUtils'
  import SubCommentItem from "./SubCommentItem.vue";
  import ReplyCommentComp from "./ReplyCommentComp.vue";
  import Pageable from "./Pageable.vue";

  export default {
    components: {
      Pageable,
      SubCommentItem, ReplyCommentComp
    },
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
      fetchConversationList: function (pageParam) {
        if (pageParam.page === 0) {
          if (this.conversationList.length === parseInt(pageParam.size)) {
            return true;
          }
          if (this.conversationList.length > 0) {
            return false;
          }
        }
        return requestApi('get', 'comments', {
            replyId: this.commentItem.uniqueId,
            type: 0,
            page: pageParam.page,
            size: pageParam.size
          },
          (res) => {
            this.conversationList = this.conversationList.concat(res.responseVO)
            return (res.responseVO != null && res.responseVO.length === parseInt(pageParam.size))
          }
        )
      },
      refreshConversationList: function () {
        this.isShowConversationList = false
        this.conversationList = []
        //重新挂载分页插件,以达到重新加载评论列表效果
        this.$nextTick(() => {
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
