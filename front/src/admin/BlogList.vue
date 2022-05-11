<template>
    <div class="bloglist-contain">
        <!-- 搜索 -->
        <el-row>
            <el-col :span="8">
                <el-input v-model="queryInfo.title" :clearable="true"
                          placeholder="输入文章标题搜索" size="medium"
                          style="min-width: 500px"
                          @clear="searchByTitle" @keyup.native.enter="searchByTitle">
                    <el-button slot="append" icon="el-icon-search" @click="searchByTitle"></el-button>
                </el-input>
            </el-col>
        </el-row>

        <!--博客列表-->
        <el-table :data="blogList"
                  :header-cell-style="{background:'#eef1f6',color:'#606266'}"
                  stripe="true"
                  border="true"
                  style="margin-bottom: 15px; margin-top: 15px">
            <el-table-column label="序号" type="index" width="50" align="center"></el-table-column>
            <el-table-column label="标题" prop="title" show-overflow-tooltip align="center"></el-table-column>
            <el-table-column label="分类" prop="category" width="150" align="center"></el-table-column>

            <el-table-column label="置顶" width="100" align="center">
                <template v-slot="scope">
                    <el-switch v-model="scope.row.top" :active-value="1" :inactive-value="0"
                               @change="updateTop"></el-switch>
                </template>
            </el-table-column>

            <el-table-column label="创建时间" width="170" align="center">
                <template v-slot="scope">{{ scope.row.createTime }}</template>
            </el-table-column>

            <el-table-column label="最近更新" width="170" align="center">
                <template v-slot="scope">{{ scope.row.updateTime }}</template>
            </el-table-column>

            <el-table-column label="操作" width="200" align="center">
                <template v-slot="scope">
                    <el-button icon="el-icon-edit" size="mini" type="primary" @click="goBlogEditPage(scope.row.id)">编辑
                    </el-button>
                    <el-popconfirm icon="el-icon-delete" iconColor="red" title="确定删除吗？"
                                   @confirm="deleteBlogById(scope.row.id)">
                        <el-button slot="reference" icon="el-icon-delete" size="mini" type="danger">删除</el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

        <!--分页-->
        <div class="home-page">
            <!--分页-->
            <el-pagination @size-change="handleSizeChange" @current-change="handleNumChange"
                           :current-page="queryInfo.pageNum"
                           :page-sizes="[10, 20, 30, 50]" :page-size="queryInfo.pageSize" :total="total"
                           layout="total, sizes, prev, pager, next, jumper" background>
            </el-pagination>
        </div>
    </div>
</template>

<script>
    export default {
        name: "BlogList",
        data() {
            return {
                queryInfo: {
                    title: '搜索测试',
                    pageNum: 1,
                    pageSize: 10
                },
                blogList: [
                    {
                        "id": 1,
                        "title": "测试文章",
                        "top": true,
                        "createTime": "2022-05-11T01:43:58.000+0000",
                        "updateTime": "2022-05-11T01:43:58.000+0000",
                        "category": '测试分类',
                    },
                    {
                        "id": 2,
                        "title": "测试文章2",
                        "top": true,
                        "createTime": "2022-05-11T01:43:58.000+0000",
                        "updateTime": "2022-05-11T01:43:58.000+0000",
                        "category": '测试分类2',
                    },
                    {
                        "id": 3,
                        "title": "测试文章3",
                        "top": true,
                        "createTime": "2022-05-11T01:43:58.000+0000",
                        "updateTime": "2022-05-11T01:43:58.000+0000",
                        "category": '测试分类3',
                    },
                ],
                total: 0,
            }
        },
        created() {
        },
        methods: {
            // 根据标题搜索文章
            searchByTitle(title) {
            },

            // 获取当前分页的博客
            getData() {
            },

            // 切换置顶状态
            updateTop(row) {
            },

            // 跳转到博客编辑页
            goBlogEditPage(blogId) {
            },

            // 通过博客id删除博客
            deleteBlogById(blogId) {
            },

            // 监听单页大小改变事件
            handleSizeChange(newPageSize) {
            },

            // 监听页码改变的事件
            handleNumChange(newPageNum) {
            },
        }
    }
</script>

<style scoped>
    .el-button + span {
        margin-left: 10px;
    }
</style>