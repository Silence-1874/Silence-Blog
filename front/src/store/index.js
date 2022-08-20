import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

import {Message, Notification} from "element-ui";
import paopaoMapper from '@/paopaoMapper.json'
import sanitizeHtml from 'sanitize-html'
import {API_COMMENT} from "@/api/comment";

export default new Vuex.Store({
  state: {
    // 介绍
    introduction: {
      avatar: 'https://s2.loli.net/2022/05/16/SKwRkP6OIj39v2y.jpg',
      name: 'Silence',
      signature: "不以物喜，不以己悲。",
    },
    // token
    tokenName: '',
    tokenValue: '',
    userInfo: JSON.parse(sessionStorage.getItem("userInfo")),
    // 评论
    commentQuery: {
      blogId: null,
      pageNum: 1,
      pageSize: 5,
    },
    allComment: 0,
    commentTotalPage: 0,
    comments: [],
    parentCommentId: -1,
    commentForm: {
      content: '',
      nickname: '',
    },
    //博客文章渲染完成的标记
    isBlogRenderComplete: false,
    //可视窗口大小
    clientSize: {
      clientHeight: 0,
      clientWidth: 1080
    }
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
    },

    SAVE_COMMENT_RESULT(state, data) {
      state.allComment = data.allComment
      state.commentTotalPage = data.totalPage
      state.comments = data.comments
    },

    SET_COMMENT_QUERY_BLOG_ID(state, blogId) {
      state.commentQuery.blogId = blogId
    },

    SET_COMMENT_QUERY_PAGE_NUM(state, pageNum) {
      state.commentQuery.pageNum = pageNum
    },

    SET_PARENT_COMMENT_ID(state, parentCommentId) {
      state.parentCommentId = parentCommentId
    },

    SET_IS_BLOG_RENDER_COMPLETE(state, ok) {
      state.isBlogRenderComplete = ok
    },

    SAVE_CLIENT_SIZE(state, clientSize) {
      state.clientSize = clientSize
    },

  },
  actions: {
    getCommentList() {
      function replaceEmoji(comment, emoji) {
        comment.content = comment.content.replace(new RegExp(emoji.reg, 'g'), `<img src="${emoji.src}">`);
      }

      function convertEmoji(comment) {
        paopaoMapper.forEach(emoji => {
          replaceEmoji(comment, emoji);
        })
      }

      const query = this.state.commentQuery;
      // actions里可以直接使用axios
      API_COMMENT.pageByBlogId(query).then(res => {
        if (res.data.isSuccess) {
          let sanitizeHtmlConfig = {
            allowedTags: [],
            allowedAttributes: false,
            disallowedTagsMode: 'recursiveEscape'
          };
          res.data.data.comments.forEach(comment => {
            //转义评论中的html
            comment.content = sanitizeHtml(comment.content, sanitizeHtmlConfig);
            //查找评论中是否有表情
            if (comment.content.indexOf('@[') != -1) {
              convertEmoji(comment);
            }
            const replys = comment.replyComments;
              //转义评论中的html
              replys.forEach(comment => {
                //转义评论中的html
                comment.content = sanitizeHtml(comment.content, sanitizeHtmlConfig);
                //查找评论中是否有表情
                if (comment.content.indexOf('@[') != -1) {
                  convertEmoji(comment);
                }
              })
            }
          );
          this.commit('SAVE_COMMENT_RESULT', res.data.data);
        }
      }).catch(() => {
        Message.error("请求失败")
      });
    },
    submitCommentForm({},userInfo) {
      let form = this.state.commentForm;
      form.blogId = this.state.commentQuery.blogId;
      form.parentCommentId = this.state.parentCommentId;
      form.isAdmin = userInfo === 'admin';
      // 获得评论者的ip和地理位置
      form.ip = returnCitySN['cip'];
      form.city = returnCitySN['cname'];
      // 评论创建时间在后端生成

      // 如果是博主评论,自动填写相关信息
      if (form.isAdmin) {
        form.qq = '2245815651';
        form.avatar = 'https://q1.qlogo.cn/g?b=qq&nk=2245815651&s=100';
        form.nickname = 'Silence';
      } else {
        // 否则，在后端通过QQ号获得名称和头像
      }
      API_COMMENT.add(form).then(res => {
        if (res.data.isSuccess) {
          Notification({
            title: res.data.msg,
            type: 'success'
          })
          this.commit('SET_PARENT_COMMENT_ID', -1)
          this.dispatch('getCommentList')
          // 回复输入框的昵称恢复为qq号
          form.nickname = res.data.data;
        } else {
          Notification({
            title: '评论失败',
            message: res.data.msg,
            type: 'error'
          })
        }
      }).catch(() => {
        Notification({
          title: '评论失败',
          message: '异常错误',
          type: 'error'
        })
      })
    }
  },
  modules: {
  }
})
