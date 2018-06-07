import {requestApi} from '../../api/requestUtils'
import types from '../mutation-types'
import storage from '../../storage'


const state = {
  //登录信息
  loginUserInfo: storage.getStorage("LOGIN_USER_INFO"),
  //个人信息
  userInfo: {},
  followerList: [],
  followingList: [],
  newAboutMeNum: 0,
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
  fetchNewAboutMeNum({commit}) {
    requestApi('get', 'my/new_about_me_num/', null, (res) => commit(types.NEW_ABOUT_ME_NUM, res))
  },
  fetchAboutmeListAction({commit}, payload) {
    if (payload.page === 0) {
      state.aboutmeList = []
    }
    return requestApi('get', 'my/aboutmes', payload, (res) => {
      commit(types.ABOUT_ME_LIST, res)
      return (res.responseVO != null && res.responseVO.length === parseInt(payload.size))
    })
  },
  fetchMyFollowerListAction({commit}, payload) {
    if (payload.page === 0) {
      state.followerList = []
    }
    return requestApi('get', 'my/followers', payload, (res) => {
      commit(types.FOLLOWER_LIST, res)
      return (res.responseVO != null && res.responseVO.length === parseInt(payload.size))
    })
  },
  fetchMyFollowingListAction({commit}, payload) {
    if (payload.page === 0) {
      state.followingList = []
    }
    return requestApi('get', 'my/followings', payload, (res) => {
      commit(types.FOLLOWING_LIST, res)
      return (res.responseVO != null && res.responseVO.length === parseInt(payload.size))
    })
  }
}

const mutations = {
  [types.LOGIN_USER_INFO](state, data) {
    state.loginUserInfo = data.responseVO
    storage.setStorage("LOGIN_USER_INFO", state.loginUserInfo, 1000 * 60 * 180)
    console.log('个人信息存储成功' + JSON.stringify(state.loginUserInfo))
  },
  [types.USER_LOGOUT](state) {
    state.loginUserInfo = {}
    storage.removeStorage("LOGIN_USER_INFO")
  },
  [types.USER_INFO](state, data) {
    state.userInfo = data.responseVO
  },
  [types.FOLLOW_USER](state, data) {
    state.userInfo.relation = data.responseVO
    state.loginUserInfo.followingNum += 1
    storage.updateLoginUserInfo("followingNum", parseInt(storage.getStorage("LOGIN_USER_INFO").followingNum) + 1)
  },
  [types.FOLLOWER_LIST](state, data) {
    state.followerList = state.followerList.concat(data.responseVO)
  },
  [types.FOLLOWING_LIST](state, data) {
    state.followingList = state.followingList.concat(data.responseVO)
  },
  [types.NEW_ABOUT_ME_NUM](state, data) {
    state.newAboutMeNum = parseInt(data.responseVO)
  },
  [types.ABOUT_ME_LIST](state, data) {
    state.aboutmeList = state.aboutmeList.concat(data.responseVO)
    if (state.newAboutMeNum !== 0) {
      state.newAboutMeNum = 0
    }
  }
}

export default {
  state,
  actions,
  mutations
}
