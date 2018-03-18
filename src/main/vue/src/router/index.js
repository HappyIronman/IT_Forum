import Vue from 'vue'
import Router from 'vue-router'
import homepage from '../pages/Homepage.vue'
import writeBlogPage from '../pages/WriteBlogPage.vue'
import friendCircle from '../components/FriendCircle'
import momentHome from '../components/MomentHome.vue'
import blogHome from '../components/BlogHome.vue'
import myMoment from '../components/MyMoment.vue'
import myBlog from '../components/myBlog.vue'
import blogDetail from '../components/BlogDetail.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'homepage',
      component: homepage,
      children: [
        {
          path: '/friend_circle',
          name: 'friendCircle',
          component: friendCircle
        },
        {
          path: '/moment',
          name: 'momentHome',
          component: momentHome
        },
        {
          path: '/blog',
          name: 'blogHome',
          component: blogHome
        },
        {
          path: '/my_moment',
          name: 'myMoment',
          component: myMoment
        },
        {
          path: '/my_blog',
          name: 'myBlog',
          component: myBlog
        },
        {
          path: '/my_blog/:uniqueId',
          name: 'blogDetail',
          component: blogDetail
        }
      ]
    },
    {
      path: '/write_blog',
      name: 'writeBlogPage',
      component: writeBlogPage,
      children: []
    }
  ]
})
