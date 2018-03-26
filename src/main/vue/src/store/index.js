import Vue from 'vue'
import Vuex from 'vuex'
import user from './modules/user'
import common from './modules/common'
import moment from './modules/moment'
import blog from './modules/blog'
import question from './modules/question'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    user, common, moment, blog, question
  }
})

export default store
