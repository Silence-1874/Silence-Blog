<template>
    <div class="site">
        <!--顶部导航-->
        <Menu :categoryList="categoryList"></Menu>

        <!--首页大图 只在首页展示-->
        <div>
            <Header v-if="$route.name==='home'"></Header>
        </div>

        <div class="main">
            <div class="m-padded-tb-big">
                <div class="ui container">
                    <div class="ui stackable grid">
                        <!--左侧-->
                        <div class="three wide column">
                            <Introduction></Introduction>
                        </div>
                        <!--中间-->
                        <div class="ten wide column">
                            <keep-alive include="BlogList">
                                <router-view></router-view>
                            </keep-alive>
                        </div>
                        <!--右侧-->
                        <div class="three wide column">
                            <Tags :tagList="tagList"></Tags>
                            <!--只在文章页面显示目录-->
                            <Tocbot v-if="$route.name==='blog'"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--页脚-->
        <Footer/>

    </div>
</template>

<script>
    import Menu from "@/components/Menu";
    import Header from "@/components/Header";
    import Footer from "@/components/Footer";
    import Introduction from "@/components/Introduction";
    import Tags from "@/components/Tags";
    import Tocbot from "@/components/Tocbot";

    export default {
        name: "Index",
        components: {Header, Tocbot, Tags, Menu, Footer, Introduction},
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
                this.$axios.get("/category").then(res => {
                    this.categoryList = res.data.data;
                })
                // 获得标签列表
                this.$axios.get("/tag").then(res => {
                    this.tagList = res.data.data;
                })
            },
        }
    }
</script>

<style scoped>
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