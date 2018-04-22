<template>
  <div>
    <advertise></advertise>
    <div class="uk-width-3-4 uk-align-center uk-padding-small uk-margin-small-top uk-border-rounded"
         style="border:2px solid darkgrey">
      <div>
        <input class="uk-input uk-width-2-3 uk-align-center" type="text" placeholder="在此输入标题" v-model="blog.title">
      </div>
      <div id="editor-bar" style=" border: 1px solid #ccc;">
      </div>
      <!--<div style="padding: 5px 0; color: #ccc">中间隔离带</div>-->
      <div id="editor" style="border: 1px solid #ccc; min-height: 800px"> <!--可使用 min-height 实现编辑区域自动增加高度-->
      </div>
      <div class="uk-grid uk-width-1-1 uk-margin-remove-left uk-margin-small-top">
        <div class="uk-card uk-padding-remove">
          <div uk-form-custom="target: > * > span:last-child">
            <select v-model="blog.isPrivate">
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
          <router-link class="uk-button uk-button-default uk-button-small"
                       to="/">取消
          </router-link>
          <button class="uk-button uk-button-primary uk-button-small"
                  v-on:click="publishBlog">发表
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import WangEditor from '../../static/js/wangEditor'
  import {mapActions, mapState} from 'vuex'
  import advertise from '../components/Advertise.vue'

  export default {
    name: 'WriteBlogPage',
    components: {
      advertise
    },
    data() {
      return {
        blog: {
          title: '',
          content: '',
          isPrivate: false,
          isShare: false
        }
      }
    },
    computed: {
      ...mapState({
        // isShowPublishMoment: state => state.moment.isShowPublishMoment
      })
    },
    methods: {
      ...mapActions([
        'publishBlogAction'
      ]),
      publishBlog: function () {
        this.publishBlogAction(this.blog).then(uniqueId => {
          console.log(uniqueId)
          if (uniqueId) {
            this.$router.push({path: `/self/my_blog/${uniqueId}`}) // -> /blog/123
          }
        })
      }
    },
    mounted: function () {
      var editor = new WangEditor('#editor-bar', '#editor')  //这个地方传入div元素的id 需要加#号
      editor.customConfig.onchange = (html) => {
        console.log(html)
        this.blog.content = html
      }
      editor.create()     // 生成编辑器
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
