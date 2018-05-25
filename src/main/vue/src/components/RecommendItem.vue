<template>
  <div>
    <div class="uk-card uk-card-default uk-margin-left uk-margin-right uk-margin-top">
      <div class="uk-card-header uk-padding-remove">
        <div class="uk-grid-small uk-flex-middle uk-margin-remove" uk-grid>
          <div class="uk-width-auto">
            <p class="uk-text-large uk-margin-remove-bottom">
              <router-link v-if="blog.type==='LOCAL'" target="_blank" v-bind:to="blog.url" v-html="blog.title"></router-link>
              <a v-if="blog.type==='EXTERNAL'" target="_blank" v-bind:href="blog.url" v-html="blog.title"></a>
            </p>
            <p class="uk-text-meta uk-margin-remove-top">
              {{blog.pubDate}}
            </p>
          </div>
          <div class="uk-width-expand uk-text-right uk-text-small" v-if="blog.author!=null">
            <p class="uk-margin-right">作者&nbsp;&nbsp;
              <span class="uk-text-bold">
                <router-link v-if="blog.authorId!=null" target="_blank" v-bind:to="'/user/'+blog.authorId">
                  {{blog.author}}
                </router-link>

                <span v-if="blog.authorId==null">
                  {{blog.author}}
                </span>

              </span>
            </p>
          </div>
        </div>
      </div>
      <div class="uk-card-body uk-padding-small">
        <div class="uk-text-small uk-text-muted">
          <p class="uk-margin-small" v-html="blog.content">
            <span v-if="blog.type==='LOCAL'">…</span>
          </p>
        </div>
      </div>
      <div class="uk-card-footer uk-padding-remove uk-text-right uk-text-small uk-text-muted">
        <span class="uk-margin-right">
        <span class="uk-text-bold">来源:{{blog.originSite}}</span>
        <span v-if="blog.type==='EXTERNAL'">
          &nbsp;&nbsp;<em>头条</em>
        </span>
        </span>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapActions} from 'vuex'
  import MomentLikeBtn from "./MomentLikeBtn.vue";
  import CommentList from "./CommentList.vue";
  import {requestApi} from "../api/requestUtils";

  export default {
    props: ['blog'],
    name: 'recommendItem',
    data() {
      return {}
    },
    methods: {
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
