<template>

  <div
    class="uk-card uk-card-default uk-card-body uk-card-small uk-flex uk-padding-remove-left uk-padding-remove-right"
    style="height: 160px">
    <div class="uk-width-1-4" style=" border-right:1px solid darkgrey">
      <img class="uk-border-circle uk-margin-top uk-flex-center"
           v-bind:src="user.profileUrl" style="width: 60px;height: 60px;"/>
    </div>
    <div class="uk-width-expand uk-text-left uk-margin-small-left">
      <p class=" uk-margin-small-bottom">
        <router-link v-bind:to="'/user/' + user.uniqueId">{{user.username}}</router-link>
      </p>
      <p v-if="user.relation===0" class="uk-text-meta uk-margin-remove">
        陌生人
      </p>
      <p v-if="user.relation===1" class="uk-text-meta uk-margin-remove">
        已关注
      </p>
      <p v-if="user.relation===2" class="uk-text-meta uk-margin-remove">
        我的粉丝
      </p>
      <p v-if="user.relation===3" class="uk-text-meta uk-margin-remove">
        已互粉
      </p>
      <div class="uk-flex uk-text-meta uk-margin-small-top">
        <p class="uk-margin-remove" style="padding-right: 4px; border-right:1px solid darkgrey">关注&nbsp;
          {{user.followingNum}}
        </p>
        <p class="uk-margin-remove" style="padding-left: 4px">粉丝&nbsp;
          {{user.followerNum}}
        </p>
      </div>
      <div class="uk-flex uk-text-meta uk-margin-small-top">
        <p class="uk-margin-remove" style="padding-right: 4px; border-right:1px solid darkgrey">动态&nbsp;
          {{user.momentNum}}
        </p>
        <p class="uk-margin-remove" style="padding: 0 4px; border-right:1px solid darkgrey">博客&nbsp;
          {{user.blogNum}}
        </p>
        <p class="uk-margin-remove" style="padding-left: 4px">提问&nbsp;
          {{user.questionNum}}
        </p>
      </div>
    </div>
  </div>
</template>
<script>
  export default {
    name: 'UserCard',
    data() {
      return {
        msg: 'Welcome to Ironman\'s world haha !'
      }
    },
    props:['user']
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
