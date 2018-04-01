<template>
  <div>
    <div id="ajax-hook"></div>
    <div class="wrap">
      <div class="wpn">
        <div class="form-data pos">
          <a href="https://www.cadillac.com.cn/sedan/atsl.html">
            <img src="../../static/img/logo.png" class="head-logo" style="width: 40px;height: 40px">
          </a>
          <div class="change-login">
            <p class="account_number on">账号登录</p>
            <p class="message">短信登录(暂不支持)</p>
          </div>
          <div class="form1">
            <p class="p-input pos">
              <label for="num">手机号/用户名/邮箱</label>
              <input type="text" id="num" v-model="username">
              <span class="tel-warn num-err hide"><em>账号或密码错误，请重新输入</em><i class="icon-warn"></i></span>
            </p>
            <p class="p-input pos">
              <label for="pass">请输入密码</label>
              <input type="password" style="display:none" v-model="password"/>
              <input type="password" id="pass" autocomplete="new-password" v-model="password">
              <span class="tel-warn pass-err hide"><em>账号或密码错误，请重新输入</em><i class="icon-warn"></i></span>
            </p>
            <p class="p-input pos code hide">
              <label for="veri">请输入验证码</label>
              <input type="text" id="veri">
              <img src="">
              <span class="tel-warn img-err hide"><em>账号或密码错误，请重新输入</em><i class="icon-warn"></i></span>
              <!-- <a href="javascript:;">换一换</a> -->
            </p>
          </div>
          <div class="form2 hide">
            <p class="p-input pos">
              <label for="num2">手机号</label>
              <input type="number" id="num2">
              <span class="tel-warn num2-err hide"><em>账号或密码错误</em><i class="icon-warn"></i></span>
            </p>
            <p class="p-input pos">
              <label for="veri-code">输入验证码</label>
              <input type="number" id="veri-code">
              <a href="javascript:;" class="send">发送验证码</a>
              <span class="time hide"><em>120</em>s</span>
              <span class="tel-warn error hide">验证码错误</span>
            </p>
          </div>
          <div class="r-forget cl">
            <router-link to="/register" class="z">账号注册</router-link>
          </div>
          <button class="lang-btn off log-btn" v-on:click="userLogin">登录</button>
          <div class="third-party">
            <a href="#" class="log-qq icon-qq-round"></a>
            <a href="#" class="log-qq icon-weixin"></a>
            <a href="#" class="log-qq icon-sina1"></a>
          </div>
          <p class="right">Powered by © 2018</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapActions} from 'vuex'
  import {Jagree} from '../../static/js/login/agree'
  import {Jlogin} from "../../static/js/login/login";

  export default {
    name: 'LoginPage',
    data() {
      return {
        username: "",
        password: ""
      }
    },
    created() {
    },
    methods: {
      ...mapActions([
        'userLoginAction'
      ]),
      userLogin: function () {
        this.userLoginAction({'username': this.username, 'password': this.password}).then(
          (result) => {
            if (result) {
              this.$router.push('/friend_circle')
            } else {
              this.username = ""
              this.password = ""
            }
          }
        )

      }
    },
    mounted: function () {
      Jagree()
      Jlogin()
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  @import "../../static/css/login/base.css";
  /*@import "../../static/css/login/iconfont.css";*/
  @import "../../static/css/login/reg.css";

</style>
