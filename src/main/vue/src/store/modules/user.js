import {requestApi} from '../../api/requestUtils'
import types from '../mutation-types'
import storage from '../../storage'


const state = {
  //登录信息
  loginUserInfo: {},
  //个人信息
  userInfo: {},
  followList: [],
  aboutmeList: []
}

const actions = {
  userLoginAction({commit}, payload) {
    return requestApi('post', 'user/login', payload, (res) => {
      commit(types.LOGIN_USER_INFO, res)
      return true
    })
  },
  fetchMineInfoAction({commit}) {
    requestApi('get', 'my/info', null, (res) => commit(types.LOGIN_USER_INFO, res))
  },
  fetchUserInfoAction({commit}, uniqueId) {
    requestApi('get', 'user/' + uniqueId, null, (res) => commit(types.USER_INFO, res))
  },
  followUserAction({commit}, uniqueId) {
    requestApi('post', 'my/follow/' + uniqueId, null, (res) => commit(types.FOLLOW_USER, res))
  },
  fetchAboutmeListAction({commit}, payload) {
    requestApi('get', 'my/aboutmes', payload, (res) => commit(types.ABOUT_ME_LIST, res))
  },
  fetchMyFollowingListAction({commit}) {
    requestApi('get', '/my/followings', null, (res) => commit(types.FOLLOW_LIST, res))
  },
  fetchMyFollowerListAction({commit}) {
    requestApi('get', '/my/followers', null, (res) => commit(types.FOLLOW_LIST, res))
  }
}

const mutations = {
  [types.LOGIN_USER_INFO](state, data) {
    state.loginUserInfo = data.responseVO
    storage.setStorage("loginUserInfo", state.loginUserInfo, 1000 * 60)
    console.log('个人信息存储成功' + JSON.stringify(state.loginUserInfo))
  },
  [types.USER_INFO](state, data) {
    state.userInfo = data.responseVO
  },
  [types.FOLLOW_USER](state, data) {
    console.log('follow success')
    state.loginUserInfo.relation = data.responseVO
    state.loginUserInfo.followerNum += 1
  },
  [types.FOLLOW_LIST](state, data) {
    state.followList = data.responseVO
  },
  [types.ABOUT_ME_LIST](state, data) {
    state.aboutmeList = data.responseVO
  }
}

export default {
  state,
  actions,
  mutations
}
