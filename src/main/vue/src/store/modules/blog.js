import {requestApi} from '../../api/requestUtils'
import types from '../mutation-types'
import storage from "../../storage";

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
      commit(types.PUBLISH_BLOG)
      return res.responseVO
    })
  },
  fetchMyBlogListAction({commit}, payload) {
    if (payload.page === 0) {
      if (state.myBlogList.length === parseInt(payload.size)) {
        return true;
      }
      if (state.myBlogList.length > 0) {
        return false;
      }
    }
    return requestApi('get', 'myblogs', payload, (res) => {
      commit(types.MY_BLOG_LIST, res)
      return (res.responseVO != null && res.responseVO.length === parseInt(payload.size))
    })
  },
  fetchUserBlogListAction({commit}, payload) {
    if (payload.pageParam.page === 0) {
      if (state.userBlogList.length === parseInt(payload.pageParam.size)) {
        return true;
      }
      if (state.userBlogList.length > 0) {
        return false;
      }
    }
    return requestApi('get', payload.uniqueId + '/blogs', payload.pageParam, (res) => {
      commit(types.USER_BLOG_LIST, res)
      return (res.responseVO != null && res.responseVO.length === parseInt(payload.pageParam.size))
    })
  },
  fetchUserBlogDetailAction({commit}, uniqueId) {
    requestApi('get', 'blog/' + uniqueId, null, (res) => commit(types.BLOG_DETAIL, res))
  },
  fetchMyBlogDetailAction({commit}, uniqueId) {
    requestApi('get', 'my_blog/' + uniqueId, null, (res) => commit(types.BLOG_DETAIL, res))
  }
}

const mutations = {
  [types.DEFAULT](state) {
  },
  [types.PUBLISH_BLOG](state) {
    storage.updateLoginUserInfo("blogNum", parseInt(storage.getStorage("LOGIN_USER_INFO").blogNum) + 1)
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
