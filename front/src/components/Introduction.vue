<template>
    <div>
        <div class="ui segments m-box">
            <div class="ui card" style="background: #fffaf4;">
                <!-- 名片头像 -->
                <div class="image">
                    <img :src="introduction.avatar">
                </div>

                <!-- 名称 -->
                <div class="content" align="center">
                    <div style="margin-bottom: 5px; font-size: x-large; font-family: Bold">
                        {{ introduction.name }}
                    </div>
                </div>

                <!-- 签名 -->
                <div class="content" align="center">
                    {{ introduction.signature }}
                </div>

                <!-- 网站图标及链接 -->
                <div class="extra content" align="center" style="font-size: 24px">
                    <a class="site-icon" href="https://github.com/Silence-Zoe">
                        <svg-icon icon-class="github"></svg-icon>
                    </a>
                    <a class="site-icon" href="https://www.zhihu.com/people/xie-lin-feng-63-55" style="margin-right: 10px">
                        <svg-icon icon-class="zhihu"></svg-icon>
                    </a>
                    <a class="site-icon" href="https://www.douban.com/people/181279082/?_i=2699943nzmqZw5">
                        <svg-icon icon-class="douban"></svg-icon>
                    </a>
                    <a class="site-icon" href="https://space.bilibili.com/111044705?spm_id_from=333.1007.0.0">
                        <svg-icon icon-class="bilibili"></svg-icon>
                    </a>
                    <a class="site-icon" href="https://juejin.cn/user/4372134640489560" style="margin-right: 10px">
                        <svg-icon icon-class="juejin"></svg-icon>
                    </a>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import {mapState} from 'vuex'

    export default {
        name: "Introduction",
        computed: {
            ...mapState(['introduction'])
        },
        methods: {
        }
    }
</script>

<style>
    /*网站图标之间的间距*/
    .site-icon {
        margin-right: 10px;
    }

    .el-collapse-item {
        padding-left: 15px;
        padding-right: 15px;
    }

    .el-collapse-item .el-collapse-item__content {
        padding-bottom: 10px;
    }
</style>