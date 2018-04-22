<template>
  <div>
    <div>
      <ul class="uk-tab">
        <li class="uk-margin-large-right uk-margin-large-left">
          <a v-on:click="onclickItem(0)" v-bind:style="tabStyle(0)">我的动态</a>
        </li>
        <li class="uk-margin-large-right">
          <a v-on:click="onclickItem(1)" v-bind:style="tabStyle(1)">我的博客</a>
        </li>
        <li>
          <a v-on:click="onclickItem(2)" v-bind:style="tabStyle(2)">我的提问</a>
        </li>
      </ul>
    </div>
  </div>

</template>

<script>
  export default {
    name: 'SelfPageNav',
    props:['index'],
    data() {
      return {
      }
    },
    computed: {

    },
    methods: {
      onclickItem: function (index) {
        if (index === 0) {
          this.$router.push({path: `/self/my_moment`})
        } else if (index === 1) {
          this.$router.push({path: `/self/my_blog`})
        } else if (index === 2) {
          this.$router.push({path: `/self/my_question`})
        }
      },
      tabStyle: function (index) {
        return {
          'color': parseInt(this.index) === index ? 'blue' : 'black',
          'border-color': parseInt(this.index) === index ? 'blue' : 'black'
        }
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
