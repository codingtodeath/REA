import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: '排行',
    component: () => import('../views/RssFeeds.vue')
  },
  {
    path: '/ArticleView',
    name: '最新',
    // route level code-splitting
    // this generates a separate chunk (Newest.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/ArticleView.vue')
  },
  {
    path: '/SaveDirection',
    name: '随机',
    component: () => import('../views/SaveDirection.vue')
  }
]

const router = new VueRouter({
  routes
})

export default router


