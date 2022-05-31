<template>
    <div>
        <div class="ui top attached segment" style="text-align: center;">
            <h2 class="m-text-500">文章归档</h2>
            <p>好! 目前共计 {{ count }} 篇日志。 继续努力。</p>
        </div>
        <div class="ui attached segment">
            <div class="timeline">
                <div :class="colorObj[key%5]" v-for="(value,key) in blogMap" :key="value[0]">
                    <div class="tl-header">
                        <a class="ui large label m-text-500">{{ value[0] }}</a>
                    </div>
                    <div class="tl-item" v-for="blog in value[1]" :key="blog.id">
                        <div class="tl-wrap">
                            <span class="tl-date">{{ blog.day }}</span>
                            <a href="javascript:;" @click.prevent="toBlog(blog)">
                                <div class="ui left pointing label tl-title">{{ blog.title }}</div>
                            </a>
                        </div>
                    </div>
                </div>

                <div class="tl-header">
                    <a class="ui red large label m-text-500">Hello World!</a>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Archives",
        data() {
            return {
                blogMap: [],
                count: 0,
                colorObj: {
                    0: 'tl-blue',
                    1: 'tl-pink',
                    2: 'tl-green',
                    3: 'tl-purple',
                    4: 'tl-yellow',
                }
            }
        },
        created() {
            this.getArchives();
            this.getCount();
        },
        methods: {
            getArchives() {
                this.$axios.get("/archives").then(res => {
                    var list = Object.entries(res.data.data);
                    list.sort((a, b) => b[0].localeCompare(a[0]))
                    this.blogMap = list;
                })
            },
            getCount() {
                this.$axios.get("/countBlog").then(res => {
                    this.count = res.data.data;
                })
            },
            toBlog(blog) {
                this.$router.push(`/blog/${blog.id}`)
            }
        }
    }
</script>

<style scoped>
    .timeline {
        margin: 20px 0;
    }

    .tl-header {
        width: 12em;
        text-align: center;
    }

    .tl-date {
        position: relative;
        top: 10px;
        display: block;
        float: left;
        width: 4.5em;
        margin-left: -7.5em;
        text-align: right;
    }

    .tl-wrap {
        padding: 15px 0 15px 20px;
        margin-left: 6em;
        border-style: solid;
        border-width: 0 0 0 4px;
    }

    .tl-wrap:before {
        position: relative;
        top: 15px;
        float: left;
        width: 10px;
        height: 10px;
        margin-left: -27px;
        background: #fff;
        border-color: inherit;
        border-style: solid;
        border-width: 3px;
        border-radius: 50%;
        content: "";
        box-shadow: 0 0 0 4px #fff;
    }

    .tl-wrap:hover:before {
        background: 0 0;
        border-color: #fff;
    }

    .tl-title {
        margin-left: 0 !important;
        letter-spacing: 0.3px !important;
        font-size: 14px !important;
        font-weight: 500 !important;
        padding: 12px 15px !important;
    }

    .tl-blue .tl-header a, .tl-blue .tl-item .tl-title {
        background: #a0d8fd!important;
        color: #fff !important;
    }

    .tl-blue .tl-item .tl-wrap {
        border-color: #a0d8fd;
    }

    .tl-green .tl-header a, .tl-green .tl-item .tl-title {
        background: #9be99e !important;
        color: #fff !important;
    }

    .tl-green .tl-item .tl-wrap {
        border-color: #9be99e;
    }

    .tl-purple .tl-header a, .tl-purple .tl-item .tl-title {
        background: #d89ac9 !important;
        color: #fff !important;
    }

    .tl-purple .tl-item .tl-wrap {
        border-color: #d89ac9;
    }

    .tl-pink .tl-header a, .tl-pink .tl-item .tl-title {
        background: #ff7b89!important;
        color: #fff !important;
    }

    .tl-pink .tl-item .tl-wrap {
        border-color: #ff7b89;
    }

    .tl-yellow .tl-header a, .tl-yellow .tl-item .tl-title {
        background: #fcdf67 !important;
        color: #fff !important;
    }

    .tl-yellow .tl-item .tl-wrap {
        border-color: #fcdf67;
    }

</style>