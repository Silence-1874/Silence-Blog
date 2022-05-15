import axios from "axios";
import Element from "element-ui";

axios.defaults.baseURL = "http://localhost:8181"

// 前置拦截
axios.interceptors.request.use(config => {
    // 有token则带上token
    const tokenName = window.localStorage.getItem('tokenName');
    const tokenValue = window.localStorage.getItem('tokenValue');
    if (tokenName != undefined && tokenName != '') {
        config.headers[tokenName] = tokenValue;
    };

    // 前端鉴权，对"/admin"路径下的所有请求，拦截所有非get方式的
    const role = window.localStorage.getItem("userInfo");
    if (config.url.slice(0, 6) === "/admin" && config.method !== "get" && role !== "admin") {
        Element.Message({
            message: "没有此权限！",
            type: 'error',
            duration: 2 * 1000
        })
        // 直接拦截此次请求
        return;
    };

    return config;
});

axios.interceptors.response.use(response => {
    const res = response.data;
    if (!res.isSuccess) {
        Element.Message({
            message: res.msg,
            type: 'error',
            duration: 2 * 1000
        })
    }
    return response;
})

