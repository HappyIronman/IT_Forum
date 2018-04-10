<template>
  <div>
    <div id="ajax-hook"></div>
    <div class="wrap">
      <div class="wpn">
        <div class="form-data pos">
          <a href="https://www.cadillac.com.cn/sedan/ct6.html">
            <img src="../../static/img/logo.png" class="head-logo" style="width: 40px;height: 40px">
          </a>
          <!--<p class="tel-warn hide"><i class="icon-warn"></i></p>-->
          <form>
            <p class="p-input pos">
              <label for="username">昵称</label>
              <input type="text" id="username" autocomplete="off" v-model="username">
              <span class="tel-warn username-err hide"><em></em><i class="icon-warn"></i></span>
            </p>
            <p class="p-input pos">
              <label for="num2">手机号(务必填写正确手机号)</label>
              <input type="number" id="num2" autocomplete="off" v-model="phone">
              <span class="tel-warn num2-err hide"><em></em><i class="icon-warn"></i></span>
            </p>
            <!--<p class="p-input pos" id="sendcode">-->
            <!--<label for="veri-code">输入验证码</label>-->
            <!--<input type="number" id="veri-code">-->
            <!--<a href="javascript:;" class="send">发送验证码</a>-->
            <!--<span class="time hide"><em>120</em>s</span>-->
            <!--<span class="error hide"><em></em><i class="icon-warn" style="margin-left: 5px"></i></span>-->
            <!--</p>-->
            <p class="p-input pos" id="pwd">
              <label for="passport">输入密码</label>
              <input type="password" style="display: none"/>
              <input type="password" id="passport" v-model="password">
              <span class="tel-warn pwd-err hide"><em></em><i class="icon-warn" style="margin-left: 5px"></i></span>
            </p>
            <p class="p-input pos" id="confirmpwd">
              <label for="passport2">确认密码</label>
              <input type="password" style="position:absolute;top:-998px"/>
              <input type="password" id="passport2" v-model="confirmPassword">
              <span class="tel-warn confirmpwd-err hide"><em></em><i class="icon-warn"
                                                                     style="margin-left: 5px"></i></span>
            </p>
          </form>

          <button class="lang-btn" v-on:click="register">注册</button>
          <div class="bottom-info">已有账号，
            <router-link to="/login">马上登录</router-link>
          </div>
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
  import {checkConfidentialPsw, checkPhone, checkUsername, Jlogin} from "../../static/js/login/login"

  export default {
    name: 'RegisterPage',
    data() {
      return {
        username: "",
        phone: "",
        password: "",
        confirmPassword: "",
        isPswConfidential: true
      }
    },
    methods: {
      ...mapActions([
        'userRegisterAction'
      ]),
      register: function () {
        if (checkUsername(this.username)
          && checkPhone(this.phone)
          && checkConfidentialPsw(this.confirmPassword, this.password)) {
          console.log("pass")

          var registerFrom = {
            "username": this.username,
            "phone": this.phone,
            "password": this.password
          }

          this.userRegisterAction(registerFrom).then(
            (result) => {
              if (result) {
                this.$router.push('/friend_circle')
              }
            }
          )
        }
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
