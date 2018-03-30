import {requestApi} from '../../api/requestUtils'
import types from '../mutation-types'

const state = {
  //我的博客列表
  myBlogList: [],
  //用户博客列表
  userBlogList: [],
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
    requestApi('get', 'myblogs', payload, (res) => commit(types.MY_BLOG_LIST, res))
  },
  fetchUserBlogListAction({commit}, payload) {
    requestApi('get', payload.uniqueId + '/blogs', payload.pageParam, (res) => commit(types.USER_BLOG_LIST, res))
  },
  fetchBlogDetailAction({commit}, uniqueId) {
    requestApi('get', 'blog/' + uniqueId, null, (res) => commit(types.BLOG_DETAIL, res))
  },
}

const mutations = {
  [types.DEFAULT](state) {
  },
  [types.MY_BLOG_LIST](state, data) {
    state.myBlogList = state.myBlogList.concat(data.responseVO)
  },
  [types.USER_BLOG_LIST](state, data) {
    state.userBlogList = state.userBlogList.concat(data.responseVO)
  },
  [types.BLOG_DETAIL](state, data) {
    state.blogDetail = data.responseVO
  }
}

export default {
  state,
  actions,
  mutations
}
