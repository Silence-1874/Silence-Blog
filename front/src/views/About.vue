<template>
    <div class="ui top attached segment m-padded-lr-big;" style="background: #fffaf4">
        <h2 class="m-text-500" style="text-align: center">{{ title }}</h2>
        <el-divider></el-divider>
        <div class="typo content m-margin-top-large markdown-body" v-html="content"></div>

        <!--评论-->
        <div class="ui bottom teal attached segment threaded comments">
            <CommentList :blogId="0"></CommentList>
        </div>
    </div>
</template>

<script>
    import {marked} from "marked";
    import hljs from "highlight.js";
    import CommentList from "@/components/CommentList";
    import {API_ABOUT} from "@/api/about";

    export default {
        name: 'About',
        components:{CommentList},
        data() {
            return {
                title: '',
                content: '',
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
            this.getData()
        },
        methods: {
            getData() {
                API_ABOUT.get().then(res => {
                    this.title = res.data.data.title;
                    this.content = marked.parse(res.data.data.content);
                });
            }
        }
    }
</script>

<style>
</style>