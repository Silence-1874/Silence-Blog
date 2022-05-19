<template>
    <div>
        <!--content-->
        <div class="ui padded attached segment m-padded-tb-large m-margin-bottom-big m-box" v-for="blog in blogList"
             :key="blog.id">
            <div class="ui big red right corner label" v-if="blog.isTop" style="padding: 8px">
                <span style="margin-left: 20px; font-size: 15px">置顶</span>
            </div>
            <div class="ui middle aligned mobile reversed stackable">
                <div class="ui grid m-margin-lr">
                    <!-- 标题 -->
                    <div class="row m-padded-tb-small">
                        <h2 class="ui header m-center m-scaleup">
                            <a href="javascript:;" @click.prevent="toBlog(blog)" class="m-black">{{ blog.title }}</a>
                        </h2>
                    </div>
                    <!-- 文章简要信息 -->
                    <div class="row m-padded-tb-small">
                        <div class="ui horizontal link list m-center">
                            <div class="item m-datetime">
                                <i class="small calendar icon"></i><span>{{ blog.createTime | dateFormat('YYYY-MM-DD')}}</span>
                            </div>
                            <div class="item m-views">
                                <i class="small eye icon"></i><span>{{ blog.views }}</span>
                            </div>
                            <div class="item" style="color: #0eb389">
                                <i class="small pencil alternate icon"></i><span>字数≈{{ blog.words }}字</span>
                            </div>
                            <div class="item" style="color: #ffaa00">
                                <i class="small clock icon"></i><span>阅读时长≈{{ blog.readTime }}分</span>
                            </div>
                        </div>
                    </div>
                    <el-divider style="margin: 0"></el-divider>
                    <!-- 分类 -->
                    <router-link :to="`/category/${blog.category.categoryName}`" class="ui teal large ribbon label" style="margin-right: 0">
                        <i class="small folder open icon"></i>
                        <span class="m-text-500" v-text="blog.category.categoryName"></span>
                    </router-link>
                    <!-- 文章Markdown描述，截取正文的前200个字符-->
                    <div class="typo m-padded-tb-small line-numbers match-braces rainbow-braces"
                         v-html="blog.content.substring(0, 200)"></div>
                    <!-- 阅读全文按钮 -->
                    <div class="row m-padded-tb-small m-margin-top">
                        <a href="javascript:;" @click.prevent="toBlog(blog)" class="color-btn">阅读全文</a>
                    </div>
                    <!-- 横线 -->
                    <div class="ui section divider m-margin-lr-no"></div>
                    <!-- 标签 -->
                    <div class="row m-padded-tb-no">
                        <div class="column m-padding-left-no">
                            <router-link :to="`/tag/${tag.tagName}`" class="ui tag label m-text-500 m-margin-small"
                                         :class="tag.color" v-for="(tag,index) in blog.tagList" :key="index">
                                {{ tag.tagName }}
                            </router-link>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--分页-->
        <div class="ui bottom" style="text-align:center">
            <el-pagination @current-change="handleCurrentChange" :current-page="pageNum" :page-count="totalPage"
                           layout="prev, pager, next" background hide-on-single-page>
            </el-pagination>
        </div>
    </div>
</template>

<script>
    import {mapState} from "vuex";

    export default {
        name: "BlogList",
        computed: {
            ...mapState(['clientSize'])
        },
        data() {
            return {
                pageNum: 1,
            }
        },
        props: {
            getBlogList: {
                type: Function,
                required: true
            },
            blogList: {
                type: Array,
                required: true
            },
            totalPage: {
                type: Number,
                required: true
            }
        },
        methods: {
            //监听页码改变的事件
            handleCurrentChange(newPage) {
                //如果是首页，则滚动至Header下方
                if (this.$route.name === 'home') {
                    window.scrollTo({top: this.clientSize.clientHeight, behavior: 'smooth'})
                } else {
                    //其它页面（分类和标签页）滚动至顶部
                    this.scrollToTop()
                }
                this.pageNum = newPage
                this.getBlogList(newPage)
            },

            toBlog(blog) {
                this.$router.push(`/blog/${blog.id}`)
            }
        }
    }
</script>

<style scoped>
    .el-pagination.is-background .btn-next, .el-pagination.is-background .btn-prev, .el-pagination.is-background .el-pager li {
        background-color: #ffffff !important;
    }

    .el-pagination.is-background .el-pager li:not(.disabled).active {
        background-color: #409EFF !important;
    }

    /* 用自定义的颜色覆盖semanticUI自带的颜色 */
    .ui.orange.label, .ui.orange.labels .label .ui.orange.labels .label:hover, a.ui.orange.label:hover {
        background-color: #ff9b6a !important;
        border-color: #ff9b6a !important;
        color: #fff!important;
    }
    .ui.yellow.label, .ui.yellow.labels .label .ui.yellow.labels .label:hover, a.ui.yellow.label:hover {
        background-color: #fecf45 !important;
        border-color: #fecf45 !important;
        color: #fff!important;
    }
    .ui.green.label, .ui.green.labels .label .ui.green.labels .label:hover, a.ui.green.label:hover {
        background-color: #849965 !important;
        border-color: #849965 !important;
        color: #fff!important;
    }
    .ui.blue.label, .ui.blue.labels .label .ui.blue.labels .label:hover, a.ui.blue.label:hover {
        background-color: #b4c6fb !important;
        border-color: #b4c6fb !important;
        color: #fff!important;
    }
    .ui.violet.label, .ui.violet.labels .label .ui.violet.labels .label:hover, a.ui.violet.label:hover {
        background-color: #6f5f90 !important;
        border-color: #6f5f90 !important;
        color: #fff!important;
    }
    .ui.pink.label, .ui.pink.labels .label .ui.pink.labels .label:hover, a.ui.pink .label:hover {
        background-color: #ff7b89 !important;
        border-color: #ff7b89 !important;
        color: #fff!important;
    }
    .ui.brown.label, .ui.brown.labels .label .ui.brown.labels .label:hover, a.ui.brown.label:hover {
        background-color: #e37c5b !important;
        border-color: #e37c5b !important;
        color: #fff!important;
    }
    .ui.grey.label, .ui.grey.labels .label .ui.grey.labels .label:hover, a.ui.grey.label:hover {
        background-color: #8e90a5 !important;
        border-color: #8e90a5 !important;
        color: #fff!important;
    }
</style>