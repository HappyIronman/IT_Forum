<template>
  <div class="uk-grid uk-child-width-1-3">
    <div class="uk-card uk-text-center">
      <button class="uk-button-primary" v-on:click="userLogin('ironman123','123456')">
        ironman123
      </button>
    </div>
    <div class="uk-card uk-text-center">
      <button class="uk-button-primary" v-on:click="userLogin('ironman124','123456')">
        ironman124
      </button>
    </div>
    <div class="uk-card uk-text-center">
      <button class="uk-button-primary" v-on:click="userLogin('ironman125','123456')">
        ironman125
      </button>
    </div>
    <div class="uk-card uk-text-center">
      <button class="uk-button-primary" v-on:click="userLogin('ironman126','123456')">
        ironman126
      </button>
    </div>
    <div class="uk-card uk-text-center">
      <button class="uk-button-primary" v-on:click="userLogin('ironman127','123456')">
        ironman127
      </button>
    </div>
  </div>
</template>

<script>
  import {mapActions} from 'vuex'

  export default {
    name: 'LoginPage',
    data() {
      return {
      }
    },
    methods: {
      ...mapActions([
        'userLoginAction'
      ]),
      userLogin: function (username, password) {
        this.userLoginAction({'username': username, 'password': password}).then(
          (result) => {
            if (result) {
              this.$router.push('/friend_circle')
            }
          }
        )

      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
