<template>
  <div>
    <div class="uk-card uk-card-default uk-margin-left uk-margin-right uk-margin-top">
      <div class="uk-card-header uk-padding-remove">
        <div class="uk-grid-small uk-flex-middle uk-margin-remove" uk-grid>
          <div>
            <img class="uk-border-circle" style="width: 40px;height: 40px" v-bind:src="question.profileUrl">
          </div>
          <div class="uk-width-auto">
            <h5 class="uk-card-title uk-margin-remove-bottom">
              <router-link v-bind:to="'/user/'+question.userId">{{question.username}}</router-link>
            </h5>
            <p class="uk-text-meta uk-margin-remove-top">
              {{question.createTime | formatDate('yyyy-MM-dd hh:mm')}}
            </p>
          </div>
          <div class="uk-width-expand uk-text-right uk-text-small">
            <p class="uk-margin-right">发表了提问</p>
          </div>
        </div>
      </div>
      <div class="uk-card-body uk-padding-small">
        <p class="uk-margin-small">
          <router-link class="uk-text-bold" v-bind:to="'/self/my_question/' + question.uniqueId">
            {{question.title}}
          </router-link>
        </p>
        <div class="uk-text-small uk-text-muted">
          <p class="uk-margin-small-bottom" v-if="question.content.length <= 80">{{question.content}}</p>
          <div v-if="question.content.length > 80">
            <p class="uk-margin-small-bottom">
              {{isDisplayAbstractContent ? (absContent + '  ...' ) : question.content}}
            </p>
            <div class="uk-text-right">
              <button class="uk-button uk-button-text uk-text-muted"
                      v-on:click="isDisplayAbstractContent=!isDisplayAbstractContent">
                <span v-if="isDisplayAbstractContent">展开全文</span>
                <span v-if="!isDisplayAbstractContent">收起</span>
              </button>
            </div>
          </div>
        </div>
      </div>
      <div class="uk-card-footer uk-padding-remove uk-text-right">
        <router-link class="uk-text-small uk-margin-right" v-bind:to="'/self/my_question/' + question.uniqueId">
          <span>回答</span>
          <span>({{question.commentNum}})</span>
        </router-link>
        <span class="uk-text-small uk-margin-right">
          <span>浏览</span>
          <span>({{question.viewNum}})</span>
        </span>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapActions} from 'vuex'
  import {requestApi} from '../api/requestUtils'

  export default {
    name: 'QuestionItem',
    props: ['question'],
    data() {
      return {
        //是否显示的是简略信息
        isDisplayAbstractContent: true
      }
    },
    computed: {
      absContent: function () {
        return this.question.content.slice(0, 80)
      }
    },

    methods: {}
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
