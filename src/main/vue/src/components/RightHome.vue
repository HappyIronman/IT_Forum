<template>
  <div>
    <div class="uk-card uk-card-default uk-card-large">
      <div class="uk-card-header uk-padding-remove">
        <div class="uk-text-center">
          <img class="uk-border-circle" width="160" height="160" src="../assets/logo.png">
        </div>
        <div class="uk-padding-small uk-text-center">
          <h3 class="uk-card-title uk-margin-remove-bottom">Happy Ironman</h3>
          <p class="uk-text-meta uk-margin-remove-top">
            a lonely child
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
          <span class="uk-text-bold">12</span>
        </div>
        <div class="uk-margin-small-top">
          <span class="uk-text-small">关注的人</span>
          <span class="uk-text-bold">134</span>
        </div>
        <div class="uk-margin-small-top">
          <span class="uk-text-small">我的动态</span>
          <router-link to="/my_moment" class="uk-text-bold">14</router-link>
        </div>
        <div class="uk-margin-small-top">
          <span class="uk-text-small">我的博客</span>
          <router-link to="/my_blog" class="uk-text-bold">14</router-link>
        </div>
        <div class="uk-margin-small-top">
          <span class="uk-text-small">我的提问</span>
          <span class="uk-text-bold">1</span>
        </div>
        <div class="uk-margin-small-top">
          <span class="uk-text-small">学校</span>
          <span class="uk-text-bold">大连理工大学</span>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
  import {mapActions} from 'vuex'

  export default {
    name: 'RightHome',
    data() {
      return {
        msg: 'Welcome to Ironman\'s world haha !'
      }
    },
    created: function () {
    },
    methods: {
      ...mapActions([
        'isShowPublishMomentCompAction', // 将 `this.increment()` 映射为 `this.$store.dispatch('increment')`
      ])
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
