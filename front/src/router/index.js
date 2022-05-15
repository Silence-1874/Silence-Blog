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
    meta: {
      title: '后台管理'
    },
    children: [
      {
        path: '/',
        name: '默认',
        meta: { requireAuth: true },
        component: () => import('../admin/BlogList'),
      },
      {
        path: 'write',
        name: 'BlogWrite',
        component: () => import('../admin/BlogEdit'),
        meta: {
          title: '写博客',
          requireAuth: true
        }
      },
      {
        path: 'edit/:blogId',
        name: 'BlogEdit',
        meta: {
          title: '编辑博客' ,
          requireAuth: true
        },
      component: () => import('../admin/BlogEdit')
      },
      {
        path: 'article',
        name: 'BlogList',
        component: () => import('../admin/BlogList'),
        meta: {
          title: '文章管理',
          requireAuth: true
        }
      },
      {
        path: 'category',
        component: () => import('../admin/CategoryList'),
        meta: {
          title: '分类管理',
          requireAuth: true
        }
      },
      {
        path: 'tag',
        component: () => import('../admin/TagList'),
        meta: {
          title: '标签管理',
          requireAuth: true
        }
      },
      {
        path: 'comments',
        component: () => import('../admin/CommentList'),
        meta: {
          title: '评论管理',
          requireAuth: true
        }
      },
      {
        path: 'about',
        component: () => import('../admin/About'),
        meta: {
          title: '关于我',
          requireAuth: true
        }
      },
      {
        path: 'userManager',
        component: () => import('../admin/UserManager'),
        meta: {
          title: '用户管理',
          requireAuth: true
        }
      },
      {
        path: 'visitLog',
        component: () => import('../admin/VisitLog'),
        meta: {
          title: '访问日志',
          requireAuth: true
        }
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
