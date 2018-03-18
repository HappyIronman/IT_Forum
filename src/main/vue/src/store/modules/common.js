import {requestApi} from '../../api/requestUtils'
import types from '../mutation-types'

const state = {}

const actions = {
  likeArticleAction({commit}, payload) {
    requestApi('post', 'article/like', payload, () => commit(types.DEFAULT))
  },
  cancelLikeArticleAction({commit}, payload) {
    requestApi('post', 'article/cancel_like', payload, () => commit(types.DEFAULT))
  }
}

const mutations = {
  [types.DEFAULT](state) {
  }
}

export default {
  state,
  actions,
  mutations
}
