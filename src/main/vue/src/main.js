// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import {formatDate} from './formater'
import VeeValidate from 'vee-validate';
import './const'

Vue.config.productionTip = false

Vue.use(VeeValidate)

Vue.filter('formatDate', formatDate);


/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  router,
  components: {App},
  template: '<App/>'
})
