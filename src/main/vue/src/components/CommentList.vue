<template>
  <div>
    <reply-comment-comp v-bind:reply-id="articleId"
                        v-bind:type="type"
                        v-on:refresh-conversation-list="refreshCommentList"
                        v-bind:rows="3"></reply-comment-comp>
    <ul class="uk-comment-list">
      <li v-for="commentItem in commentList" class="uk-margin-small-top">
        <comment-item v-bind:comment-item="commentItem"></comment-item>
      </li>
    </ul>
    <pageable v-if="isRefresh" v-bind:fetch-data-func="fetchCommentList" size="5"></pageable>
  </div>
</template>

<script>
  import {requestApi} from '../api/requestUtils'
  import CommentItem from "./CommentItem.vue";
  import ReplyCommentComp from "./ReplyCommentComp.vue";
  import Pageable from "./Pageable.vue";

  export default {
    components: {
      Pageable,
      ReplyCommentComp,
      CommentItem
    },
    name: 'CommentList',
    props: ['type', 'articleId'],
    data() {
      return {
        commentList: [],
        isRefresh: true
      }
    },
    computed: {},
    created: function () {
      this.fetchCommentList()
    },
    methods: {
      fetchCommentList: function (pageParam) {
        if (pageParam.page === 0) {
          if (this.commentList.length === parseInt(pageParam.size)) {
            return true;
          }
          if (this.commentList.length > 0) {
            return false;
          }
        }
        return requestApi('get', 'comments', {
            replyId: this.articleId,
            type: this.type,
            page: pageParam.page,
            size: pageParam.size
          },
          (res) => {
            this.commentList = this.commentList.concat(res.responseVO)
            return (res.responseVO != null && res.responseVO.length === parseInt(pageParam.size))
          }
        )
      },
      refreshCommentList: function () {
        this.isRefresh = false
        this.commentList = []
        //重新挂载分页插件,以达到重新加载评论列表效果
        this.$nextTick(() => {
          this.isRefresh = true
        })
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
