import {requestApi} from '../../api/requestUtils'
import types from '../mutation-types'

const state = {
  //搜索结果列表
  searchResultList: [],
  //个性推荐列表
  myRecommendList:[]
}

const actions = {
  likeArticleAction({commit}, payload) {
    requestApi('post', 'article/like', payload, () => commit(types.DEFAULT))
  },
  cancelLikeArticleAction({commit}, payload) {
    requestApi('post', 'article/cancel_like', payload, () => commit(types.DEFAULT))
  },
  fetchSearchResultAction({commit}, payload) {
    if (payload.page === 0) {
      state.searchResultList = []
    }
    return requestApi('post', 'search', payload, (res) => {
      commit(types.SEARCH_RESULT_LIST, res)
      return (res.responseVO != null && res.responseVO.length === parseInt(payload.size))
    })
  },
  fetchMyRecommendListAction({commit}, payload) {
    if (payload.page === 0) {
      state.myRecommendList = []
    }
    return requestApi('get', 'recommend/homepage/', payload, (res) => {
      commit(types.MY_RECOMMEND_LIST, res)
      return (res.responseVO != null && res.responseVO.length === parseInt(payload.size))
    })
  },
}

const mutations = {
  [types.DEFAULT](state) {
  },
  [types.SEARCH_RESULT_LIST](state, data) {
    state.searchResultList = state.searchResultList.concat(data.responseVO)
  },
  [types.MY_RECOMMEND_LIST](state, data) {
    state.myRecommendList = state.myRecommendList.concat(data.responseVO)
  }
}

export default {
  state,
  actions,
  mutations
}
