<template>
  <div>
    <div class="uk-text-center">
      <span class="uk-label uk-label-success">ta的动态</span>
    </div>
    <div v-for="moment in userMomentList">
      <moment-item v-bind:moment="moment"></moment-item>
    </div>
  </div>
</template>

<script>
  import {mapActions, mapState} from 'vuex'
  import MomentItem from "./MomentItem";


  export default {
    components: {MomentItem},
    name: 'UserMoment',
    data() {
      return {}
    },
    computed: {
      ...mapState({
        userMomentList: state => state.moment.userMomentList
      })
    },
    created: function () {
      console.log(this.$route.params.uniqueId)
      this.fetchUserMomentListAction({uniqueId: this.$route.params.uniqueId, pageParam: {page: 0, size: 10}})
    },
    methods: {
      ...mapActions([
        'fetchUserMomentListAction'
      ])
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
