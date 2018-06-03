<template>
  <div class="uk-container">
    <advertise></advertise>
    <div uk-grid>
      <div class="uk-width-1-5">
        <!--<left-home></left-home>-->
      </div>
      <div class="uk-width-3-5 uk-padding-small">
        <ul uk-tab>
          <li>
            <router-link v-bind:to="{path:'/search',query:{keywords:keywords, type:2}}">博客</router-link>
          </li>
          <li>
            <router-link v-bind:to="{path:'/search',query:{keywords:keywords, type:4}}">用户</router-link>
          </li>
        </ul>
        <div v-if="searchResultList!=null&&searchResultList.length>0">
          <div v-if="type==2">
            <ul class="uk-comment-list">
              <li class="uk-margin-small-top" v-for="searchBlog in searchResultList">
                <search-blog-item v-bind:item="searchBlog"
                                  v-bind:key="searchBlog.uniqueId"></search-blog-item>
              </li>
            </ul>
          </div>

          <div v-if="type==4" class="uk-grid-small uk-child-width-1-2 uk-text-center" uk-grid>
            <div v-for="user in searchResultList">
              <user-card v-bind:user="user"></user-card>
            </div>
          </div>

        </div>
        <blank-icon v-else></blank-icon>
        <pageable v-if="isShowPageable" v-bind:fetchDataFunc="fetchSearchResult" size="5"></pageable>
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
  import Pageable from "../components/Pageable.vue";
  import UserCard from "../components/UserCard.vue";
  import BlankIcon from "../components/BlankIcon.vue";

  export default {
    components: {
      BlankIcon,
      UserCard,
      Pageable,
      advertise,
      leftHome,
      searchBlogItem,
    },
    data() {
      return {
        isShowPageable: true
      }
    },
    computed: {
      ...mapState({
        searchResultList: state => state.common.searchResultList
      }),
      keywords: function () {
        return this.$route.query.keywords
      },
      type: function () {
        return this.$route.query.type
      }
    },
    watch: {
      //重新挂载分页插件达到刷新页面数据效果
      keywords: function (newKeywords, oldKeywords) {
        this.isShowPageable = false
        this.$nextTick(() => {
          this.isShowPageable = true
        })
      },
      type: function (newType, oldType) {
        this.isShowPageable = false
        this.$nextTick(() => {
          this.isShowPageable = true
        })
      }
    },

    methods: {
      ...
        mapActions([
          'fetchSearchResultAction'
        ]),
      fetchSearchResult: function (pageParam) {
        return this.fetchSearchResultAction({
          keywords: this.keywords,
          type: this.type,
          page: pageParam.page,
          size: pageParam.size
        })
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
