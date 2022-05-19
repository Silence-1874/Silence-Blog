<template>
    <div ref="menu" class="ui fixed inverted stackable pointing menu" :class="{'transparent':$route.name==='home' && clientSize.clientWidth > 768}"
                    style="background: #2a4b8e">
        <div class="ui container">
            <router-link to="/">
                <h2 class="ui header item" style="color: #f65066; font-family: 微软雅黑" >
                    <span>Silence's Blog</span>
                </h2>
            </router-link>

            <router-link to="/home" class="item" :class="{'active':$route.name==='home'}">
                <i class="home icon"></i>首页
            </router-link>

            <el-dropdown trigger="click" @command="categoryRoute">
				<span class="el-dropdown-link item" :class="{'active':$route.name==='category'}">
					<i class="idea icon"></i>分类<i class="caret down icon"></i>
				</span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item :command="category.categoryName" v-for="(category,index) in categoryList" :key="index">{{ category.categoryName }}</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>

            <router-link to="/archives" class="item" :class="{'active':$route.name==='archives'}">
                <i class="clone icon"></i>归档
            </router-link>

            <router-link to="/about" class="item" :class="{'active':$route.name==='about'}">
                <i class="info icon"></i>关于我
            </router-link>

            <!--<el-autocomplete v-model="queryString" :fetch-suggestions="debounceQuery" placeholder="搜索文章标题或内容"-->
            <el-autocomplete v-model="queryString" :fetch-suggestions="debounceQuery" placeholder="搜索功能暂未实现"
                             class="right item m-search"
                             popper-class="m-search-item" @select="handleSelect">
                <i class="search icon el-input__icon" slot="suffix"></i>
                <template slot-scope="{ item }">
                    <div class="title">{{ item.title }}</div>
                    <span class="content">{{ item.content }}</span>
                </template>
            </el-autocomplete>
        </div>
    </div>
</template>

<script>
    import {mapState} from 'vuex'

    export default {
        name: "Menu",
        props: {
            categoryList: {
                type: Array,
                required: true
            },
        },
        data() {
            return {
                queryString: '',
                queryResult: [],
                timer: null
            }
        },
        computed: {
            ...mapState(['clientSize'])
        },
        mounted() {
            // 监听页面滚动位置，改变导航栏的显示
            window.addEventListener('scroll', () => {
                //首页且不是移动端
                if (this.$route.name === 'home' && this.clientSize.clientWidth > 768) {
                    if (window.scrollY > this.clientSize.clientHeight / 2) {
                        this.$refs.menu.classList.remove('transparent')
                    } else {
                        this.$refs.menu.classList.add('transparent')
                    }
                }
            })
        },
        methods: {
            categoryRoute(name) {
                this.$router.push(`/category/${name}`)
            },

            debounceQuery(queryString, callback) {
                this.timer && clearTimeout(this.timer)
                this.timer = setTimeout(() => this.querySearchAsync(queryString, callback), 1000)
            },

            querySearchAsync(queryString, callback) {
            },

            handleSelect(item) {
                if (item.id) {
                    this.$router.push(`/blog/${item.id}`)
                }
            }
        }
    }
</script>

<style>
    .ui.fixed.menu .container {
        width: 1700px !important;
        margin-left: auto !important;
        margin-right: auto !important;
    }

    .ui.fixed.menu {
        transition: .3s ease-out;
    }

    .ui.inverted.pointing.menu.transparent {
        background: transparent !important;
    }

    .ui.inverted.pointing.menu.transparent .active.item:after {
        background: transparent !important;
        transition: .3s ease-out;
    }

    .ui.inverted.pointing.menu.transparent .active.item:hover:after {
        background: transparent !important;
    }

    .el-dropdown-link {
        outline-style: none !important;
        outline-color: unset !important;
        height: 100%;
        cursor: pointer;
    }

    .el-dropdown-menu {
        margin: 2px 0 0 0 !important;
        padding: 0 !important;
        border: 0 !important;
        background: #031634 !important;
    }

    .el-dropdown-menu__item {
        padding: 0 15px !important;
        color: rgba(255, 255, 255, .9) !important;
    }

    .el-dropdown-menu__item:hover {
        background: rgba(3, 255, 255, 0.08) !important;
    }

    .el-popper .popper__arrow::after {
        content: none !important;
    }

    .popper__arrow {
        display: none !important;
    }

    .m-search {
        min-width: 220px;
        padding: 0 !important;
    }

    .m-search input {
        color: rgba(255, 255, 255, .9);;
        border: 0 !important;
        background-color: inherit;
        padding: .67857143em 2.1em .67857143em 1em;
    }

    .m-search i {
        color: rgba(255, 255, 255, .9) !important;
    }

    .m-search-item {
        min-width: 350px !important;
    }

    .m-search-item li {
        line-height: normal !important;
        padding: 8px 10px !important;
    }

    .m-search-item li .title {
        text-overflow: ellipsis;
        overflow: hidden;
        color: rgba(0, 0, 0, 0.87);
    }

    .m-search-item li .content {
        text-overflow: ellipsis;
        font-size: 12px;
        color: rgba(0, 0, 0, .70);
    }
</style>