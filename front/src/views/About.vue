<template>
    <div class="ui top attached segment m-padded-lr-big">
        <h2 class="m-text-500" style="text-align: center">{{ title }}</h2>
        <el-divider></el-divider>
        <div class="typo content m-margin-top-large" v-html="content"></div>
    </div>
</template>

<script>
    import {marked} from "marked";
    import hljs from "highlight.js";

    export default {
        name: 'About',
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
                this.$axios.get("/about").then(res => {
                    console.log(res.data.data)
                    this.title = res.data.data.title;
                    this.content = marked.parse(res.data.data.content);
                })
            }
        }
    }
</script>

<style>
</style>