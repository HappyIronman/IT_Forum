<template>
  <div class="uk-container">
    <nav class="uk-navbar-container" uk-navbar>
      <div class="uk-navbar-left">
        <ul class="uk-navbar-nav">
          <li class="uk-active"><a href="">关注</a></li>
          <li>
            <router-link to="/moment">动态</router-link>
          </li>
          <li>
            <router-link to="/blog">博客</router-link>
          </li>
          <li>
            <router-link to="/question">提问</router-link>
          </li>
          <li>
            <router-link to="/aboutme">关于我</router-link>
          </li>
        </ul>
      </div>
    </nav>
    <div>
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'LeftHome',
    data() {
      return {
        msg: 'Welcome to Ironman\'s world haha !'
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
