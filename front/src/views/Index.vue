<template>
    <div class="site">
        <!--顶部导航-->
        <Menu :categoryList="categoryList"></Menu>

        <!--首页大图 只在首页展示-->
        <div class="header" v-if="$route.name==='home'">
            <Header></Header>
        </div>

        <div class="main" style="padding-top: 3em; padding-bottom: 3em;">
            <div class="m-padded-tb-big">
                <div class="ui container">
                    <div class="ui stackable grid">
                        <!--左侧-->
                        <div class="three wide column">
                            <Introduction></Introduction>
                        </div>
                        <!--中间-->
                        <div class="ten wide column">
                            <keep-alive include="Home">
                                <router-view></router-view>
                            </keep-alive>
                        </div>
                        <!--右侧-->
                        <div class="three wide column">
                            <!--只在文章页面显示目录-->
                            <Tocbot v-if="$route.name==='blog'"/>
                            <SiteInfo></SiteInfo>
                            <Tags :tagList="tagList"></Tags>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--回到顶部-->
        <el-backtop style="box-shadow: none;background: none">
            <i class="el-icon-download" style="font-size: 80px; transform: rotate(180deg);" ></i>
        </el-backtop>

        <!--页脚-->
        <Footer></Footer>
    </div>
</template>

<script>
    import Menu from "@/components/Menu";
    import Header from "@/components/Header";
    import Footer from "@/components/Footer";
    import Introduction from "@/components/Introduction";
    import Tags from "@/components/Tags";
    import Tocbot from "@/components/Tocbot";
    import SiteInfo from "@/components/SiteInfo";
    import {API_CATEGORY} from "@/api/category";
    import {API_TAG} from "@/api/tag";

    export default {
        name: "Index",
        components: {Header, Tocbot, Tags, Menu, Footer, Introduction, SiteInfo},
        data() {
            return {
                categoryList: [],
                tagList: [],
            }
        },
        watch: {
            //路由改变时，页面滚动至顶部
            '$route.path'() {
                this.scrollToTop()
            }
        },
        created() {
            this.getSite();
        },
        mounted() {
            //保存可视窗口大小
            this.$store.commit("SAVE_CLIENT_SIZE", {clientHeight: document.body.clientHeight, clientWidth: document.body.clientWidth})
            window.onresize = () => {
                this.$store.commit("SAVE_CLIENT_SIZE", {clientHeight: document.body.clientHeight, clientWidth: document.body.clientWidth})
            }
        },
        methods: {
            getSite() {
                // 获得分类列表
                API_CATEGORY.list().then(res => {
                    this.categoryList = res.data.data;
                })
                // 获得标签列表
                API_TAG.list().then(res => {
                    this.tagList = res.data.data;
                })
            },
        }
    }
</script>

<style scoped>
    .header {
        height: 800px;
    }

    .site {
        display: flex;
        min-height: 100vh; /* 没有元素时，也把页面撑开至100% */
        flex-direction: column;
    }

    .main {
        margin-top: 40px;
        flex: 1;
    }

    .main .ui.container {
        width: 1400px !important;
        margin-left: auto !important;
        margin-right: auto !important;
    }

    .ui.grid .three.column {
        padding: 0;
    }

    .ui.grid .ten.column {
        padding-top: 0;
    }

</style>