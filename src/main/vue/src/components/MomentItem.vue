<template>
  <div>
    <div class="uk-card uk-card-default uk-margin-left uk-margin-right uk-margin-top">
      <div class="uk-card-header uk-padding-remove">
        <div class="uk-grid-small uk-flex-middle uk-margin-remove" uk-grid>
          <div>
            <img class="uk-border-circle" width="40" height="40" src="../assets/logo.png">
          </div>
          <div class="uk-width-auto">
            <h5 class="uk-card-title uk-margin-remove-bottom">{{moment.username}}</h5>
            <p class="uk-text-meta uk-margin-remove-top">
              {{moment.createTime | formatDate('yyyy-MM-dd hh:mm')}}
            </p>
          </div>
          <div class="uk-width-expand uk-text-right uk-text-small">
            <p v-if="moment.share" class="uk-margin-right">转发了
              <span class="uk-text-success">{{moment.originUsername}}</span> 的动态
            </p>
            <p v-if="!moment.share" class="uk-margin-right">发表了新动态</p>
          </div>
        </div>
      </div>
      <div class="uk-card-body uk-padding-small">
        <p v-if="!moment.share">{{moment.content}}</p>
        <div v-if="moment.share">
          <p class="uk-margin-remove">{{moment.content}}</p>
          <hr class="uk-divider-icon uk-margin-small">
          <p class="uk-margin-small uk-text-small uk-text-bold">原文:</p>
          <p class="uk-margin-remove">{{moment.originContent}}</p>
          <p class="uk-margin-remove uk-text-small  uk-text-muted uk-text-right">
            发表于{{moment.originCreateTime | formatDate('yyyy-MM-dd hh:mm')}}
          </p>
        </div>
      </div>
      <div class="uk-card-footer uk-padding-remove uk-text-right">
        <button class="uk-button uk-button-text uk-margin-small-right"
                v-bind:style="likeBtnStyle" v-on:click="likeMoment(moment, true)"
                v-bind:disabled="moment.likeCondition===3">
          <span v-show="moment.likeCondition!==2">赞</span>
          <span v-show="moment.likeCondition===2">已赞</span>
          <span>({{moment.likeNum}})</span>
        </button>
        <button class="uk-button uk-button-text uk-margin-small-right"
                v-bind:style="dislikeBtnStyle" v-on:click="likeMoment(moment, false)"
                v-bind:disabled="moment.likeCondition===2">
          <span v-show="moment.likeCondition!==3">踩</span>
          <span v-show="moment.likeCondition===3">已踩</span>
          <span>({{moment.dislikeNum}})</span>
        </button>
        <button class="uk-button uk-button-text uk-margin-small-right">
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
    <moment-share-modal v-bind:id="'m'+ moment.uniqueId" v-bind:moment-info="moment"></moment-share-modal>
  </div>
</template>

<script>
  import {mapActions} from 'vuex'
  import MomentShareModal from "./MomentShareModal";

  export default {
    components: {MomentShareModal},
    name: 'MomentItem',
    props: ['moment'],
    comments: {
      MomentShareModal
    },
    data() {
      return {}
    },
    computed: {
      //赞或者踩的状态，1-->未赞或踩过， 2--->已赞，3--->已踩
      likeBtnStyle: function () {
        return {color: this.moment.likeCondition === 2 ? 'blue' : 'black'}
      },
      dislikeBtnStyle: function () {
        return {color: this.moment.likeCondition === 3 ? 'blue' : 'black'}
      }
    },
    methods: {
      ...mapActions([
        'likeArticleAction',
        'cancelLikeArticleAction'
      ]),
      onclickShareMoment: function () {
        UIkit.modal('#m' + this.moment.uniqueId).show();
      },
      likeMoment: function (moment, isLike) {
        var params = {
          targetId: moment.uniqueId,
          type: 1,
          like: isLike
        }
        if (moment.likeCondition !== 1) {
          this.cancelLikeArticleAction(params)
          moment.likeCondition = 1
          if (isLike) {
            moment.likeNum -= 1
          } else {
            moment.dislikeNum -= 1
          }
        } else {
          this.likeArticleAction(params)
          if (isLike) {
            moment.likeCondition = 2
            moment.likeNum += 1
          } else {
            moment.likeCondition = 3
            moment.dislikeNum += 1
          }
        }
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
