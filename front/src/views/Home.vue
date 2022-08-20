<template>
	<div>
		<BlogList :getBlogList="getBlogList" :blogList="blogList" :totalPage="totalPage"/>
	</div>
</template>

<script>
	import BlogList from "@/components/BlogList";
	import {marked} from "marked";
	import hljs from "highlight.js";
	import {API_BLOG} from "@/api/blog";

	export default {
		name: "Home",
		components: {BlogList},
		data() {
			return {
				blogList: [],
				totalPage: 0,
			}
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
		created() {
			this.getBlogList(1);
		},
		methods: {
			// 获得博客列表
			getBlogList(pageNum) {
				API_BLOG.page(pageNum, null).then(res => {
					const blogList = res.data.data;
					if (blogList === null || blogList.length === 0) {
						this.totalPage = 0;
					} else {
						this.totalPage = res.data.data[0].totalPage;
					}
					// markdown渲染
					for (var blog of blogList) {
						var content = marked.parse(blog.content);
						blog.content = content;
					}
					this.blogList = blogList;
				})
			},

		}
	}
</script>

<style scoped>

</style>