import {requestApi} from '../../api/requestUtils'
import types from '../mutation-types'

const state = {
  //我的博客列表
  myBlogList: []
}

const actions = {
  publishBlogAction({commit}, payload) {
    //增加成功失败处理...
    return requestApi('post', 'blog', payload, (res) => {
      commit(types.DEFAULT)
      return res.responseVO
    })
  },
  fetchMyBlogListAction({commit}, payload) {
    requestApi('get', 'mine/blog', payload, (res) => commit(types.FETCH_MY_BLOG_LIST, res))
  },
}

const mutations = {
  [types.DEFAULT](state) {
  },
  [types.FETCH_MY_BLOG_LIST](state, data) {
    state.myBlogList = state.myBlogList.concat(data.responseVO)
  }
}

export default {
  state,
  actions,
  mutations
}
