<template>
  <div>
    <self-page-nav></self-page-nav>

    <div class="uk-text-center">
      <span class="uk-label uk-label-success">我的动态</span>
    </div>
    <div v-for="myMoment in myMomentList">
      <moment-item v-bind:moment="myMoment"></moment-item>
    </div>
  </div>
</template>

<script>
  import {mapActions, mapState} from 'vuex'
  import MomentItem from "./MomentItem";
  import SelfPageNav from "./SelfPageNav";


  export default {
    components: {
      SelfPageNav,
      MomentItem
    },
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
