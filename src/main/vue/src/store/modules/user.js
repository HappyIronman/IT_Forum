import {requestApi} from '../../api/requestUtils'
import types from '../mutation-types'

const state = {
  userInfo: {}
}

const actions = {
  userLoginAction({commit}, payload) {
    return requestApi('post', 'user/login', payload, (res) => {
      commit(types.USER_INFO, res)
      return true
    })
  },
  fetchMineInfoAction({commit}) {
    requestApi('get', 'user/mine', null, (res) => commit(types.USER_INFO, res))
  }
}

const mutations = {
  [types.USER_INFO](state, data) {
    state.userInfo = data.responseVO
  },
}

export default {
  state,
  actions,
  mutations
}
