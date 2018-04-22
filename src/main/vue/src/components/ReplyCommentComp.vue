<template>
  <div>
    <div class="uk-margin-small-top uk-margin-small-bottom uk-margin-small-left uk-margin-small-right">
      <textarea v-model="commentPublishForm.content"
                class="uk-textarea" v-bind:rows="rows?rows:3" placeholder="说点想说的...">
      </textarea>
    </div>
    <div class=" uk-text-right" style="margin-top: 4px">
      <button class="uk-button uk-button-primary uk-button-small"
              v-on:click="publishComment">发表
      </button>
    </div>
  </div>
</template>

<script>
  import {requestApi} from '../api/requestUtils'

  export default {
    name: 'ReplyCommentComp',
    props: ['replyId', 'type', 'rows'],
    data() {
      return {
        commentPublishForm: {
          content: '',
          replyId: '',
          type: ''
        }
      }
    },
    methods: {
      publishComment: function () {
        var self = this
        this.commentPublishForm.replyId = this.replyId
        this.commentPublishForm.type = this.type
        requestApi('post', 'comment', this.commentPublishForm, () => {
          UIkit.notification({message: '<span uk-icon=\'icon: check\'></span>评论成功', status: 'success'})
          self.commentPublishForm.content = ''
          self.$emit('refresh-conversation-list')
        })
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
