<template>
  <div>
    <advertise></advertise>
    <div class="uk-text-center">
      <span class="uk-label uk-label-success">发布问题</span>
    </div>
    <div class="uk-width-3-4 uk-align-center uk-padding-small uk-margin-small-top uk-border-rounded">
      <div>
        <input class="uk-input uk-width-2-3 uk-align-center" type="text" placeholder="在此输入问题" v-model="question.title">
      </div>
      <div id="editor-bar" style=" border: 1px solid #ccc;">
      </div>
      <div style="padding: 5px 0; color: #ccc" class="uk-text-meta">请输入问题具体描述(非必须,不多于200字符)</div>
      <div id="editor" style="border: 1px solid #ccc; min-height: 300px">
      </div>
      <div class="uk-grid uk-width-1-1 uk-margin-remove-left uk-margin-small-top">

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
    name: 'WriteQuestionPage',
    components: {
      advertise
    },
    data() {
      return {
        question: {
          title: '',
          content: ''
        }
      }
    },
    computed: {
      ...mapState({})
    },
    methods: {
      ...mapActions([
        'publishQuestionAction'
      ]),
      publishBlog: function () {
        this.publishQuestionAction(this.question).then((res) => {
          if (res) {
            this.$router.push({path: `/self/my_question`}) // -> /blog/123
          }
        })
      }
    },
    mounted: function () {
      var editor = new WangEditor('#editor-bar', '#editor')  //这个地方传入div元素的id 需要加#号
      editor.customConfig.onchange = (html) => {
        console.log(html)
        this.question.content = html
      }
      editor.create()     // 生成编辑器
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
