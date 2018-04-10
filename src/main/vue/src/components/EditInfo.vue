<template>
  <div class="uk-padding">

    <div class="uk-text-center">
      <span class="uk-label uk-label-success">完善个人信息</span>
    </div>

    <div class="uk-padding">
      <form class="uk-form-stacked" @submit.prevent="validateBeforeSubmit">

        <div class="uk-margin">
          <p class="uk-text-meta uk-margin-bottom">我的头像</p>
          <div>
            <img class="uk-border-circle" style="border:1px solid darkgrey; width: 80px; height: 80px"
                 v-bind:src="profileUrl">
            <div class="js-upload uk-text-bottom" uk-form-custom>
              <input type="file" multiple>
              <button class="uk-margin-left uk-button uk-button-small uk-button-default uk-text-small">
                换个头像呗
              </button>
            </div>
          </div>
        </div>

        <div class="uk-margin uk-margin-top">
          <label class="uk-form-label" for="username">用户名</label>
          <div class="uk-form-controls">
            <input class="uk-input uk-width-1-2" id="username" type="text" placeholder="Some text..." disabled
                   v-bind:value="loginUserInfo.username">
          </div>
        </div>
        <div class="uk-margin">
          <label class="uk-form-label" for="phone">手机号</label>
          <div class="uk-form-controls">
            <input class="uk-input uk-width-1-2" id="phone" type="text" placeholder="请输入常用手机号" v-model="phone"
                   v-validate="{ required: true, regex: /^1[34578]\d{9}$/ }" name="phone">
            <span v-show="errors.has('phone')" class="uk-text-danger">手机号格式不合法</span>
          </div>
        </div>

        <div class="uk-margin">
          <label class="uk-form-label" for="email">邮箱</label>
          <div class="uk-form-controls">
            <input class="uk-input uk-width-1-2" id="email" type="text" placeholder="请输入常用邮箱" v-model="email"
                   v-validate="'email'" name="email">
            <span v-show="errors.has('email')" class="uk-text-danger">邮箱格式不合法</span>
          </div>
        </div>


        <div class="uk-margin">
          <label class="uk-form-label" for="form-stacked-select">性别</label>
          <div class="uk-form-controls uk-width-1-4">
            <select class="uk-select" id="form-stacked-select" v-model="sex">
              <option value="0">保密</option>
              <option value="1">男</option>
              <option value="2">女</option>
            </select>
          </div>
        </div>

        <div class="uk-margin">
          <label class="uk-form-label" for="school">学校</label>
          <div class="uk-form-controls">
            <input class="uk-input uk-width-1-2" id="school" type="text" v-model="school"
                   v-validate="'min:2|max:20'" name="school">
            <span v-show="errors.has('school')" class="uk-text-danger">校名有点奇葩啊</span>
          </div>
        </div>

        <div class="uk-margin">
          <label class="uk-form-label" for="intro">自我介绍</label>
          <div class="uk-form-controls">
            <input class="uk-input uk-width-3-4" id="intro" type="text" placeholder="一句话自我介绍一下吧" v-model="intro"
                   v-validate="'max:24'" name="intro">
            <span v-show="errors.has('intro')" class="uk-text-danger">字数超限</span>
          </div>
        </div>

        <div class="uk-margin-large-top">
          <input type="submit" class="uk-button-small uk-button-primary" value="提交修改"/>
        </div>

      </form>
    </div>


    <!--<div>-->
    <!--<label class="label">First Name</label>-->
    <!--<p class="control has-icon has-icon-right">-->
    <!--<input name="first_name" v-model="first_name" v-validate.initial="'required|alpha'"-->
    <!--:class="{'input': true, 'is-danger': errors.has('first_name') }" type="text" placeholder="First Name">-->
    <!--<i v-show="errors.has('first_name')" class="fa fa-warning"></i>-->
    <!--<span v-show="errors.has('first_name')" class="help is-danger">{{ errors.first('first_name') }}</span>-->
    <!--</p>-->
    <!--</div>-->


  </div>
</template>

<script>
  import {mapActions, mapState} from 'vuex'

  export default {
    name: 'EditInfo',
    data() {
      return {
        profilePath: "",
        profile: "",
        phone: "",
        email: "",
        sex: "",
        school: "",
        intro: ""
      }
    },
    computed: {
      ...mapState({
        loginUserInfo: state => state.user.loginUserInfo
      })
    },
    created: function () {
      console.log(this.loginUserInfo)
      this.profileUrl = this.loginUserInfo.profileUrl
      this.profile = this.loginUserInfo.profile
      this.phone = this.loginUserInfo.phone
      this.email = this.loginUserInfo.email
      this.sex = this.loginUserInfo.sex
      this.school = this.loginUserInfo.school
      this.intro = this.loginUserInfo.intro
    },
    methods: {
      ...mapActions([
        'editInfoAction'
      ]),
      validateBeforeSubmit() {
        this.$validator.validateAll().then((result) => {
          if (result) {
            var payload = {
              profile: this.profile,
              phone: this.phone,
              email: this.email,
              sex: this.sex,
              school: this.school,
              intro: this.intro
            }
            this.editInfoAction(payload).then(
              res => {
                if (res) {
                  alert("修改成功")
                  this.$router.push({path: `/self/`})
                }
              }
            )
            return;
          }
          alert('格式有误哦!');
        });
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
          let profileInfo = JSON.parse(arguments[0].response)[0]
          console.log(profileInfo)
          self.profileUrl = profileInfo.url
          self.profile = profileInfo.name
          alert('Upload Completed');
        }

      });
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
