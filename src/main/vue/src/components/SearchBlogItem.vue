<template>
  <div class="uk-margin-top uk-padding">
    <ul class="uk-comment-list">
      <li class="uk-margin-small-top">
        <article class="uk-comment uk-comment-primary uk-visible-toggle">
          <header class="uk-comment-header uk-position-relative">
            <div class="uk-grid-medium uk-flex-middle" uk-grid>
              <div class="uk-width-expand">
                <h4 class="uk-comment-title uk-margin-remove"><a class="uk-link-reset" href="#">vue学习</a></h4>
                <p class="uk-comment-meta uk-margin-remove-top"><a class="uk-link-reset" href="#">Ironman123</a></p>
              </div>
            </div>
            <div class="uk-position-top-right uk-position-small uk-margin-remove">
              <span class="uk-text-muted uk-text-small">赞1</span>
              <span class="uk-text-muted uk-text-small">评论2</span>
            </div>
            <div class="uk-position-bottom-right uk-position-small uk-margin-remove">
              <span class="uk-text-muted uk-text-small">2007-01-02</span>
            </div>
          </header>
          <div class="uk-comment-body">
            <p>Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore
              et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum.
              Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.</p>
          </div>
        </article>
      </li>

      <li class="uk-margin-small-top">
        <article class="uk-comment uk-comment-primary uk-visible-toggle">
          <header class="uk-comment-header uk-position-relative">
            <div class="uk-grid-medium uk-flex-middle" uk-grid>
              <div class="uk-width-expand">
                <h4 class="uk-comment-title uk-margin-remove"><a class="uk-link-reset" href="#">vue学习</a></h4>
                <p class="uk-comment-meta uk-margin-remove-top"><a class="uk-link-reset" href="#">Ironman123</a></p>
              </div>
            </div>
            <div class="uk-position-bottom-right uk-position-small"><span
              class="uk-text-muted uk-text-small">2007-01-02</span></div>
          </header>
          <div class="uk-comment-body">
            <p>Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore
              et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum.
              Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.</p>
          </div>
        </article>
      </li>
    </ul>
  </div>
</template>

<script>
  export default {
    name: 'SearchBlogItem',
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
