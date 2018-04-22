<template>
  <div class="uk-container">
    <div class=" uk-margin-top uk-flex">
      <ul uk-tab class=" uk-margin-remove uk-width-2-3">
        <li>
          <router-link v-bind:to="'/user/'+uniqueId+'/moment'">他的动态</router-link>
        </li>
        <li>
          <router-link v-bind:to="'/user/'+uniqueId+'/blog'">他的博客</router-link>
        </li>
        <li>
          <router-link v-bind:to="'/user/'+uniqueId+'/question' ">他的提问</router-link>
        </li>
      </ul>
    </div>
    <div>
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
  import {mapActions, mapState} from 'vuex'

  export default {
    name: 'UserMiddleHome',
    props: ['uniqueId'],
    data() {
      return {}
    },
    computed: {
      ...mapState({})
    },
    methods: {
      ...mapActions([]),
    },
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
