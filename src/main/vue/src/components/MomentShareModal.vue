<template>
  <div uk-modal>
    <div class="uk-modal-dialog">
      <button class="uk-modal-close-default" type="button" uk-close></button>
      <div class="uk-modal-header uk-padding-small">
        <p>转发 <span class="uk-text-bold uk-text-warning">{{momentInfo.username}}</span> 的动态</p>
      </div>
      <div class="uk-modal-body">
        <div class="uk-margin-small-top uk-margin-small-bottom">
        <textarea v-model="moment.content"
                  class="uk-textarea" rows="5" placeholder="转发理由..."></textarea>
          <div class="uk-margin-remove">
            <div uk-form-custom="target: > * > span:last-child">
              <select v-model="moment.isPrivate">
                <option value="false">公开</option>
                <option value="true">私人</option>
              </select>
              <span class="uk-link">
                <span uk-icon="icon: unlock"></span>
                <span></span>
              </span>
            </div>
          </div>
        </div>
      </div>
      <div class="uk-modal-footer uk-text-right uk-padding-small">
        <button class="uk-button uk-button-default uk-modal-close uk-button-small" type="button">取消</button>
        <button class="uk-button uk-button-primary uk-button-small" type="button" v-on:click="publishMoment">转发</button>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapActions} from 'vuex'

  export default {
    name: 'MomentShareModal',
    props: ['momentInfo'],
    data() {
      return {
        moment: {
          content: '',
          isPrivate: false,
          isShare: true,
          isContainPic: false,
          originId: ''
        }
      }
    },
    created: function () {

    },
    methods: {
      ...mapActions([
        'publishMomentAction'
      ]),
      publishMoment: function () {
        this.moment.originId = this.momentInfo.uniqueId
        console.log(JSON.stringify(this.moment))
        this.publishMomentAction(this.moment).then(res => {
          console.log(res)
          if (res) {
            UIkit.notification({message: '<span uk-icon=\'icon: check\'></span>转发成功', status: 'success'})
            UIkit.modal('#m' + this.momentInfo.uniqueId).hide()
            this.moment.content = ''
          }
        })
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
