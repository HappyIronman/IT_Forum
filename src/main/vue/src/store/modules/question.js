import {requestApi} from '../../api/requestUtils'
import types from '../mutation-types'

const state = {
  myQuestionList: [],//我的提问列表
  userQuestionList: [],//用户提问列表
  questionDetail: {}//问题详情
}

const actions = {
  publishQuestionAction({commit}, payload) {
    //增加成功失败处理...
    return requestApi('post', 'question', payload, () => {
      return true
    })
  },

  //获取我的提问列表
  fetchMyQuestionListAction({commit}, payload) {
    if (payload.page === 0) {
      if (state.myQuestionList.length === parseInt(payload.size)) {
        return true;
      }
      if (state.myQuestionList.length > 0) {
        return false;
      }
    }
    return requestApi('get', 'myquestions', payload, (res) => {
      commit(types.MY_QUESTION_LIST, res)
      return (res.responseVO != null && res.responseVO.length === parseInt(payload.size))
    })
  },
  //获取用户的提问列表
  fetchUserQuestionListAction({commit}, payload) {
    if (payload.pageParam.page === 0) {
      if (state.userQuestionList.length === parseInt(payload.pageParam.size)) {
        return true;
      }
      if (state.userQuestionList.length > 0) {
        return false;
      }
    }
    return requestApi('get', payload.uniqueId + '/questions', payload.pageParam, (res) => {
      commit(types.USER_QUESTION_LIST, res)
      return (res.responseVO != null && res.responseVO.length === parseInt(payload.pageParam.size))
    })
  },
  fetchQuestionDetailAction({commit}, uniqueId) {
    requestApi('get', 'question/' + uniqueId, null, (res) => commit(types.QUESTION_DETAIL, res))
  },
}

const mutations = {
  [types.MY_QUESTION_LIST](state, data) {
    state.myQuestionList = state.myQuestionList.concat(data.responseVO)
  },
  [types.USER_QUESTION_LIST](state, data) {
    state.userQuestionList = state.userQuestionList.concat(data.responseVO)
  },
  [types.QUESTION_DETAIL](state, data) {
    state.questionDetail = data.responseVO
  }
}

export default {
  state,
  actions,
  mutations
}
