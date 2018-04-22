<template>
  <div>
    <div class="uk-card uk-card-default uk-card-large uk-margin-top">
      <div class="uk-card-header uk-padding-remove">
        <div class="uk-text-center">
          <img class="uk-border-circle" style="height: 160px;width: 160px" v-bind:src="loginUserInfo.profileUrl">
        </div>
        <div class="uk-padding-small uk-text-center">
          <h3 class="uk-card-title uk-margin-remove-bottom">
            <router-link to="/self">{{loginUserInfo.username}}</router-link>
          </h3>
          <p class="uk-text-meta uk-margin-remove-top">
            {{loginUserInfo.intro}}
          </p>
        </div>

        <div class="uk-padding-remove uk-text-center">
          <div class=" uk-button-group">
            <button class="uk-button uk-button-default uk-margin-small-bottom" type="button">
              <span uk-icon="icon:  triangle-down"></span>
              <span>写点什么吧</span>
            </button>
            <div uk-dropdown="mode: click; boundary: ! .uk-button-group; boundary-align: true;"
                 class="uk-padding-remove">
              <ul class="uk-nav uk-dropdown-nav">
                <li class="uk-nav-header"><a class="uk-text-bold"
                                             v-on:click="isShowPublishMomentCompAction(true)">动态</a></li>
                <li class="uk-nav-header">
                  <router-link to="/write_blog" class="uk-text-bold">博客</router-link>
                </li>
                <li class="uk-nav-header">
                  <router-link to="/question">提问</router-link>
                </li>
              </ul>
            </div>
          </div>
        </div>

      </div>


      <div class="uk-card-body uk-padding-remove uk-text-center">


        <div class="uk-margin-small-top">
          <span class="uk-text-small">我的粉丝</span>
          <router-link to="/self/my_follower" class="uk-text-bold">{{loginUserInfo.followerNum}}</router-link>
        </div>
        <div class="uk-margin-small-top">
          <span class="uk-text-small">关注的人</span>
          <router-link to="self/my_following" class="uk-text-bold">{{loginUserInfo.followingNum}}</router-link>
        </div>
        <div class="uk-margin-small-top">
          <span class="uk-text-small">我的动态</span>
          <router-link to="/self/my_moment" class="uk-text-bold">{{loginUserInfo.momentNum}}</router-link>
        </div>
        <div class="uk-margin-small-top">
          <span class="uk-text-small">我的博客</span>
          <router-link to="/self/my_blog" class="uk-text-bold">
            {{loginUserInfo.blogNum}}
          </router-link>
        </div>
        <div class="uk-margin-small-top">
          <span class="uk-text-small">我的提问</span>
          <router-link to="/self/my_question" class="uk-text-bold">{{loginUserInfo.questionNum}}</router-link>
        </div>
        <div class="uk-margin-small-top">
          <span class="uk-text-small">学校</span>
          <span class="uk-text-bold">{{loginUserInfo.school}}</span>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
  import {mapActions, mapState} from 'vuex'

  export default {
    name: 'RightHome',
    data() {
      return {
        msg: 'Welcome to Ironman\'s world haha !'
      }
    },
    computed: {
      ...mapState({
        loginUserInfo: state => state.user.loginUserInfo
      })
    },
    created: function () {
      // this.fetchMineInfoAction()
    },
    methods: {
      ...mapActions([
        'isShowPublishMomentCompAction', // 将 `this.increment()` 映射为 `this.$store.dispatch('increment')`
        'fetchMineInfoAction'
      ])
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
