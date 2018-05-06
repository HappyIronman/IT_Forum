<template>
  <article class="uk-comment uk-comment-primary uk-visible-toggle">
    <header class="uk-comment-header uk-position-relative">
      <div class="uk-grid-medium uk-flex-middle" uk-grid>
        <div class="uk-width-expand">
          <h4 class="uk-comment-title uk-margin-remove">
            <router-link class="uk-text-bold" v-bind:to="'/user/'+item.userUniqueId+'/blog/'+item.uniqueId">
              {{item.title}}
            </router-link>
          </h4>
          <p class="uk-comment-meta uk-margin-remove-top"><a class="uk-link-reset" href="#">{{item.username}}</a>
          </p>
        </div>
      </div>
      <div class="uk-position-top-right uk-position-small uk-margin-remove">
        <span class="uk-text-muted uk-text-small">喜欢:{{item.likeNum}}</span>
        <span class="uk-text-muted uk-text-small">评论:{{item.commentNum}}</span>
      </div>
      <div class="uk-position-bottom-right uk-position-small uk-margin-remove">
        <span class="uk-text-muted uk-text-small">{{item.createTime | formatDate('yyyy-MM-dd hh:mm')}}</span>
      </div>
    </header>
    <div class="uk-comment-body">
      <p v-html="item.content"></p>
    </div>
  </article>


</template>

<script>
  export default {
    name: 'SearchBlogItem',
    props: ['item'],
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
