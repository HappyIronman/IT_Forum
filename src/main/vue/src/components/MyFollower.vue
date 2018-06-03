<template>
  <div class="uk-margin-top uk-margin-right">
    <div v-if="followerList!=null && followerList.length>0" class="uk-grid-small uk-child-width-1-3 uk-text-center" uk-grid>
      <div v-for="user in followerList">
        <user-card v-bind:user="user"></user-card>
      </div>
    </div>
    <blank-icon v-else></blank-icon>
    <pageable v-bind:fetchDataFunc="fetchMyFollowerListAction" size="6"></pageable>
  </div>
</template>

<script>
  import {mapActions, mapState} from 'vuex'
  import Pageable from "./Pageable.vue";
  import UserCard from "./UserCard.vue";
  import BlankIcon from "./BlankIcon.vue";

  export default {
    components: {
      BlankIcon,
      UserCard,
      Pageable
    },
    name: 'MyFollower',
    data() {
      return {}
    },
    computed: {
      ...mapState({
        followerList: state => state.user.followerList
      })
    },
    methods: {
      ...mapActions([
        'fetchMyFollowerListAction'
      ])
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
