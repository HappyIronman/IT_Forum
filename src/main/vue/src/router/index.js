import Vue from 'vue'
import Router from 'vue-router'
import state from '../store/modules/user'
import homepage from '../pages/Homepage.vue'
import writeBlogPage from '../pages/WriteBlogPage.vue'
import writeQuestionPage from '../pages/WriteQuestionPage.vue'
import shareBlogPage from '../pages/ShareBlogPage.vue'
import selfPage from '../pages/SelfPage.vue'
import loginPage from '../pages/LoginPage.vue'
import userLoginPage from '../pages/UserLoginPage.vue'
import registerPage from '../pages/RegisterPage.vue'
import searchPage from '../pages/SearchPage.vue'
import friendCircle from '../components/FriendCircle.vue'
import recommend from '../components/Recommend.vue'
import aboutme from '../components/Aboutme.vue'
import myMoment from '../components/MyMoment.vue'
import myBlog from '../components/MyBlog.vue'
import myQuestion from '../components/MyQuestion.vue'
import myFollowing from '../components/MyFollowing.vue'
import myFollower from '../components/MyFollower.vue'
import editInfo from '../components/EditInfo.vue'
import userMoment from '../components/UserMoment.vue'
import userBlog from '../components/UserBlog.vue'
import userQuestion from '../components/UserQuestion.vue'
import myBlogDetail from '../components/MyBlogDetail.vue'
import userBlogDetail from '../components/UserBlogDetail.vue'
import myQuestionDetail from '../components/MyQuestionDetail.vue'
import userQuestionDetail from '../components/UserQuestionDetail.vue'

import userpage from '../pages/Userpage.vue'
import storage from '../storage'

Vue.use(Router)

const router = new Router({
  // mode: 'history',
  routes: [
    {
      path: '/',
      name: 'homepage',
      component: homepage,
      meta: {
        auth: true // 这里设置，当前路由需要校验
      },
      children: [
        {
          path: '',
          name: 'friendCircle',
          component: friendCircle
        },
        {
          path: 'friend_circle',
          name: 'friendCircle',
          component: friendCircle
        },
        {
          path: 'recommend',
          name: 'recommend',
          component: recommend
        },
        {
          path: 'aboutme',
          name: 'aboutme',
          component: aboutme
        }
      ]
    },
    {
      path: '/write_blog',
      name: 'writeBlogPage',
      component: writeBlogPage,
      meta: {
        auth: true // 这里设置，当前路由需要校验
      },
      children: []
    },
    {
      path: '/write_question',
      name: 'writeQuestionPage',
      component: writeQuestionPage,
      meta: {
        auth: true // 这里设置，当前路由需要校验
      },
      children: []
    },
    {
      path: '/share_blog/:uniqueId',
      name: 'shareBlogPage',
      component: shareBlogPage,
      meta: {
        auth: true // 这里设置，当前路由需要校验
      },
      children: []
    },
    {
      path: '/self',
      name: 'selfPage',
      component: selfPage,
      meta: {
        auth: true // 这里设置，当前路由需要校验
      },
      children: [
        {
          path: '',
          name: 'myMoment',
          component: myMoment
        },
        {
          path: 'my_moment',
          name: 'myMoment',
          component: myMoment
        },
        {
          path: 'my_blog',
          name: 'myBlog',
          component: myBlog
        },
        {
          path: 'my_question',
          name: 'myQuestion',
          component: myQuestion
        },
        {
          path: 'my_following',
          name: 'myFollowing',
          component: myFollowing,
        },
        {
          path: 'my_follower',
          name: 'myFollower',
          component: myFollower,
        },
        {
          path: 'edit',
          name: 'editInfo',
          component: editInfo,
        },
        {
          path: 'my_blog/:blogId',
          name: 'myBlogDetail',
          component: myBlogDetail
        },
        {
          path: 'my_question/:questionId',
          name: 'myQuestionDetail',
          component: myQuestionDetail
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
      path: '/userLogin',
      name: 'userLoginPage',
      component: userLoginPage,
      children: []
    },
    {
      path: '/register',
      name: 'registerPage',
      component: registerPage,
      children: []
    },
    {
      path: '/user/:uniqueId',
      name: 'userpage',
      component: userpage,
      props: true,
      children: [
        {
          path: '',
          name: 'userMoment',
          component: userMoment
        },
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
          path: 'question',
          name: 'userQuestion',
          component: userQuestion
        },
        {
          path: 'blog/:blogId',
          name: 'userBlogDetail',
          component: userBlogDetail
        },
        {
          path: 'question/:questionId',
          name: 'userQuestionDetail',
          component: userQuestionDetail
        }
      ]
    },
    {
      path: '/search',
      name: 'searchPage',
      component: searchPage,
      children: []
    },
  ]
})

router.beforeEach((to, from, next) => {
  if (to.matched.some(m => m.meta.auth)) {
    // 对路由进行验证
    let loginUserInfo = storage.getStorage("LOGIN_USER_INFO")
    console.log('srotageLoginUserInfo:' + JSON.stringify(loginUserInfo))
    if (loginUserInfo) { // 已经登陆
      state.loginUserInfo = loginUserInfo
      next()
    } else {
      // 未登录,跳转到登陆页面，并且带上 将要去的地址，方便登陆后跳转。
      next({path: '/login', query: {referrer: to.fullPath}})
    }
  } else {
    next()
  }
})

export default router
