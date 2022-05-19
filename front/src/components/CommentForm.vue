<template>
    <!-- 评论输入表单 -->
    <div class="form">
        <h3> 发表评论
            <el-button class="m-small" size="mini" type="primary"
                       @click="$store.commit('SET_PARENT_COMMENT_ID', -1)"
                       v-show="parentCommentId !== -1">
                取消回复
            </el-button>
        </h3>
        <el-form :inline="true" :model="commentForm" :rules="formRules" ref="formRef" size="small">
            <el-input :class="'textarea'" type="textarea" :rows="5"
                      v-model="commentForm.content" placeholder="评论千万条，友善第一条"
                      maxlength="250" show-word-limit :validate-event="false">
            </el-input>
            <!-- 表情框 -->
            <div class="el-form-item el-form-item--small emoji">
                <img src="../assets/huaji.png" @click="showEmojiBox">
                <div class="mask" v-show="emojiShow" @click="hideEmojiBox"></div>
                <div class="emoji-box" v-show="emojiShow">
                    <div class="emoji-wrap">
                        <div class="emoji-list" v-for="(img,index) in paopaoMapper" :key="index" @click="insertEmoji(img.name)">
                            <img :src="img.src" :title="img.name">
                        </div>
                    </div>
                </div>
            </div>
            <!-- 昵称输入框 -->
            <el-form-item prop="nickname">
                <el-popover ref="nicknamePopover" placement="bottom" trigger="focus" content="输入QQ号将自动获取QQ头像和名称"></el-popover>
                <el-input v-model="commentForm.nickname" placeholder="输入昵称将获取随机头像"
                          :validate-event="false" v-popover:nicknamePopover>
                    <i slot="prefix" class="el-input__icon el-icon-user"></i>
                </el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" size="medium" @click="postForm">发表评论</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import {mapState} from 'vuex'
    import paopaoMapper from '@/paopaoMapper.json'

    export default {
        name: "CommentForm",
        computed: {
            ...mapState(['parentCommentId', 'commentForm', 'commentQuery'])
        },
        data() {
            return {
                formRules: {
                    nickname: [
                        {required: true, message: '请输入评论昵称'},
                        {max: 18, message: '昵称不可多于15个字符'}
                    ],
                },
                emojiShow: false,
                paopaoMapper: [],
                textarea: null,
                start: 0,
                end: 0,
            }
        },
        created() {
            this.paopaoMapper = paopaoMapper
        },
        mounted() {
            this.textarea = document.querySelector('.el-form textarea')
        },
        methods: {
            showEmojiBox() {
                this.start = this.textarea.selectionStart
                this.end = this.textarea.selectionEnd
                this.textarea.focus()
                this.textarea.setSelectionRange(this.start, this.end)
                this.emojiShow = !this.emojiShow
            },
            insertEmoji(name) {
                let str = this.commentForm.content
                this.commentForm.content = str.substring(0, this.start) + name + str.substring(this.end)
                this.start += name.length
                this.end = this.start
                this.textarea.focus()
                this.$nextTick(() => {
                    this.textarea.setSelectionRange(this.start, this.end)
                })
            },
            hideEmojiBox() {
                this.emojiShow = false
                this.textarea.focus()
                this.textarea.setSelectionRange(this.start, this.end)
            },
            postForm() {
                const userInfo = window.localStorage.getItem('userInfo')
                // 博主登录后,userInfo为admin，无需填写昵称即可评论
                if (userInfo === 'admin') {
                    if (this.commentForm.content === '' || this.commentForm.content.length > 250) {
                        return this.$notify({
                            title: '评论失败',
                            message: '评论内容有误',
                            type: 'warning'
                        })
                    } else {
                        return this.$store.dispatch('submitCommentForm', userInfo);
                    }
                }
                this.$refs.formRef.validate(valid => {
                    if (!valid || this.commentForm.content === '' || this.commentForm.content.length > 250) {
                        this.$notify({
                            title: '评论失败',
                            message: '请正确填写评论',
                            type: 'warning'
                        })
                    } else {
                        this.$store.dispatch('submitCommentForm', userInfo)
                    }
                })
            }
        }
    }
</script>

<style scoped>
    .form {
        background: #fff;
        position: relative;
    }

    .form h3 {
        margin: 5px;
        font-weight: 500 !important;
    }

    .form .m-small {
        margin-left: 5px;
        padding: 4px 5px;
    }

    .el-form .textarea {
        margin-top: 5px;
        margin-bottom: 15px;
    }

    .el-form textarea {
        padding: 6px 8px;
    }

    .el-form textarea, .el-form input {
        color: black;
    }

    .el-form .el-form-item__label {
        padding-right: 3px;
    }

    .emoji {
        margin-right: 5px;
        position: relative;
        user-select: none;
    }

    .emoji > img {
        cursor: pointer;
        transition: all 0.3s ease-in-out;
        -webkit-transition: all 0.3s ease-in-out;
        -moz-transition: all 0.3s ease-in-out;
        -o-transition: all 0.3s ease-in-out;
    }

    .emoji > img:hover {
        transform: rotate(360deg);
        -webkit-transform: rotate(360deg);
        -moz-transform: rotate(360deg);
        -o-transform: rotate(360deg);
    }

    .emoji-box {
        color: #222;
        overflow: visible;
        background: #fff;
        border: 1px solid #E5E9EF;
        box-shadow: 0 11px 12px 0 rgba(106, 115, 133, 0.3);
        border-radius: 8px;
        width: 340px;
        position: absolute;
        top: 40px;
        z-index: 100;
    }

    .emoji-box * {
        box-sizing: content-box;
    }

    .emoji-box .emoji-title {
        font-size: 12px;
        line-height: 16px;
        margin: 13px 15px 0;
        color: #757575;
    }

    .emoji-box .emoji-wrap {
        margin: 6px 11px 0 11px;
        height: 155px;
        overflow: auto;
        word-break: break-word;
    }

    .emoji-box .emoji-wrap .emoji-list {
        height: 33px;
        color: #111;
        border-radius: 4px;
        transition: background 0.2s;
        display: inline-block;
        outline: 0;
        cursor: pointer;
    }

    .emoji-box .emoji-wrap .emoji-list:hover {
        background-color: #ddd;
    }

    .emoji-box .emoji-wrap .emoji-list img {
        margin: 4px;
        width: 25px;
        height: 25px;
    }

    .emoji-box .emoji-tabs {
        position: relative;
        height: 36px;
        overflow: hidden;
        background-color: #f4f4f4;
        border-radius: 0 0 4px 4px;
    }

    .emoji-box .emoji-tabs .tab-link {
        cursor: pointer;
        float: left;
        padding: 7px 18px;
        width: 22px;
        height: 22px;
    }

    .emoji-box .emoji-tabs .tab-link.on {
        background-color: #fff;
    }

    .emoji-box .emoji-tabs .tab-link img {
        width: 22px;
    }

    .emoji-box .emoji-tabs .tab-link:hover {
        background: #e7e7e7;
    }

    .mask {
        pointer-events: auto;
        position: fixed;
        z-index: 99;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
    }
</style>