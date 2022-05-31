<template>
    <div>
        <div class="ui padded attached segment m-padded-tb-large">
            <div class="ui big red right corner label" v-if="blog.isTop" style="padding: 8px">
                <span style="margin-left: 20px; font-size: 15px">置顶</span>
            </div>
            <div class="ui middle aligned mobile reversed stackable">
                <div class="ui grid m-margin-lr">
                    <!--标题-->
                    <div class="row m-padded-tb-small">
                        <h2 class="ui header m-center">{{ blog.title }}</h2>
                    </div>
                    <!--文章简要信息-->
                    <div class="row m-padded-tb-small">
                        <div class="ui horizontal link list m-center">
                            <div class="item m-datetime">
                                <i class="small calendar icon"></i><span>{{ blog.createTime | dateFormat('YYYY-MM-DD') }}</span>
                            </div>
                            <div class="item m-views">
                                <i class="small eye icon"></i><span>{{ blog.views }}</span>
                            </div>
                            <div class="item m-common-black">
                                <i class="small pencil alternate icon"></i><span>字数≈{{ blog.words }}字</span>
                            </div>
                            <div class="item m-common-black">
                                <i class="small clock icon"></i><span>阅读时长≈{{ blog.readTime }}分</span>
                            </div>
                        </div>
                    </div>
                    <!--分类-->
                    <router-link :to="`/category/${blog.category.categoryName}`" class="ui orange large ribbon label" v-if="blog.category">
                        <i class="small folder open icon"></i><span class="m-text-500">{{ blog.category.categoryName }}</span>
                    </router-link>
                    <!--文章Markdown正文-->
                    <div class="typo js-toc-content m-padded-tb-small match-braces rainbow-braces markdown-body" v-html="blog.content"></div>
                    <!--横线-->
                    <el-divider></el-divider>
                    <!--标签-->
                    <div class="row m-padded-tb-no">
                        <div class="column m-padding-left-no">
                            <router-link :to="`/tag/${tag.tagName}`" class="ui tag label m-text-500 m-margin-small" :class="tag.color" v-for="(tag,index) in blog.tagList" :key="index">{{ tag.tagName }}</router-link>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--博客信息-->
        <div class="ui attached positive message">
            <ul class="list">
                <li>作者：Silence
                    <router-link to="/about"></router-link>
                </li>
                <li>发表时间：{{ blog.createTime | dateFormat('YYYY-MM-DD HH:mm') }}</li>
                <li>最后修改：{{ blog.updateTime | dateFormat('YYYY-MM-DD HH:mm') }}</li>
                <li>本站点采用<a href="https://creativecommons.org/licenses/by/4.0/" target="_blank"> 署名 4.0 国际 (CC BY 4.0) </a>创作共享协议。可自由转载、引用，并且允许商业性使用。但需署名作者且注明文章出处。</li>
            </ul>
        </div>
        <!--评论-->
        <div class="ui bottom teal attached segment threaded comments">
            <CommentList :blogId="blogId"></CommentList>
        </div>
    </div>
</template>

<script>
    import {marked} from "marked";
    import hljs from "highlight.js";
    import CommentList from "@/components/CommentList";

    export default {
        name: "Blog",
        components: {CommentList},
        data() {
            return {
                blog: {},
            }
        },
        computed: {
            blogId() {
                return parseInt(this.$route.params.id)
            },
        },
        mounted() {
            marked.setOptions({
                renderer: new marked.Renderer(),
                highlight: function (code, lang) {
                    const language = hljs.getLanguage(lang) ? lang : 'plaintext'
                    return hljs.highlight(code, {language}).value
                },
                langPrefix: 'hljs language-',
                pedantic: false,
                gfm: true,
                breaks: false,
                sanitize: false,
                smartLists: true,
                smartypants: false,
                xhtml: false
            })
        },
        beforeRouteEnter(to, from, next) {
            //路由到博客文章页面之前，应将文章的渲染完成状态置为 false
            next(vm => {
                // 当 beforeRouteEnter 钩子执行前，组件实例尚未创建
                // vm 就是当前组件的实例，可以在 next 方法中把 vm 当做 this用
                vm.$store.commit('SET_IS_BLOG_RENDER_COMPLETE', false)
            })
        },
        beforeRouteLeave(to, from, next) {
            // 从文章页面路由到其它页面时，销毁当前组件的同时，要销毁tocbot实例
            // 否则tocbot一直在监听页面滚动事件，而文章页面的锚点已经不存在了，会报"Uncaught TypeError: Cannot read property 'className' of null"
            tocbot.destroy()
            next()
        },
        beforeRouteUpdate(to, from, next) {
            // 一般有两种情况会触发这个钩子
            // ①当前文章页面跳转到其它文章页面
            // ②点击目录跳转锚点时，路由hash值会改变，导致当前页面会重新加载，这种情况是不希望出现的
            // 在路由 beforeRouteUpdate 中判断路径是否改变
            // 如果跳转到其它页面，to.path!==from.path 就放行 next()
            // 如果是跳转锚点，path不会改变，hash会改变，to.path===from.path, to.hash!==from.path 不放行路由跳转，就能让锚点正常跳转
            if (to.path !== from.path) {
                //在当前组件内路由到其它博客文章时，要重新获取文章
                this.getBlog(to.params.id)
                //只要路由路径有改变，且停留在当前Blog组件内，就把文章的渲染完成状态置为 false
                this.$store.commit('SET_IS_BLOG_RENDER_COMPLETE', false)
                next()
            }
        },
        created() {
            // 每次组件创建时当前文章浏览量加1，博主本人不贡献浏览量
            if (localStorage.getItem("userInfo") !== 'admin') {
                this.$axios.get("/blogView/" + this.blogId);
            }
            this.getBlog();
        },
        methods: {
            getBlog() {
                this.$axios.get("blog/" + this.blogId).then(res => {
                    this.blog = res.data.data;
                    // markdown渲染
                    var content = marked.parse(this.blog.content);
                    this.blog.content = content;
                    this.$nextTick(() => {
                        this.$store.commit('SET_IS_BLOG_RENDER_COMPLETE', true);
                    })
                })
            },
        }
    }
</script>

<style scoped>
    .el-divider {
        margin: 1rem 0 !important;
    }

    h1::before, h2::before, h3::before, h4::before, h5::before, h6::before {
        display: block;
        content: " ";
        height: 55px;
        margin-top: -55px;
        visibility: hidden;
    }
</style>