<template>
  <div>
    <div class="uk-text-center">
      <span class="uk-label uk-label-success">我的朋友圈</span>
    </div>
    <div v-for="item in circleList">
      <moment-item v-if="item.type===1" v-bind:moment="item.entity"></moment-item>
      <blog-item v-if="item.type===2" v-bind:blog="item.entity"></blog-item>
    </div>

    <div v-show="!isBottom" class="uk-text-center uk-margin-left uk-margin-right" v-on:click="this.fetchMyCircleList">
      <button class="uk-button uk-button-text uk-width-1-1">
        <span class="uk-text-success">点击加载更多...</span>
      </button>
    </div>
  </div>
</template>

<script>
  import {mapActions, mapState} from 'vuex'
  import MomentItem from './MomentItem'
  import BlogItem from "./BlogItem";

  export default {
    components: {
      BlogItem,
      MomentItem
    },
    name: 'FriendCircle',
    data() {
      return {
        page: 0,
        size: 5,
        isBottom: true
      }
    },
    computed: {
      ...mapState({
        circleList: state => state.moment.circleList,
      })
    },
    created: function () {
      if (this.circleList == null || this.circleList.length === 0) {
        this.fetchMyCircleList()
      }
    },
    methods: {
      ...mapActions([
        'fetchMyCircleListAction'// �� `this.increment()` ӳ��Ϊ `this.$store.dispatch('increment')`
      ]),
      fetchMyCircleList: function () {
        this.fetchMyCircleListAction({page: this.page, size: this.size}).then(res => {
            console.log(res)
            if (res) {
              this.page++
              this.isBottom = false
            } else {
              this.isBottom = true
            }
          }
        )
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
