<template>
  <div>
    <self-page-nav index="2"></self-page-nav>

    <div class="uk-text-center">
      <span class="uk-label uk-label-success">我的提问</span>
    </div>
    <div v-if="myQuestionList!=null&&myQuestionList.length>0">
      <div v-for="myQuestion in myQuestionList">
        <my-question-item v-bind:question="myQuestion"></my-question-item>
      </div>
    </div>
    <blank-icon v-else></blank-icon>
    <pageable v-bind:fetch-data-func="fetchMyQuestionListAction" size="5"></pageable>
  </div>
</template>

<script>
  import {mapActions, mapState} from 'vuex'
  import SelfPageNav from "./SelfPageNav.vue";
  import Pageable from "./Pageable.vue";
  import MyQuestionItem from "./MyQuestionItem.vue";
  import BlankIcon from "./BlankIcon.vue";

  export default {
    components: {
      BlankIcon,
      MyQuestionItem,
      Pageable,
      SelfPageNav
    },
    name: 'MyQuestion',
    data() {
      return {
        msg: 'Welcome to Ironman\'s world haha !'
      }
    },
    computed: {
      ...mapState({
        myQuestionList: state => state.question.myQuestionList
      })
    },
    methods: {
      ...mapActions([
        'fetchMyQuestionListAction'
      ])
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
