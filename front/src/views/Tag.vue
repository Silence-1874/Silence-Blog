<template>
    <div>
        <div class="ui top segment" style="text-align: center">
            <h2 class="m-text-500">标签 {{ tagName }} 下共 {{ this.total }} 篇文章</h2>
        </div>
        <BlogList :getBlogList="getBlogList" :blogList="blogList" :totalPage="totalPage"/>
    </div>
</template>

<script>
    import BlogList from "@/components/BlogList"
    import {marked} from "marked";
    import {API_BLOG} from "@/api/blog";
    import {API_TAG} from "@/api/tag";

    export default {
        name: "Tag",
        components: {BlogList},
        data() {
            return {
                blogList: [],
                tagId: -1,
                totalPage: 0,
                total: 0,
            }
        },
        watch: {
            //在当前组件被重用时，要重新获取博客列表
            '$route.fullPath'() {
                if (this.$route.name === 'tag') {
                    this.getData()
                }
            }
        },
        async created() {
            this.getData()
        },
        computed: {
            tagName() {
                return this.$route.params.name
            }
        },
        methods: {
            async getData() {
                // 顺序执行异步请求
                await this.getIdByName();
                await this.getBlogList(1);
            },
            async getIdByName() {
                await API_TAG.getByName(this.$route.params.name).then(res => {
                    this.tagId = res.data.data.id;
                })
            },
            async getBlogList(pageNum) {
                await API_BLOG.pageByTagId(pageNum, this.tagId).then(res => {
                    const blogList = res.data.data;
                    if (blogList === null || blogList.length === 0) {
                        this.totalPage = 0;
                    } else {
                        this.totalPage = res.data.data[0].totalPage;
                        this.total = res.data.data[0].totalRecords;
                    }
                    // markdown渲染
                    for (var blog of blogList) {
                        var content = marked.parse(blog.content);
                        blog.content = content;
                    }
                    this.blogList = blogList;
                })
            }
        }
    }
</script>

<style scoped>

</style>