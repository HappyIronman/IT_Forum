<template>
  <div v-show="!isBottom" class="uk-text-center uk-margin-left uk-margin-right" v-on:click="this.fetchItemList">
    <button class="uk-button uk-button-text uk-width-1-1">
      <span class="uk-text-success">点击加载更多...</span>
    </button>
  </div>
</template>

<script>

  export default {
    name: 'Pageable',
    props: {
      fetchDataFunc: Function,
      size: [String, Number]
    },
    data() {
      return {
        page: 0,
        isBottom: false
      }
    },
    created: function () {
      this.fetchItemList()
    },
    methods: {
      fetchItemList: function () {
        this.fetchDataFunc({page: this.page, size: this.size}).then((res) => {
          this.page++
          if (res) {
            this.isBottom = false
          } else {
            this.isBottom = true
          }
        })
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
