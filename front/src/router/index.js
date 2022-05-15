import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'login',
    component: () => import('@/admin/Login'),
    meta: { title: '登录' }
  },
  {
    path: '/admin',
    name: 'admin',
    component: () => import('@/admin/Admin'),
    meta: {
      title: '后台管理'
    },
    children: [
      {
        path: '/',
        redirect: 'article',
        meta: { requireAuth: true },
      },
      {
        path: 'write',
        name: 'write',
        component: () => import('@/admin/BlogEdit'),
        meta: {
          title: '写博客',
          requireAuth: true
        }
      },
      {
        path: 'edit/:blogId',
        name: 'edit',
        component: () => import('@/admin/BlogEdit'),
        meta: {
          title: '编辑博客' ,
          requireAuth: true
        },
      },
      {
        path: 'article',
        name: 'article',
        component: () => import('@/admin/BlogList'),
        meta: {
          title: '文章管理',
          requireAuth: true
        }
      },
      {
        path: 'category',
        name: 'category',
        component: () => import('@/admin/CategoryList'),
        meta: {
          title: '分类管理',
          requireAuth: true
        }
      },
      {
        path: 'tag',
        name: 'tag',
        component: () => import('@/admin/TagList'),
        meta: {
          title: '标签管理',
          requireAuth: true
        }
      },
      {
        path: 'comments',
        name: 'comments',
        component: () => import('@/admin/CommentList'),
        meta: {
          title: '评论管理',
          requireAuth: true
        }
      },
      {
        path: 'about',
        name: 'about',
        component: () => import('@/admin/About'),
        meta: {
          title: '关于我',
          requireAuth: true
        }
      },
      {
        path: 'userManager',
        name: 'userManager',
        component: () => import('@/admin/UserManager'),
        meta: {
          title: '用户管理',
          requireAuth: true
        }
      },
      {
        path: 'visitLog',
        name: 'visitLog',
        component: () => import('@/admin/VisitLog'),
        meta: {
          title: '访问日志',
          requireAuth: true
        }
      },
    ]
  },
  {
    path: '/',
    name: 'index',
    component: () => import('@/views/Index'),
    redirect: '/home',
    children: [
      {
        path: '/home',
        name: 'home',
        component: () => import('@/views/Home'),
        meta: {title: '首页'}
      },
      {
        path: '/archives',
        name: 'archives',
        component: () => import('@/views/Archives'),
        meta: {title: '归档'}
      },
      {
        path: '/blog/:id',
        name: 'blog',
        component: () => import('@/views/Blog'),
        meta: {title: '博客详情'}
      },
      {
        path: '/tag/:name',
        name: 'tag',
        component: () => import('@/views/Tag'),
        meta: {title: '标签'}
      },
      {
        path: '/category/:name',
        name: 'category',
        component: () => import('@/views/Category'),
        meta: {title: '分类'}
      },
      {
        path: '/about',
        name: 'about',
        component: () => import('@/views/About'),
        meta: {title: '关于我'}
      }
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
