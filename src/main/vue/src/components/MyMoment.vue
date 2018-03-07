<template>
  <div>
    <div class="uk-text-center">
      <span class="uk-label uk-label-success">我的动态</span>
    </div>
    <div v-for="myMoment in myMomentList">
      <div class="uk-card uk-card-default uk-margin-left uk-margin-right uk-margin-top">
        <div class="uk-card-header uk-padding-remove">
          <div class="uk-grid-small uk-flex-middle uk-margin-remove" uk-grid>
            <div>
              <img class="uk-border-circle" width="40" height="40" src="../assets/logo.png">
            </div>
            <div class="uk-width-expand">
              <h5 class="uk-card-title uk-margin-remove-bottom">{{myMoment.username}}</h5>
              <p class="uk-text-meta uk-margin-remove-top">
                {{myMoment.createTime | formatDate('yyyy-MM-dd hh:mm')}}
              </p>
            </div>
          </div>
        </div>
        <div class="uk-card-body">
          <p>{{myMoment.content}}</p>
        </div>
        <div class="uk-card-footer uk-padding-remove uk-text-right">
          <button class="uk-button uk-button-text uk-margin-small-right">
            <span>喜欢</span>
            <span>({{myMoment.likeNum}})</span>
          </button>
          <button class="uk-button uk-button-text uk-margin-small-right">
            <span>不喜欢</span>
            <span>({{myMoment.dislikeNum}})</span>
          </button>
          <button class="uk-button uk-button-text uk-margin-small-right">
            <span>评论</span>
            <span>({{myMoment.commentNum}})</span>
          </button>
          <button class="uk-button uk-button-text uk-margin-small-right">
            <span>转发</span>
            <span>({{myMoment.shareNum}})</span>
          </button>
          <button class="uk-button uk-button-text uk-margin-right">
            <span>浏览</span>
            <span>({{myMoment.viewNum}})</span>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapState} from 'vuex'
  import {mapActions} from 'vuex'

  export default {
    name: 'MyMoment',
    data() {
      return {}
    },
    computed: {
      ...mapState({
        myMomentList: state => state.moment.myMomentList
      })
    },
    created: function () {
      this.fetchMyMomentListAction({page: 0, size: 10})
    },
    methods: {
      ...mapActions([
        'fetchMyMomentListAction'
      ])
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
