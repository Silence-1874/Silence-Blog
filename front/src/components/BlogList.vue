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
                    <!--标题-->
                    <div class="row m-padded-tb-small">
                        <h2 class="ui header m-center m-scaleup">
                            <a href="javascript:;" @click.prevent="toBlog(blog)" class="m-black">{{ blog.title }}</a>
                        </h2>
                    </div>
                    <!--文章简要信息-->
                    <div class="row m-padded-tb-small">
                        <div class="ui horizontal link list m-center">
                            <div class="item m-datetime">
                                <i class="small calendar icon"></i><span>{{ blog.createTime | dateFormat('YYYY-MM-DD')}}</span>
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
                    <router-link :to="`/category/${blog.category.categoryName}`" class="ui orange large ribbon label" style="margin-right: 0">
                        <i class="small folder open icon"></i>
                        <span class="m-text-500" v-text="blog.category.categoryName"></span>
                    </router-link>
                    <!--文章Markdown描述-->
                    <div class="typo m-padded-tb-small line-numbers match-braces rainbow-braces"
                         v-html="blog.content"></div>
                    <!--阅读全文按钮-->
                    <div class="row m-padded-tb-small m-margin-top">
                        <a href="javascript:;" @click.prevent="toBlog(blog)" class="color-btn">阅读全文</a>
                    </div>
                    <!--横线-->
                    <div class="ui section divider m-margin-lr-no"></div>
                    <!--标签-->
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
                this.$store.dispatch('', blog)
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
</style>