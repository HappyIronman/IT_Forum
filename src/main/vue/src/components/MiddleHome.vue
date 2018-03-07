<template>
  <div class="uk-container">
    <nav class="uk-navbar-container" uk-navbar>
      <div class="uk-navbar-left">
        <ul class="uk-navbar-nav">
          <li class="uk-active"><a href="">关注</a></li>
          <li>
            <router-link to="/moment">动态</router-link>
          </li>
          <li>
            <router-link to="/blog">博客</router-link>
          </li>
          <li>
            <router-link to="/question">提问</router-link>
          </li>
          <li>
            <router-link to="/aboutme">关于我</router-link>
          </li>
        </ul>
      </div>
    </nav>
    <div v-show="isShowPublishMoment">
      <div class="uk-margin-small-top uk-margin-small-bottom">
        <textarea v-model="moment.content"
                  class="uk-textarea" rows="5" placeholder="说点想说的..."></textarea>
        <div uk-grid class="uk-margin-remove">
          <button uk-icon="image" class="uk-card uk-button-small uk-padding-remove"></button>
          <div class="uk-card">
            <div uk-form-custom="target: > * > span:last-child">
              <select v-model="moment.isPrivate">
                <option value="false">公开</option>
                <option value="true">私人</option>
              </select>
              <span class="uk-link">
                <span uk-icon="icon: unlock"></span>
                <span></span>
              </span>
            </div>
          </div>
          <div class="uk-card uk-width-expand uk-text-right">
            <button class="uk-button uk-button-default uk-button-small"
                    v-on:click="isShowPublishMomentCompAction(false)">隐藏
            </button>
            <button class="uk-button uk-button-primary uk-button-small"
                    v-on:click="publishMoment">发表
            </button>
          </div>
        </div>
      </div>
    </div>
    <div>
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
  import {mapState} from 'vuex'
  import {mapActions} from 'vuex'

  export default {
    name: 'MiddleHome',
    data() {
      return {
        moment: {
          content: '',
          isPrivate: 0
        }
      }
    },
    computed: {
      ...mapState({
        isShowPublishMoment: state => state.moment.isShowPublishMoment
      })
    },
    methods: {
      ...mapActions([
        'isShowPublishMomentCompAction', // 将 `this.increment()` 映射为 `this.$store.dispatch('increment')`
        'publishMomentAction'
      ]),
      publishMoment: function () {
        this.publishMomentAction(this.moment).then(res => {
          console.log(res)
          if (res) {
            alert("hahaha")
          }
        })
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
