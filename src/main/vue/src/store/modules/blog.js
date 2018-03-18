import {requestApi} from '../../api/requestUtils'
import types from '../mutation-types'

const state = {
  //我的博客列表
  myBlogList: [],
  //博客详情
  blogDetail: {}
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
  fetchBlogDetailAction({commit}, uniqueId) {
    requestApi('get', '/blog/' + uniqueId, null, (res) => commit(types.FETCH_BLOG_DETAIL, res))
  },
}

const mutations = {
  [types.DEFAULT](state) {
  },
  [types.FETCH_MY_BLOG_LIST](state, data) {
    state.myBlogList = state.myBlogList.concat(data.responseVO)
  },
  [types.FETCH_BLOG_DETAIL](state, data) {
    state.blogDetail = data.responseVO
  }
}

export default {
  state,
  actions,
  mutations
}
