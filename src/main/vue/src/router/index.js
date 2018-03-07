import Vue from 'vue'
import Router from 'vue-router'
import homepage from '../pages/Homepage.vue'
import writeBlogPage from '../pages/WriteBlogPage.vue'
import momentHome from '../components/MomentHome.vue'
import blogHome from '../components/BlogHome.vue'
import myMoment from '../components/MyMoment.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'homepage',
      component: homepage,
      children: [
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
