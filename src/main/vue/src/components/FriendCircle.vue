<template>
  <div>
    <div class="uk-text-center">
      <span class="uk-label uk-label-success">我的朋友圈</span>
    </div>
    <div v-for="item in circleList">
      <moment-item v-if="item.type===1" v-bind:moment="item.entity"></moment-item>
      <blog-item v-if="item.type===2" v-bind:blog="item.entity"></blog-item>
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
      return {}
    },
    computed: {
      ...mapState({
        circleList: state => state.moment.circleList
      })
    },
    created: function () {
      this.fetchMyCircleListAction({page: 0, size: 10})
    },
    methods: {
      ...mapActions([
        'fetchMyCircleListAction'// �� `this.increment()` ӳ��Ϊ `this.$store.dispatch('increment')`
      ])
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
