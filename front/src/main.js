import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import 'semantic-ui-css/semantic.min.css'
import './axios.js'
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import './util/dateFormatUtil.js'
import './css/base.css'
import './icons'
import './permission'
import 'highlight.js/styles/github.css'
import 'github-markdown-css/github-markdown-light.css'
import './css/github-mardown.css'
import {API_VISITOR} from "@/api/visitor";

Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(mavonEditor)

// 每次路由更新时更新pv
router.afterEach((to, from, next) => {
  // 博主本人不贡献pv
  if (localStorage.getItem("userInfo") !== 'admin') {
    const ip = returnCitySN['cip'];
    const city = returnCitySN['cname'];
    API_VISITOR.updateByIp(ip, city);
  }
})

// 成功消息提示
Vue.prototype.msgSuccess = function (msg) {
  this.$message.success(msg)
}

// 错误消息提示
Vue.prototype.msgError = function (msg) {
  this.$message.error(msg)
}

//滚动至页面顶部
const cubic = value => Math.pow(value, 3);
const easeInOutCubic = value => value < 0.5 ? cubic(value * 2) / 2 : 1 - cubic((1 - value) * 2) / 2;
Vue.prototype.scrollToTop = function () {
  const el = document.documentElement
  const beginTime = Date.now()
  const beginValue = el.scrollTop
  const rAF = window.requestAnimationFrame || (func => setTimeout(func, 16))
  const frameFunc = () => {
    const progress = (Date.now() - beginTime) / 500;
    if (progress < 1) {
      el.scrollTop = beginValue * (1 - easeInOutCubic(progress))
      rAF(frameFunc)
    } else {
      el.scrollTop = 0
    }
  }
  rAF(frameFunc)
}


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
