<template>
  <div>
    <div class="uk-card uk-card-default uk-margin-left uk-margin-right uk-margin-top">
      <div class="uk-card-header uk-padding-remove">
        <div class="uk-grid-small uk-flex-middle uk-margin-remove" uk-grid>
          <div>
            <img class="uk-border-circle" width="40" height="40" src="../assets/logo.png">
          </div>
          <div class="uk-width-expand">
            <h5 class="uk-card-title uk-margin-remove-bottom">Title</h5>
            <p class="uk-text-meta uk-margin-remove-top">
              <time datetime="2016-04-01T19:00">April 01, 2016</time>
            </p>
          </div>
        </div>
      </div>
      <div class="uk-card-body">
        <p>{{momentList}}</p>
      </div>
      <div class="uk-card-footer uk-padding-remove uk-text-right">
        <button class="uk-button uk-button-text uk-margin-small-right">
          <span>喜欢</span>
          <span>(1)</span>
        </button>
        <button class="uk-button uk-button-text uk-margin-small-right">
          <span>不喜欢</span>
          <span>(1)</span>
        </button>
        <button class="uk-button uk-button-text uk-margin-small-right">
          <span>评论</span>
          <span>(1)</span>
        </button>
        <button class="uk-button uk-button-text uk-margin-small-right">
          <span>转发</span>
          <span>(1)</span>
        </button>
        <button class="uk-button uk-button-text uk-margin-right">
          <span>浏览</span>
          <span>(1)</span>
        </button>
      </div>
    </div>


    <div class="uk-card uk-card-default uk-margin-left uk-margin-right uk-margin-top">
      <div class="uk-card-header uk-padding-remove">
        <div class="uk-grid-small uk-flex-middle uk-margin-remove" uk-grid>
          <div>
            <img width="40" height="40" class="uk-border-circle" src="../assets/logo.png">
          </div>
          <div class="uk-width-expand">
            <h5 class="uk-card-title uk-margin-remove-bottom">Title</h5>
            <p class="uk-text-meta uk-margin-remove-top">
              <time datetime="2016-04-01T19:00">April 01, 2016</time>
            </p>
          </div>
        </div>
      </div>
      <div class="uk-card-body">
        <p>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididuntff4g4g4g542f3qffcwerfwrferfgwg.</p>
      </div>
      <div class="uk-card-footer uk-padding-remove">
        <a href="#" class="uk-button uk-button-text">Read more</a>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapState} from 'vuex'
  import {mapActions} from 'vuex'

  export default {
    name: 'MomentHome',
    data() {
      return {
        msg: 'Welcome to Ironman\'s world haha !'
      }
    },
    computed: {
      ...mapState({
        // 箭头函数可使代码更简练
        momentList: state => state.moment.momentList
        // 传字符串参数 'count' 等同于 `state => state.count`
        //    countAlias: 'count',
        //
        //     为了能够使用 `this` 获取局部状态，必须使用常规函数
        //    countPlusLocalState (state) {
        //      return state.count + this.localCount
        //    }
      })
    },
    created: function () {
      this.getRecommendMoments({id: 1})
    },
    methods: {
      ...mapActions([
        'getRecommendMoments', // 将 `this.increment()` 映射为 `this.$store.dispatch('increment')`

        // `mapActions` 也支持载荷, 将 `this.incrementBy(amount)` 映射为 `this.$store.dispatch('incrementBy', amount)`
      ])
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
