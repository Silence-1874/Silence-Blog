import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'
import './axios.js'
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import './util/dateFormatUtil.js'
import './css/base.css'
import './icons'

Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(mavonEditor)

Vue.prototype.$axios = axios

// 成功消息提示
Vue.prototype.msgSuccess = function (msg) {
  this.$message.success(msg)
}

// 错误消息提示
Vue.prototype.msgError = function (msg) {
  this.$message.error(msg)
}

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
