<template xmlns="http://www.w3.org/1999/html" xmlns:animation="http://www.w3.org/1999/xhtml">
  <div class="uk-container uk-border-rounded uk-margin-top uk-margin-large-left uk-margin-large-right uk-padding-remove"
       style="border:2px solid darkgrey">
    <div
      class="uk-background-fixed uk-background-center-center uk-width-1-1 uk-text-center uk-padding uk-margin-small-bottom"
      style="background-image: url(../../static/img/3.jpg); height: 330px">
      <div>
        <img class="uk-border-circle" style="border:4px solid darkgrey; width: 120px; height: 120px"
             v-bind:src="loginUserInfo.profileUrl">
        <p class="uk-margin-small uk-text-bold uk-text-large">{{loginUserInfo.username}}</p>
        <p class="uk-margin-small uk-text-muted uk-text-small">{{loginUserInfo.intro}}</p>
      </div>
    </div>

    <div style="min-height: 800px" class="uk-grid uk-margin-remove">
      <div class="uk-width-1-4 uk-padding-remove uk-margin-right uk-margin-remove-left">
        <div class="uk-margin-small-bottom uk-margin-remove-left uk-padding-small
        uk-margin-remove-bottom uk-padding-remove-bottom"
             uk-grid>
          <div class="uk-padding-remove uk-width-1-3 uk-text-center uk-margin-small-top uk-margin-small-bottom"
               style="border-right: 1px solid gray ">
            <p class="uk-margin-remove-bottom uk-text-bold uk-text-large">
              <router-link to="/self/my_following">{{loginUserInfo.followingNum}}</router-link>
            </p>
            <p class="uk-margin-remove-top uk-text-small uk-text-muted">关注</p>
          </div>
          <div class="uk-padding-remove uk-width-1-3 uk-text-center uk-margin-small-top uk-margin-small-bottom"
               style="border-right: 1px solid gray ">
            <p class="uk-margin-remove-bottom uk-text-bold uk-text-large">
              <router-link to="/self/my_follower">{{loginUserInfo.followerNum}}</router-link>
            </p>
            <p class="uk-margin-remove-top uk-text-small uk-text-muted">粉丝</p>
          </div>
        </div>

        <div class="uk-margin-small-bottom uk-margin-remove-left uk-padding-small uk-margin-remove-top
        uk-margin-small-bottom uk-padding-remove-top uk-padding-remove-bottom"
             uk-grid>
          <div class="uk-padding-remove uk-width-1-3 uk-text-center uk-margin-small-top uk-margin-small-bottom"
               style="border-right: 1px solid gray ">
            <p class="uk-margin-remove-bottom uk-text-bold uk-text-large">
              <router-link to="/self/my_moment">{{loginUserInfo.momentNum}}</router-link>
            </p>
            <p class="uk-margin-remove-top uk-text-small uk-text-muted">动态</p>
          </div>
          <div class="uk-padding-remove uk-width-1-3 uk-text-center uk-margin-small-top uk-margin-small-bottom"
               style="border-right: 1px solid gray ">
            <p class="uk-margin-remove-bottom uk-text-bold uk-text-large">
              <router-link to="/self/my_blog">{{loginUserInfo.blogNum}}</router-link>
            </p>
            <p class="uk-margin-remove-top uk-text-small uk-text-muted">博客</p>
          </div>
          <div class="uk-padding-remove uk-width-1-3 uk-text-center uk-margin-small-top uk-margin-small-bottom">
            <p class="uk-margin-remove-bottom uk-text-bold uk-text-large">
              <router-link to="/self/my_question">{{loginUserInfo.questionNum}}</router-link>
            </p>
            <p class="uk-margin-remove-top uk-text-small uk-text-muted">提问</p>
          </div>
        </div>

        <div>
          <div class="uk-card uk-card-default uk-card-small uk-width-1-1">
            <div class="uk-card-body">
              <p><span class="uk-text-small">个人资料完成度 </span><span class="uk-text-bold uk-text-large">25%</span></p>
            </div>
            <div class="uk-card-footer">
              <router-link to="/self/edit" class="uk-button uk-button-text">编辑个人资料 ></router-link>
            </div>
          </div>
        </div>

        <div class="uk-card uk-card-default uk-card-small uk-width-1-1 uk-margin-top">
          <div class="uk-card-header">
            <p>我的相册</p>
          </div>
          <div class="uk-card-body uk-padding-remove">
            <div class="uk-text-center uk-margin-remove-left" uk-grid uk-lightbox="animation: fade">
              <div
                class="uk-width-2-3 uk-card-body uk-margin-remove uk-padding-remove" style="height: 160px">
                <a href="../../static/img/3.jpg">
                  <img src="../../static/img/3.jpg" style="width: 100% ;height: 100%"/>
                </a>
              </div>
              <div class="uk-padding-remove uk-margin-remove uk-width-1-3">
                <div style="height: 80px">
                  <a href="../../static/img/2.jpg">
                    <img src="../../static/img/2.jpg" style="width: 100% ;height: 100%; padding: 2px"/>
                  </a>
                </div>
                <div style="height: 80px">
                  <a href="../../static/img/1.jpg">
                    <img src="../../static/img/1.jpg" style="width: 100% ;height: 100%; padding: 2px"/>
                  </a>
                </div>
              </div>

              <!--<div class="uk-text-center uk-margin-remove-left-->
              <!--uk-child-width-1-3" style="height: 80px" uk-grid>-->
              <div class="uk-padding-remove uk-width-1-3 uk-margin-remove" style="height: 80px">
                <a href="../../static/img/1.jpg">
                  <img src="../../static/img/1.jpg" style="width: 100% ;height: 100%; padding: 2px"/>
                </a>
              </div>
              <div class="uk-padding-remove uk-width-1-3 uk-margin-remove" style="height: 80px">
                <a href="../../static/img/2.jpg">
                  <img src="../../static/img/2.jpg" style="width: 100% ;height: 100%; padding: 2px"/>
                </a>
              </div>
              <div class="uk-padding-remove uk-width-1-3 uk-margin-remove" style="height: 80px">
                <a href="../../static/img/3.jpg">
                  <img src="../../static/img/3.jpg" style="width: 100% ;height: 100%; padding: 2px"/>
                </a>
              </div>
              <!--</div>-->
            </div>
          </div>


          <div class="uk-card-footer">
            <button href="#" class="uk-button uk-button-text" disabled>查看更多 ></button>
          </div>
        </div>

      </div>

      <div class="uk-width-expand uk-padding-remove">
        <router-view></router-view>
      </div>

    </div>
  </div>
</template>

<script>
  import {mapActions, mapState} from 'vuex'

  export default {
    name: 'selfPage',
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
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
