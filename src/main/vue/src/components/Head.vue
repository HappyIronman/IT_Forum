<template>
  <nav class="uk-navbar-container" uk-navbar>
    <a class="uk-navbar-item uk-logo" href="#">Logo</a>

    <div class="uk-navbar-item">
      <form class="uk-form">
        <input class="uk-input uk-form-width-large" type="text" placeholder="Search...">
        <a uk-icon="search"></a>
      </form>
    </div>

    <div class="uk-navbar-right">
      <a class="uk-navbar-item">
        <span uk-icon="user"></span>
        <span>快乐少年小伟</span>
      </a>
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
  export default {
    name: 'Head',
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
