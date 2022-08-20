<template>
    <el-container class="admin-container">
        <!--头部-->
        <el-header>
            <!--标题-->
            <div>
                <img alt="" height="60" src="../assets/logo.png">
                <span> Silence's Blog 后台管理  </span>
                <el-divider direction="vertical"></el-divider>
                <span><el-link href="/" style="color: white">主页</el-link></span>
            </div>
        </el-header>

        <!--页面主体-->
        <el-container>
            <!--侧边栏-->
            <el-aside :width="isCollapse? '64px' : '190px'">
                <div class="toggle-button" @click="isCollapse=!isCollapse"><i
                        :class="isCollapse?'el-icon-s-unfold':'el-icon-s-fold'"></i></div>
                <!--菜单-->
                <el-menu :collapse="isCollapse" :collapse-transition="false" :default-active="$store.state.activePath"
                         :default-openeds="defaultOpeneds"
                         :router="true" :unique-opened="false" active-text-color="#409eff"
                         background-color="#033649" text-color="#efefef">
                    <!-- 一级菜单 -->
                    <el-submenu v-for="item in menuList" :key="item.id" :index="item.id + ''">
                        <!-- 一级菜单的模板区域 -->
                        <template slot="title">
                            <i :class="iconsObj[item.id]"></i>
                            <span style="font-size: 17px">{{ item.title }}</span>
                        </template>
                        <!-- 二级菜单 -->
                        <el-menu-item v-for="subItem in item.children" :key="subItem.id" :index="subItem.path">
                            <template slot="title">
                                <i :class="iconsObj[subItem.id]"></i>
                                <span>{{ subItem.title }}</span>
                            </template>
                        </el-menu-item>
                    </el-submenu>
                </el-menu>
            </el-aside>
            <!--右侧内容主体-->
            <el-main :class="isCollapse?'m-el-main-width-64':'m-el-main-width-190'">
                <router-view :key="$route.fullPath"/>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
    export default {
        name: "Admin",
        data() {
            return {
                menuList: [
                    {
                        id: 1,
                        title: '博客管理',
                        children: [
                            {
                                id: 11,
                                title: '写文章',
                                path: '/admin/write'
                            },
                            {
                                id: 12,
                                title: '文章管理',
                                path: '/admin/article'
                            },
                            {
                                id: 13,
                                title: '分类管理',
                                path: '/admin/category'
                            },
                            {
                                id: 14,
                                title: '标签管理',
                                path: '/admin/tag'
                            },
                            {
                                id: 15,
                                title: '评论管理',
                                path: '/admin/comments'
                            }
                        ]
                    },
                    {
                        id: 2,
                        title: '页面管理',
                        children: [
                            {
                                id: 21,
                                title: '关于我',
                                path: '/admin/about'
                            }
                        ]
                    },
                    {
                        id: 3,
                        title: '系统管理',
                        children: [
                            {
                                id: 31,
                                title: '用户管理',
                                path: '/admin/userManager'
                            },
                        ]
                    },
                    {
                        id: 4,
                        title: '日志管理',
                        children: [
                            {
                                id: 41,
                                title: '访客日志',
                                path: '/admin/visitorLog'
                            },
                            {
                                id: 42,
                                title: '浏览日志',
                                path: '/admin/browserLog'
                            }
                        ]
                    },
                ],
                iconsObj: {
                    '1': 'el-icon-menu',
                    '2': 'el-icon-document-copy',
                    '3': 'el-icon-s-tools',
                    '4': 'el-icon-document',
                    '11': 'el-icon-edit',
                    '12': 'el-icon-notebook-2',
                    '13': 'el-icon-files',
                    '14': 'el-icon-collection-tag',
                    '15': 'el-icon-chat-dot-round',
                    '21': 'el-icon-view',
                    '31': 'el-icon-user',
                    '41': 'el-icon-alarm-clock',
                    '42': 'el-icon-time',
                },
                //是否折叠
                isCollapse: false,
                //默认打开的菜单
                defaultOpeneds: ['1', '2', '4'],
            }
        },
        methods: {},
        created() {
        },
    }
</script>

<style scoped>
    .el-header {
        background-color: #222222;
        display: flex;
        justify-content: space-between;
        padding-left: 10px;
        align-items: center;
        color: #ffffff;
        font-size: 22px;
        user-select: none;
    }
    .el-header div {
        display: flex;
        align-items: center;
    }
    .el-header .title span {
        margin-left: 15px;
    }
    .el-aside {
        background-color: #033649;
        position: absolute;
        top: 60px;
        bottom: 0;
        user-select: none;
    }
    .el-main {
        background-color: white;
        position: absolute;
        top: 60px;
        bottom: 0;
        right: 0;
        overflow-y: auto;
        overflow-x: hidden;

    }

    /* 二级菜单 */
    .el-menu-item {
        /* !important 表示覆盖父层的样式 */
        background: #062437 !important;
        font-size: 15px;
    }

    .el-aside .el-menu {
        border-right: none;
    }
    .m-el-main-width-190 {
        width: calc(100% - 190px);
    }
    .el-dropdown-menu {
        margin: 7px 0 0 0 !important;
        padding: 0 !important;
        border: 0 !important;
    }
    .m-el-main-width-64 {
        width: calc(100% - 64px);
    }
    .admin-container {
        height: 100%;
    }
    .toggle-button {
        background-color: #033649;
        font-size: 30px;
        line-height: 40px;
        color: #EFEFEF;
        text-align: center;
        cursor: pointer;
    }
    .el-dropdown-link {
        outline-style: none !important;
        outline-color: unset !important;
        height: 100%;
        cursor: pointer;
    }
    .el-main::-webkit-scrollbar-track-piece {
        background-color: transparent;
    }
    .el-main::-webkit-scrollbar-track {
        -webkit-box-shadow: inset 0 0 6px transparent;
        box-shadow: inset 0 0 6px transparent;
        background-color: transparent;
    }
    .el-main::-webkit-scrollbar-thumb {
        -webkit-box-shadow: inset 0 0 6px #48dbfb;
        box-shadow: inset 0 0 6px #48dbfb;
        background-color: #48dbfb;
    }
    .el-aside {
        -ms-overflow-style: none; /* IE10 */
        scrollbar-width: none; /* Firefox */
    }
    .el-aside::-webkit-scrollbar {
        display: none;
    }
    .el-main::-webkit-scrollbar {
        width: 8px;
        height: 5px;
    }
</style>