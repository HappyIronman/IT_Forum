<template>
  <div>
    <div class="uk-text-center">
      <span class="uk-label uk-label-success">我的朋友圈</span>
    </div>
    <div v-if="circleList!=null && circleList.length>0">
      <div v-for="item in circleList">
        <moment-item v-if="item.type===1" v-bind:moment="item.entity"></moment-item>
        <blog-item v-if="item.type===2" v-bind:blog="item.entity"></blog-item>
        <user-question-item v-if="item.type===3" v-bind:question="item.entity"></user-question-item>
      </div>
    </div>
    <blank-icon v-else></blank-icon>
    <pageable v-bind:fetch-data-func="fetchMyCircleListAction" size="5"></pageable>


  </div>
</template>

<script>
  import {mapActions, mapState} from 'vuex'
  import MomentItem from './MomentItem'
  import BlogItem from "./BlogItem";
  import Pageable from "./Pageable.vue";
  import UserQuestionItem from "./UserQuestionItem.vue";
  import BlankIcon from "./BlankIcon.vue";

  export default {
    components: {
      BlankIcon,
      UserQuestionItem,
      Pageable,
      BlogItem,
      MomentItem
    },
    name: 'FriendCircle',
    data() {
      return {}
    },
    computed: {
      ...mapState({
        circleList: state => state.moment.circleList,
      })
    },

    methods: {
      ...mapActions([
        'fetchMyCircleListAction'// �� `this.increment()` ӳ��Ϊ `this.$store.dispatch('increment')`
      ]),

    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
