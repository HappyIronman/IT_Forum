<template>
  <div>
    <div class="uk-text-center">
      <span class="uk-label uk-label-success">转发博客</span>
    </div>
    <div class="uk-width-3-4 uk-align-center uk-padding-small uk-margin-small-top uk-border-rounded"
         style="border:2px solid darkgrey">
      <div>
        <input class="uk-input uk-width-2-3 uk-align-center" type="text" placeholder="在此输入标题" v-model="blog.title">
      </div>
      <div>
        <div id="editor-bar" style=" border: 1px solid #ccc;">
        </div>
        <!--<div style="padding: 5px 0; color: #ccc">中间隔离带</div>-->
        <div id="editor" style="border: 1px solid #ccc; min-height: 400px"> <!--可使用 min-height 实现编辑区域自动增加高度-->
          <p>请输入分享理由</p>
        </div>
        <div id="origin_blog_content" class="uk-margin-small-top">
          <hr class="uk-divider-icon uk-margin-small">
          <p class="uk-text-success uk-text-bold">原博文</p>
          <p class="uk-text-lead">{{blogDetail.title}}</p>
          <div>
            <p class="uk-text-muted" v-html="blogDetail.content">
            </p>
          </div>
          <p class="uk-text-muted uk-text-small uk-text-right">
            原作者: {{blogDetail.username}} <br/> 创建于 {{blogDetail.createTime | formatDate('yyyy-MM-dd hh:mm')}}
          </p>
          <hr class="uk-hr uk-margin-small">
        </div>
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

  export default {
    name: 'ShareBlogPage',
    data() {
      return {
        blog: {
          title: '',
          content: '',
          isPrivate: false,
          isShare: true,
          originId: this.$route.params.uniqueId
        }
      }
    },
    computed: {
      ...mapState({
        blogDetail: state => state.blog.blogDetail
      })
    },
    created: function () {
      this.fetchBlogDetailAction(this.$route.params.uniqueId)
    },
    methods: {
      ...mapActions([
        'publishBlogAction',
        'fetchBlogDetailAction'
      ]),
      publishBlog: function () {
        this.publishBlogAction(this.blog).then(uniqueId => {
          console.log(uniqueId)
          if (uniqueId) {
            this.$router.push({path: `/blog/${uniqueId}`}) // -> /blog/123
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
