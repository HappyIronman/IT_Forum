<template>
  <div class="uk-margin-top uk-margin-right">
    <div v-if="followList!=null&& followList.length>0" class="uk-grid-small uk-child-width-1-3 uk-text-center" uk-grid>
      <div v-for="user in followList">
        <user-card v-bind:user="user"></user-card>
      </div>
    </div>
    <blank-icon v-else></blank-icon>
    <pageable v-bind:fetchDataFunc="fetchMyFollowingListAction" size="5"></pageable>
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
    name: 'MyFollowing',
    data() {
      return {}
    },
    computed: {
      ...mapState({
        followList: state => state.user.followList
      })
    },
    methods: {
      ...mapActions([
        'fetchMyFollowingListAction'
      ])
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
