import {requestApi} from '../../api/requestUtils'
import types from '../mutation-types'
import storage from "../../storage";

const state = {
  myQuestionList: [],//我的提问列表
  userQuestionList: [],//用户提问列表
  questionDetail: {}//问题详情
}

const actions = {
  publishQuestionAction({commit}, payload) {
    //增加成功失败处理...
    return requestApi('post', 'question', payload, () => {
      commit(types.CLEAR_MY_QUESTION_LIST)
      return true
    })
  },

  //获取我的提问列表
  fetchMyQuestionListAction({commit}, payload) {
    if (payload.page === 0) {
      state.myQuestionList = []
    }
    return requestApi('get', 'myquestions', payload, (res) => {
      commit(types.MY_QUESTION_LIST, res)
      return (res.responseVO != null && res.responseVO.length === parseInt(payload.size))
    })
  },
  //获取用户的提问列表
  fetchUserQuestionListAction({commit}, payload) {
    if (payload.pageParam.page === 0) {
      state.userQuestionList = []
    }
    return requestApi('get', payload.uniqueId + '/questions', payload.pageParam, (res) => {
      commit(types.USER_QUESTION_LIST, res)
      return (res.responseVO != null && res.responseVO.length === parseInt(payload.pageParam.size))
    })
  },
  fetchMyQuestionDetailAction({commit}, uniqueId) {
    requestApi('get', 'my_question/' + uniqueId, null, (res) => commit(types.QUESTION_DETAIL, res))
  },
  fetchUserQuestionDetailAction({commit}, uniqueId) {
    requestApi('get', 'question/' + uniqueId, null, (res) => commit(types.QUESTION_DETAIL, res))
  },
}

const mutations = {
  [types.MY_QUESTION_LIST](state, data) {
    state.myQuestionList = state.myQuestionList.concat(data.responseVO)
  },
  [types.CLEAR_MY_QUESTION_LIST](state) {
    state.myQuestionList = []
    storage.updateLoginUserInfo("questionNum", parseInt(storage.getStorage("LOGIN_USER_INFO").questionNum) + 1)
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
