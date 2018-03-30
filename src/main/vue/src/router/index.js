import Vue from 'vue'
import Router from 'vue-router'
import homepage from '../pages/Homepage.vue'
import writeBlogPage from '../pages/WriteBlogPage.vue'
import shareBlogPage from '../pages/ShareBlogPage.vue'
import selfPage from '../pages/SelfPage'
import loginPage from '../pages/LoginPage'
import friendCircle from '../components/FriendCircle'
import aboutme from '../components/Aboutme'
import myMoment from '../components/MyMoment.vue'
import myBlog from '../components/MyBlog.vue'
import myFollowing from '../components/MyFollowing.vue'
import myFollower from '../components/MyFollower.vue'
import userMoment from '../components/UserMoment.vue'
import userBlog from '../components/UserBlog.vue'
import blogDetail from '../components/BlogDetail.vue'

import userpage from '../pages/Userpage.vue'

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
          path: '/aboutme',
          name: 'aboutme',
          component: aboutme
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
          path: '/my_blog/:blogId',
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
    },
    {
      path: '/share_blog/:uniqueId',
      name: 'shareBlogPage',
      component: shareBlogPage,
      children: []
    },
    {
      path: '/self',
      name: 'selfPage',
      component: selfPage,
      children: [
        {
          path: 'my_following',
          name: 'myFollowing',
          component: myFollowing,
        },
        {
          path: 'my_follower',
          name: 'myFollower',
          component: myFollower,
        }
      ]
    },
    {
      path: '/login',
      name: 'loginPage',
      component: loginPage,
      children: []
    },
    {
      path: '/user/:uniqueId',
      name: 'userpage',
      component: userpage,
      props: true,
      children: [
        {
          path: 'moment',
          name: 'userMoment',
          component: userMoment
        },
        {
          path: 'blog',
          name: 'userBlog',
          component: userBlog
        },
        {
          path: '/blog/:blogId',
          name: 'blogDetail',
          component: blogDetail
        }
      ]
    }
  ]
})
