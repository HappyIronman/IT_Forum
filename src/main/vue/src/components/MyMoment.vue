<template>
  <div>
    <self-page-nav index="0"></self-page-nav>

    <div class="uk-text-center">
      <span class="uk-label uk-label-success">我的动态</span>
    </div>
    <div v-if="myMomentList!=null && myMomentList.length>0">
      <div v-for="myMoment in myMomentList">
        <moment-item v-bind:moment="myMoment"></moment-item>
      </div>
    </div>
    <blank-icon v-else></blank-icon>
    <pageable v-bind:fetch-data-func="fetchMyMomentListAction" size="5"></pageable>


  </div>
</template>

<script>
  import {mapActions, mapState} from 'vuex'
  import MomentItem from "./MomentItem";
  import SelfPageNav from "./SelfPageNav";
  import Pageable from "./Pageable.vue";
  import BlankIcon from "./BlankIcon.vue";


  export default {
    components: {
      BlankIcon,
      Pageable,
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
