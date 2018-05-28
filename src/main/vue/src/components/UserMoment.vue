<template>
  <div>
    <div class="uk-text-center">
      <span class="uk-label uk-label-success">ta的动态</span>
    </div>
    <div v-if="userMomentList!=null&&userMomentList.length>0">
      <div v-for="moment in userMomentList">
        <moment-item v-bind:moment="moment"></moment-item>
      </div>
    </div>
    <blank-icon v-else></blank-icon>
    <pageable v-bind:fetch-data-func="fetchUserMomentList" size="5"></pageable>
  </div>
</template>

<script>
  import {mapActions, mapState} from 'vuex'
  import MomentItem from "./MomentItem";
  import Pageable from "./Pageable.vue";
  import BlankIcon from "./BlankIcon.vue";


  export default {
    components: {
      BlankIcon,
      Pageable,
      MomentItem
    },
    name: 'UserMoment',
    data() {
      return {}
    },
    computed: {
      ...mapState({
        userMomentList: state => state.moment.userMomentList
      })
    },
    methods: {
      ...mapActions([
        'fetchUserMomentListAction'
      ]),
      fetchUserMomentList: function (pageParam) {
        return this.fetchUserMomentListAction({uniqueId: this.$route.params.uniqueId, pageParam: pageParam})
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
