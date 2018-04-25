<template>
  <div>
    <div class="uk-card uk-card-default uk-margin-left uk-margin-right uk-margin-top">
      <div class="uk-card-header uk-padding-remove">
        <div class="uk-grid-small uk-flex-middle uk-margin-remove" uk-grid>
          <div>
            <img class="uk-border-circle" style="width: 40px;height: 40px" v-bind:src="moment.profileUrl">
          </div>
          <div class="uk-width-auto">
            <h5 class="uk-card-title uk-margin-remove-bottom">
              <router-link v-bind:to="'/user/'+moment.userId">{{moment.username}}</router-link>
            </h5>
            <p class="uk-text-meta uk-margin-remove-top">
              {{moment.createTime | formatDate('yyyy-MM-dd hh:mm')}}
            </p>
          </div>
          <div class="uk-width-expand uk-text-right uk-text-small">
            <p v-if="moment.share" class="uk-margin-right">转发了
              <router-link v-bind:to="'/user/'+moment.originUserId">
                <span class="uk-text-success">{{moment.originUsername}}</span>
              </router-link>
              的动态
            </p>
            <p v-if="!moment.share" class="uk-margin-right">发表了新动态</p>
          </div>
        </div>
      </div>
      <div class="uk-card-body uk-padding-small">
        <p v-if="moment.content.length <= 80">{{moment.content}}</p>
        <div v-if="moment.content.length > 80">
          <p class="uk-margin-small-bottom">
            {{isDisplayAbstractContent ? (absContent + '  ...' ) : moment.content}}
          </p>
          <div class="uk-text-right">
            <button class="uk-button uk-button-text uk-text-muted"
                    v-on:click="isDisplayAbstractContent=!isDisplayAbstractContent">
              <span v-if="isDisplayAbstractContent">展开全文</span>
              <span v-if="!isDisplayAbstractContent">收起</span>
            </button>
          </div>

        </div>
        <div v-if="moment.containPic" class="uk-grid" uk-lightbox="animation: fade">
          <a v-for="url in moment.picUrlList" v-bind:href="url" class="uk-width-1-3" style="height: 200px">
            <img v-bind:src="url" style="height: 100%;width: 100%"/>
          </a>
        </div>
        <div v-if="moment.share">
          <hr class="uk-divider-icon uk-margin-small">
          <p class="uk-margin-small uk-text-small uk-text-bold">原文:</p>
          <p class="uk-margin-remove">{{moment.originContent}}&nbsp;&nbsp;...</p>
          <p class="uk-margin-remove uk-text-small  uk-text-muted uk-text-right">
            发表于{{moment.originCreateTime | formatDate('yyyy-MM-dd hh:mm')}}
          </p>
        </div>
      </div>
      <div class="uk-card-footer uk-padding-remove uk-text-right">
        <moment-like-btn v-bind:article="moment" type="1"></moment-like-btn>
        <button class="uk-button uk-button-text uk-margin-small-right" v-on:click="fetchCommentList">
          <span>评论</span>
          <span>({{moment.commentNum}})</span>
        </button>
        <button class="uk-button uk-button-text uk-margin-small-right" v-on:click="onclickShareMoment">
          <span>转发</span>
          <span>({{moment.shareNum}})</span>
        </button>
        <button class="uk-button uk-button-text uk-margin-right">
          <span>浏览</span>
          <span>({{moment.viewNum}})</span>
        </button>
      </div>
    </div>
    <comment-list v-if="isShowComment" class="uk-width-2-3 uk-align-center uk-margin-right"
                  v-bind:type="1"
                  v-bind:comment-list="commentList"
                  v-bind:article="moment"
                  v-on:refresh-comment-list="refreshCommentList">
    </comment-list>

    <moment-share-modal v-bind:id="'m'+ moment.uniqueId" v-bind:moment-info="moment"></moment-share-modal>
  </div>
</template>

<script>
  import {mapActions} from 'vuex'
  import MomentShareModal from "./MomentShareModal";
  import CommentList from "./CommentList.vue";
  import {requestApi} from '../api/requestUtils'
  import MomentLikeBtn from "./MomentLikeBtn.vue";

  export default {
    components: {
      MomentLikeBtn,
      CommentList,
      MomentShareModal
    },
    name: 'MomentItem',
    props: ['moment'],
    comments: {
      MomentShareModal
    },
    data() {
      return {
        isShowComment: false,
        commentList: [],
        //是否显示的是简略信息
        isDisplayAbstractContent: true
      }
    },
    computed: {
      absContent: function () {
        return this.moment.content.slice(0, 80)
      }
    },

    methods: {

      onclickShareMoment: function () {
        UIkit.modal('#m' + this.moment.uniqueId).show();
      },
      onclickShowFullContent: function () {

      },
      fetchCommentList: function () {
        if (this.commentList.length === 0) {
          var payload = {
            'replyId': this.moment.uniqueId,
            'type': 1
          }
          requestApi('get', 'comments', payload, (res) => {
            this.commentList = this.commentList.concat(res.responseVO)
          })
        }
        this.isShowComment = !this.isShowComment
      },
      refreshCommentList: function () {
        var payload = {
          'replyId': this.moment.uniqueId,
          'type': 1
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
