<template>
  <div class="uk-container">
    <advertise></advertise>
    <div uk-grid>
      <div class="uk-width-1-5">
        <left-home></left-home>
      </div>
      <div class="uk-width-3-5 uk-padding-remove">
        <div class="uk-padding">
          <ul class="uk-comment-list">
            <search-blog-item v-for="searchResult in searchResultList" v-bind:item="searchResult"
                              v-bind:key="searchResult.unniqueId"></search-blog-item>
          </ul>
        </div>
      </div>
    </div>
    <div class="uk-width-1-5 uk-padding-remove">
    </div>
  </div>
</template>
<script>
  import {mapActions, mapState} from 'vuex'
  import advertise from '../components/Advertise.vue'
  import leftHome from '../components/LeftHome.vue'
  import searchBlogItem from '../components/SearchBlogItem.vue'

  export default {
    components: {
      advertise,
      leftHome,
      searchBlogItem,
    },
    data() {
      return {}
    },
    computed: {
      ...mapState({
        searchResultList: state => state.common.searchResultList
      }),
      keywords: function () {
        return this.$route.params.keywords
      }
    },
    watch: {
      keywords: function (newKeywords, oldKeywords) {
        this.fetchSearchResultAction({keywords: newKeywords, type: 2, page: 0, size: 10})
      }
    },
    created: function () {
      //type暂时写死,为blog
      this.fetchSearchResultAction({keywords: this.keywords, type: 2, page: 0, size: 10})
    },
    methods: {
      ...
        mapActions([
          'fetchSearchResultAction'
        ])
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
