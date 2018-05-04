<template>
  <nav class="uk-navbar-container" uk-navbar>
    <router-link class="uk-navbar-item uk-logo" to="/">
      <img src="../../static/img/logo.png" style="height: 46px;width:46px;"/>
    </router-link>

    <div class="uk-navbar-item">
      <form class="uk-form">
        <input class="uk-input uk-form-width-large" type="text" placeholder="Search..." v-model="keywords">
        <a uk-icon="search" v-on:click="search"></a>
      </form>
    </div>

    <div class="uk-navbar-right">
      <a class="uk-navbar-item">
        <span uk-icon="user"></span>
        <span v-if="loginUserInfo&&loginUserInfo.username">
          {{loginUserInfo.username}}
        </span>
        <span v-if="!loginUserInfo||!loginUserInfo.username">
          未登录
        </span>
      </a>
      <div v-if="loginUserInfo&&loginUserInfo.username"
           uk-dropdown="animation: uk-animation-slide-top-small; duration: 500; pos: bottom-justify">
        <ul class="uk-nav uk-dropdown-nav">
          <li class="uk-nav-header">
            <router-link to="/">我的主页</router-link>
          </li>
          <li class="uk-nav-header">
            <a href="javascript:void(0);" v-on:click="logout">退出登录</a>
          </li>
        </ul>
      </div>

      <div v-if="!loginUserInfo||!loginUserInfo.username"
           uk-dropdown="animation: uk-animation-slide-top-small; duration: 1000; pos: bottom-justify">
        <ul class="uk-nav uk-dropdown-nav">
          <li class="uk-nav-header">
            <router-link v-bind:to="{ path: '/login', query: { referrer: $route.path }}">登录</router-link>
          </li>
        </ul>
      </div>
      <a class="uk-navbar-item">
        <span uk-icon="commenting"></span>
      </a>
      <a class="uk-navbar-item">
        <span uk-icon="cog"></span>
      </a>
    </div>
  </nav>
</template>

<script>
  import {mapActions, mapState} from 'vuex'

  export default {
    name: 'Head',
    data() {
      return {
        keywords: null
      }
    },
    computed: {
      ...mapState({
        loginUserInfo: state => state.user.loginUserInfo
      })
    },
    methods: {
      ...mapActions([
        'userLogoutAction'
      ]),
      search: function () {
        this.$router.push({path: `/search/` + this.keywords})
      },
      logout: function () {
        this.userLogoutAction()
        this.$router.push('/login/')
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .uk-navbar-item {
    height: 60px;
  }
</style>
