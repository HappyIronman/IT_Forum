<template>
  <div class="uk-margin-top uk-margin-right">
    <div class="uk-grid-small uk-child-width-1-3 uk-text-center" uk-grid>
      <div v-for="i in [1,2,3,4,5,6]">
        <div
          class="uk-card uk-card-default uk-card-body uk-card-small uk-flex uk-padding-remove-left uk-padding-remove-right"
          style="height: 160px">
          <div class="uk-width-1-4" style=" border-right:1px solid darkgrey">
            <img class="uk-border-circle uk-margin-top"
                 src="../../static/img/ironman.jpeg" style="width: 60px;height: 60px;"/>
          </div>
          <div class="uk-width-expand uk-text-left uk-margin-small-left">
            <p class=" uk-margin-small-bottom">快乐少年小纬</p>
            <p class="uk-text-meta uk-margin-remove">已关注</p>
            <div class="uk-flex uk-text-meta uk-margin-small-top">
              <p class="uk-margin-remove" style="padding-right: 4px; border-right:1px solid darkgrey">关注&nbsp;35</p>
              <p class="uk-margin-remove" style="padding-left: 4px">粉丝&nbsp;35</p>
            </div>
            <div class="uk-flex uk-text-meta uk-margin-small-top">
              <p class="uk-margin-remove" style="padding-right: 4px; border-right:1px solid darkgrey">动态&nbsp;35</p>
              <p class="uk-margin-remove" style="padding: 0 4px; border-right:1px solid darkgrey">博客&nbsp;35</p>
              <p class="uk-margin-remove" style="padding-left: 4px">提问&nbsp;35</p>
            </div>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
  export default {
    name: 'MyFollowing',
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
