import {requestApi} from '../../api/requestUtils'
import types from '../mutation-types'

const state = {
  circleList: [], //首页朋友圈列表
  momentList: [],//首页推荐动态列表
  myMomentList: [],//我的动态列表
  userMomentList: [],//用户动态列表
  isShowPublishMoment: false, //是否显示发表动态组件
}

const actions = {
  //获取首页推荐动态列表
  getRecommendMoments({commit}, payload) {
    requestApi('get', 'main', payload, (res) => commit(types.RECOMMEND_MOMENTS, res))
  },
  //显示发表动态组件
  isShowPublishMomentCompAction({commit}, isShow) {
    commit(types.DISPLAY_PUBLISH_MOMENT_COMP, isShow)
  },
  publishMomentAction({commit}, payload) {
    //增加成功失败处理...
    return requestApi('post', 'moment', payload, () => {
      commit(types.DISPLAY_PUBLISH_MOMENT_COMP, false)
      return true
    })
  },
  //获取我的动态朋友圈列表
  fetchMyCircleListAction({commit}, payload) {
    return requestApi('get', 'mycircle', payload, (res) => {
      commit(types.MY_CIRCLE_LIST, res)
      return (res.responseVO != null && res.responseVO.length !== 0)
    })
  },
  //获取我的动态列表
  fetchMyMomentListAction({commit}, payload) {
    requestApi('get', 'mymoments', payload, (res) => commit(types.MY_MOMENT_LIST, res))
  },
  //获取用户的动态列表
  fetchUserMomentListAction({commit}, payload) {
    requestApi('get', payload.uniqueId + '/moments', payload.pageParam, (res) => commit(types.USER_MOMENT_LIST, res))
  }
}

const mutations = {
  [types.RECOMMEND_MOMENTS](state, data) {
    state.momentList = data.responseVO
  },
  [types.DISPLAY_PUBLISH_MOMENT_COMP](state, isShow) {
    state.isShowPublishMoment = isShow
  },
  [types.MY_CIRCLE_LIST](state, data) {
    state.circleList = state.circleList.concat(data.responseVO)
  },
  [types.MY_MOMENT_LIST](state, data) {
    state.myMomentList = state.myMomentList.concat(data.responseVO)
  },
  [types.USER_MOMENT_LIST](state, data) {
    state.userMomentList = state.userMomentList.concat(data.responseVO)
  }
}

export default {
  state,
  actions,
  mutations
}
