<template>
  <div>
    <div class="uk-card uk-card-default uk-card-large uk-margin-top">
      <div class="uk-card-header uk-padding-remove">
        <div class="uk-text-center">
          <img class="uk-border-circle" style="height: 160px;width: 160px" v-bind:src="userInfo.profileUrl">
        </div>
        <div class="uk-padding-small uk-text-center">
          <h3 class="uk-card-title uk-margin-remove-bottom">{{userInfo.username}}</h3>
          <p class="uk-text-meta uk-margin-remove-top">
            {{userInfo.intro}}
          </p>
          <div>
            <div id="relation">
              <p v-if="userInfo.relation===0" class="uk-margin-small-bottom">
                陌生人
              </p>
              <p v-if="userInfo.relation===1" class="uk-margin-small-bottom">
                已关注
              </p>
              <p v-if="userInfo.relation===2" class="uk-margin-small-bottom">
                我的粉丝
              </p>
              <p v-if="userInfo.relation===3" class="uk-margin-small-bottom">
                已互粉
              </p>
            </div>
            <div id="follow" v-if="userInfo.relation===0||userInfo.relation===2">
              <a class="uk-button uk-button-link" v-on:click="followUser">
                <span uk-icon="happy"></span>
                <span>关注</span>
              </a>
            </div>
          </div>
        </div>

      </div>


      <div class="uk-card-body uk-padding-remove uk-text-center">

        <div class="uk-margin-small-top">
          <span class="uk-text-small">TA的粉丝</span>
          <span class="uk-text-bold">{{userInfo.followerNum}}</span>
        </div>
        <div class="uk-margin-small-top">
          <span class="uk-text-small">关注的人</span>
          <span class="uk-text-bold">{{userInfo.followingNum}}</span>
        </div>
        <div class="uk-margin-small-top">
          <span class="uk-text-small">TA的动态</span>
          <router-link v-bind:to="'/user/'+userInfo.uniqueId+'/moment'" class="uk-text-bold">
            {{userInfo.momentNum}}
          </router-link>
        </div>
        <div class="uk-margin-small-top">
          <span class="uk-text-small">TA的博客</span>
          <router-link v-bind:to="'/user/'+userInfo.uniqueId+'/blog'" class="uk-text-bold">
            {{userInfo.blogNum}}
          </router-link>
        </div>
        <div class="uk-margin-small-top">
          <span class="uk-text-small">TA的提问</span>
          <router-link v-bind:to="'/user/'+userInfo.uniqueId+'/question'" class="uk-text-bold">
            {{userInfo.questionNum}}
          </router-link>
        </div>
        <div class="uk-margin-small-top">
          <span class="uk-text-small">学校</span>
          <span class="uk-text-bold">{{userInfo.school}}</span>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
  import {mapActions, mapState} from 'vuex'

  export default {
    name: 'UserRightHome',
    data() {
      return {
        msg: 'Welcome to Ironman\'s world haha !'
      }
    },
    computed: {
      ...mapState({
        userInfo: state => state.user.userInfo
      })
    },
    created: function () {
      this.fetchUserInfoAction(this.$route.params.uniqueId)
    },
    methods: {
      ...mapActions([
        'fetchUserInfoAction',
        'followUserAction'
      ]),
      followUser: function () {
        this.followUserAction(this.$route.params.uniqueId)
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
