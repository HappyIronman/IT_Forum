<template>
  <div>
    <div class="uk-width-3-4 uk-align-center uk-padding-small uk-margin-small-top uk-border-rounded"
         style="border:2px solid darkgrey">
      <div>
        <input class="uk-input uk-width-2-3 uk-align-center" type="text" placeholder="在此输入标题">
      </div>
      <div id="editor"></div>
      <div class="uk-grid uk-width-1-1 uk-margin-remove-left uk-margin-small-top">
        <div class="uk-card uk-padding-remove">
          <div uk-form-custom="target: > * > span:last-child">
            <select>
              <option value="false">公开</option>
              <option value="true">私人</option>
            </select>
            <span class="uk-link">
                <span uk-icon="icon: unlock"></span>
                <span></span>
              </span>
          </div>
        </div>
        <div class="uk-card uk-width-expand uk-text-right">
          <button class="uk-button uk-button-default uk-button-small"
                  v-on:click="">取消
          </button>
          <button class="uk-button uk-button-primary uk-button-small"
                  v-on:click="">发表
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import WangEditor from '../../static/js/wangEditor'

  export default {
    name: 'WriteBlogPage',
    data() {
      return {
        msg: 'Welcome to Ironman\'s world haha !'
      }
    },
    mounted: function () {
//      let that = this
      var editor = new WangEditor('#editor')  //这个地方传入div元素的id 需要加#号
      editor.customConfig.onchange = (html) => {
        console.log(html)
      }
      editor.create()     // 生成编辑器
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
