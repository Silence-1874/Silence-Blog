import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    tokenName: '',
    tokenValue: '',
    userInfo: JSON.parse(sessionStorage.getItem("userInfo"))
  },
  getters: {
    getUser: state => {
      return state.userInfo;
    }
  },
  mutations: {
    SET_TOKEN_NAME: (state, tokenName) => {
      state.tokenName = tokenName;
      localStorage.setItem("tokenName", tokenName);
    },

    SET_TOKEN_VALUE: (state, tokenValue) => {
      state.token = tokenValue;
      localStorage.setItem("tokenValue", tokenValue);
    },

    SET_USERINFO: (state, userInfo) => {
      state.userInfo = userInfo;
      localStorage.setItem("userInfo", userInfo);
    },

    REMOVE_INFO: (state) => {
      state.tokenName = '';
      state.tokenValue = '';
      state.userInfo = {};
      localStorage.setItem("tokenName", '');
      localStorage.setItem("tokenValue", '');
      localStorage.setItem("userInfo", '');
    }

  },
  actions: {
  },
  modules: {
  }
})
