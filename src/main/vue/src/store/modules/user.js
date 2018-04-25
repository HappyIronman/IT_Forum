import {requestApi} from '../../api/requestUtils'
import types from '../mutation-types'
import storage from '../../storage'


const state = {
  //登录信息
  loginUserInfo: storage.getStorage("loginUserInfo"),
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
  userRegisterAction({commit}, payload) {
    return requestApi('post', 'user/register', payload, (res) => {
      commit(types.LOGIN_USER_INFO, res)
      return true
    })
  },
  userLogoutAction({commit}) {
    requestApi('get', 'user/logout', null, () => {
      commit(types.USER_LOGOUT)
    })
  },
  editInfoAction({commit}, payload) {
    return requestApi('put', 'my/edit', payload, (res) => {
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
    if (payload.page === 0) {
      if (state.aboutmeList.length === parseInt(payload.size)) {
        return true;
      }
      if (state.aboutmeList.length > 0) {
        return false;
      }
    }
    return requestApi('get', 'my/aboutmes', payload, (res) => {
      commit(types.ABOUT_ME_LIST, res)
      return (res.responseVO != null && res.responseVO.length === parseInt(payload.size))
    })
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
    storage.setStorage("loginUserInfo", state.loginUserInfo, 1000 * 60 * 180)
    console.log('个人信息存储成功' + JSON.stringify(state.loginUserInfo))
  },
  [types.USER_LOGOUT](state) {
    state.loginUserInfo = {}
    storage.removeStorage("loginUserInfo")
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
    state.aboutmeList = state.aboutmeList.concat(data.responseVO)
  }
}

export default {
  state,
  actions,
  mutations
}
