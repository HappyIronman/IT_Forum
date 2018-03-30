import {requestApi} from '../../api/requestUtils'
import types from '../mutation-types'

const state = {
  userInfo: {},
  followList: [],
  aboutmeList: []
}

const actions = {
  userLoginAction({commit}, payload) {
    return requestApi('post', 'user/login', payload, (res) => {
      commit(types.USER_INFO, res)
      return true
    })
  },
  fetchMineInfoAction({commit}) {
    requestApi('get', 'my/info', null, (res) => commit(types.USER_INFO, res))
  },
  fetchUserInfoAction({commit}, uniqueId) {
    requestApi('get', 'user/' + uniqueId, null, (res) => commit(types.USER_INFO, res))
  },
  followUserAction({commit}, uniqueId) {
    requestApi('post', 'user/follow/' + uniqueId, null, (res) => commit(types.FOLLOW_USER, res))
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
  [types.USER_INFO](state, data) {
    state.userInfo = data.responseVO
  },
  [types.FOLLOW_USER](state, data) {
    console.log('follow success')
    state.userInfo.relation = data.responseVO
    state.userInfo.followerNum += 1
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
