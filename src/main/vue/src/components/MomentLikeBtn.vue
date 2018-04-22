<template>
  <div class="uk-inline">
    <button class="uk-button uk-button-text uk-margin-small-right"
            v-bind:style="likeBtnStyle" v-on:click="likeArticle(article, true)"
            v-bind:disabled="article.likeCondition===3">
      <span v-show="article.likeCondition!==2">赞</span>
      <span v-show="article.likeCondition===2">已赞</span>
      <span>({{article.likeNum}})</span>
    </button>
    <button class="uk-button uk-button-text uk-margin-small-right"
            v-bind:style="dislikeBtnStyle" v-on:click="likeArticle(article, false)"
            v-bind:disabled="article.likeCondition===2">
      <span v-show="article.likeCondition!==3">踩</span>
      <span v-show="article.likeCondition===3">已踩</span>
      <span>({{article.dislikeNum}})</span>
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
      //赞或者踩的状态，1-->未赞或踩过， 2--->已赞，3--->已踩
      likeBtnStyle: function () {
        return {color: this.article.likeCondition === 2 ? 'blue' : 'black'}
      },
      dislikeBtnStyle: function () {
        return {color: this.article.likeCondition === 3 ? 'blue' : 'black'}
      }
    },
    methods:{
      ...mapActions([
        'likeArticleAction',
        'cancelLikeArticleAction'
      ]),
      likeArticle:  function (article, isLike) {
        var params = {
          targetId: article.uniqueId,
          type: this.type,
          like: isLike
        }
        if (article.likeCondition !== 1) {
          this.cancelLikeArticleAction(params)
          article.likeCondition = 1
          if (isLike) {
            article.likeNum -= 1
          } else {
            article.dislikeNum -= 1
          }
        } else {
          this.likeArticleAction(params)
          if (isLike) {
            article.likeCondition = 2
            article.likeNum += 1
          } else {
            article.likeCondition = 3
            article.dislikeNum += 1
          }
        }
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
