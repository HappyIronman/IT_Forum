<template>
  <div>
    <div class="uk-card uk-card-default uk-margin-left uk-margin-right uk-margin-top">
      <div class="uk-card-header uk-padding-remove">
        <div class="uk-grid-small uk-flex-middle uk-margin-remove" uk-grid>
          <div>
            <img class="uk-border-circle" width="40" height="40" src="../assets/logo.png">
          </div>
          <div class="uk-width-auto">
            <h5 class="uk-card-title uk-margin-remove-bottom">{{blog.username}}</h5>
            <p class="uk-text-meta uk-margin-remove-top">
              {{blog.createTime | formatDate('yyyy-MM-dd hh:mm')}}
            </p>
          </div>
          <div class="uk-width-expand uk-text-right uk-text-small">
            <p v-if="blog.share" class="uk-margin-right">转发了
              <span class="uk-text-success">{{blog.originUsername}}</span> 的博客
            </p>
            <p v-if="!blog.share" class="uk-margin-right">发表了新博客</p>
          </div>
        </div>
      </div>
      <div class="uk-card-body uk-padding-small">
        <div v-if="!blog.share">
          <p class="uk-margin-small">{{blog.title}}</p>
          <div class="uk-text-small uk-text-muted">
            <p class="uk-margin-small" v-html="blog.content"></p>
            <span v-if="blog.abstract">......</span>
          </div>
        </div>
        <div v-if="blog.share">
          <p class="uk-margin-small uk-text-bold">转发理由</p>
          <div class="uk-text-small uk-text-muted">
            <p class="uk-margin-small" v-html="blog.content"></p>
            <span v-if="blog.abstract">......</span>
          </div>
          <hr class="uk-divider-icon uk-margin-small">
          <p class="uk-margin-small uk-text-bold">原博文:</p>
          <p class="uk-margin-small" v-html="blog.originContent"></p>
          <span v-if="blog.abstract">......</span>
          <p class="uk-margin-remove uk-text-small  uk-text-muted uk-text-right">
            发表于{{blog.originCreateTime | formatDate('yyyy-MM-dd hh:mm')}}
          </p>
        </div>
      </div>
      <div class="uk-card-footer uk-padding-remove uk-text-right">
        <button class="uk-button uk-button-text uk-margin-small-right">
          <span>喜欢</span>
          <span>({{blog.likeNum}})</span>
        </button>
        <button class="uk-button uk-button-text uk-margin-small-right">
          <span>不喜欢</span>
          <span>({{blog.dislikeNum}})</span>
        </button>
        <button class="uk-button uk-button-text uk-margin-small-right">
          <span>评论</span>
          <span>({{blog.commentNum}})</span>
        </button>
        <button class="uk-button uk-button-text uk-margin-small-right">
          <span>转发</span>
          <span>({{blog.shareNum}})</span>
        </button>
        <button class="uk-button uk-button-text uk-margin-right">
          <span>浏览</span>
          <span>({{blog.viewNum}})</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script>

  export default {
    name: 'blogItem',
    props: ['blog'],
    data() {
      return {}
    },
    methods: {}
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
