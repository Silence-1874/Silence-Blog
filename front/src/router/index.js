import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../admin/Login'),
    meta: { title: '登录' }
  },
  {
    path: '/admin',
    name: 'Admin',
    component: () => import('../admin/Admin'),
    meta: { title: '后台管理' },
    children: [
      {
        path: 'write',
        name: 'BlogWrite',
        component: () => import('../admin/BlogEdit'),
        meta: { title: '写博客' }
      },
      {
        path: 'edit/:blogId',
        name: 'BlogEdit',
        meta: { title: '编辑博客' },
        component: () => import('../admin/BlogEdit')
      },
      {
        path: 'article',
        name: 'BlogList',
        component: () => import('../admin/BlogList'),
        meta: { title: '文章管理' }
      },
      {
        path: 'category',
        component: () => import('../admin/CategoryList'),
        meta: { title: '分类管理' }
      },
      {
        path: 'tag',
        component: () => import('../admin/TagList'),
        meta: { title: '标签管理' }
      },
      {
        path: 'comments',
        component: () => import('../admin/CommentList'),
        meta: { title: '评论管理' }
      },
      {
        path: 'userManager',
        component: () => import('../admin/UserList'),
        meta: { title: '用户管理' }
      },
    ]
  },
  {
    path: '/',
    name: 'home',
    component: () => import('../views/Home')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
