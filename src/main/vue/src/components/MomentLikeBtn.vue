<template>
  <div class="uk-inline">
    <button class="uk-button uk-button-text uk-margin-small-right"
            v-bind:style="likeBtnStyle" v-on:click="likeArticle(localArticle, true)"
            v-bind:disabled="localArticle.likeCondition===3">
      <span v-show="localArticle.likeCondition!==2">赞</span>
      <span v-show="localArticle.likeCondition===2">已赞</span>
      <span>({{localArticle.likeNum}})</span>
    </button>
    <button class="uk-button uk-button-text uk-margin-small-right"
            v-bind:style="dislikeBtnStyle" v-on:click="likeArticle(localArticle, false)"
            v-bind:disabled="localArticle.likeCondition===2">
      <span v-show="localArticle.likeCondition!==3">踩</span>
      <span v-show="localArticle.likeCondition===3">已踩</span>
      <span>({{localArticle.dislikeNum}})</span>
    </button>
  </div>
</template>

<script>
  import {mapActions} from 'vuex'
  export default {
    props:['article','type'],
    name: 'MomentLikeBtn',
    data() {
      return {

      }
    },
    computed: {
      localArticle: function () {
        return this.article
      },
      //赞或者踩的状态，1-->未赞或踩过， 2--->已赞，3--->已踩
      likeBtnStyle: function () {
        return {color: this.localArticle.likeCondition === 2 ? 'blue' : 'black'}
      },
      dislikeBtnStyle: function () {
        return {color: this.localArticle.likeCondition === 3 ? 'blue' : 'black'}
      }
    },
    methods:{
      ...mapActions([
        'likeArticleAction',
        'cancelLikeArticleAction'
      ]),
      likeArticle:  function (localArticle, isLike) {
        var params = {
          targetId: localArticle.uniqueId,
          type: this.type,
          like: isLike
        }
        if (localArticle.likeCondition !== 1) {
          this.cancelLikeArticleAction(params)
          localArticle.likeCondition = 1
          if (isLike) {
            localArticle.likeNum -= 1
          } else {
            localArticle.dislikeNum -= 1
          }
        } else {
          this.likeArticleAction(params)
          if (isLike) {
            localArticle.likeCondition = 2
            localArticle.likeNum += 1
          } else {
            localArticle.likeCondition = 3
            localArticle.dislikeNum += 1
          }
        }
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
