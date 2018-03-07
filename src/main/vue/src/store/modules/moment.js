import {requestApi} from '../../api/requestUtils'
import types from '../mutation-types'

const state = {
  momentList: [],//首页推荐动态列表
  myMomentList: [],//我的动态列表
  isShowPublishMoment: false //是否显示发表动态组件
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
  //获取我的动态列表
  fetchMyMomentListAction({commit}, payload) {
    requestApi('get', 'mine/moment', payload, (res) => commit(types.FETCH_MY_MOMENT_LIST, res))
  }
}

const mutations = {
  [types.RECOMMEND_MOMENTS](state, data) {
    state.momentList = data
  },
  [types.DISPLAY_PUBLISH_MOMENT_COMP](state, isShow) {
    state.isShowPublishMoment = isShow
  },
  [types.FETCH_MY_MOMENT_LIST](state, data) {
    state.myMomentList = state.myMomentList.concat(data.responseVO)
  }
}

export default {
  state,
  actions,
  mutations
}
