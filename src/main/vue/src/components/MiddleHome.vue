<template>
  <div class="uk-container">
    <div class="uk-margin-top uk-flex">
      <ul uk-tab class=" uk-margin-remove uk-width-2-3">
        <li class="uk-active">
          <router-link to="/friend_circle">朋友圈</router-link>
        </li>
        <li>
          <router-link to="/recommend">今日推荐</router-link>
        </li>
      </ul>
      <div class="uk-width-expand uk-text-right uk-text-bottom uk-margin-right">
        <router-link class="uk-button uk-button-link uk-text-bold" to="/aboutme">
          <span uk-icon="triangle-right"></span>
          <span>关于我</span>
          <span uk-icon="triangle-left"></span>
        </router-link>
      </div>
    </div>

    <div v-show="isShowPublishMoment">
      <div class="uk-margin-small-top uk-margin-small-bottom uk-margin-small-left uk-margin-small-right">
        <textarea v-model="moment.content"
                  class="uk-textarea" rows="6" placeholder="说点想说的..."></textarea>
        <div uk-grid class="uk-margin-remove">

          <img class="uk-padding-remove" v-for="path in picPathList" v-bind:src="path"
               style="height: 40px;width: 40px"/>

          <div class="js-upload" uk-form-custom>
            <input type="file" multiple>
            <button uk-icon="image" class="uk-card uk-button-small uk-padding-remove"></button>
          </div>
          <div class="uk-card">
            <div uk-form-custom="target: > * > span:last-child">
              <select v-model="moment.isPrivate">
                <option value="false">公开</option>
                <option value="true">私人</option>
              </select>
              <span class="uk-link">
                <span uk-icon="icon: unlock"></span>
              </span>
            </div>
          </div>
          <div class="uk-card uk-width-expand uk-text-right" style="margin-top: 4px">
            <button class="uk-button uk-button-default uk-button-small"
                    v-on:click="isShowPublishMomentCompAction(false)">隐藏
            </button>
            <button class="uk-button uk-button-primary uk-button-small"
                    v-on:click="publishMoment">发表
            </button>
          </div>
        </div>
      </div>
    </div>
    <div>
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
  import {mapActions, mapState} from 'vuex'

  export default {
    name: 'MiddleHome',
    data() {
      return {
        moment: {
          content: '',
          isPrivate: false,
          isShare: false,
          isContainPic: false,
          picNameList: []
        },
        picPathList: []
      }
    },
    computed: {
      ...mapState({
        isShowPublishMoment: state => state.moment.isShowPublishMoment
      })
    },
    methods: {
      ...mapActions([
        'isShowPublishMomentCompAction', // 将 `this.increment()` 映射为 `this.$store.dispatch('increment')`
        'publishMomentAction'
      ]),
      publishMoment: function () {
        this.publishMomentAction(this.moment).then(res => {
          if (res) {
            UIkit.notification({message: '<span uk-icon=\'icon: check\'></span>发表成功', status: 'success'})
            this.moment.content = ''
            this.isShowPublishMomentCompAction(false)
            this.$router.push('/self/my_moment')
          }
        })
      }
    },
    mounted: function () {
      const self = this;
      UIkit.upload('.js-upload', {
        name: 'pics',
        url: global.UPLOAD_PIC_URL,
        allow: '*.(jpg|jpeg|png)',
        multiple: true,

        beforeSend: function () {
          console.log('beforeSend', arguments);
          arguments[0].xhr.withCredentials = true
        },
        beforeAll: function () {
          console.log('beforeAll', arguments);
        },
        load: function () {
          console.log('load', arguments);
        },
        error: function () {
          console.log('error', arguments);
        },
        complete: function () {
          console.log('complete', arguments);
        },

        loadStart: function (e) {
          console.log('loadStart', arguments);
        },

        loadEnd: function (e) {
          console.log('loadEnd', arguments);
        },

        completeAll: function () {
          self.moment.isContainPic = true
          var picList = JSON.parse(arguments[0].response)
          for (var i = 0; i < picList.length; i++) {
            self.picPathList.push(picList[i].url)
            self.moment.picNameList.push(picList[i].name)
          }
          alert('Upload Completed');
        }

      });
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
