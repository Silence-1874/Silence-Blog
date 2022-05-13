<template>
    <div>
        <!--搜索-->
        <el-row>
            <el-col :span="6">
                <el-select v-model="blogId" placeholder="请选择页面" :filterable="true" :clearable="true" @change="search" size="small" style="min-width: 400px">
                    <el-option :label="item.title" :value="item.id" v-for="item in blogList" :key="item.id"></el-option>
                </el-select>
            </el-col>
        </el-row>

        <el-table :data="commentList" row-key="id" :tree-props="{children: 'replyComments'}"
                  :header-cell-style="{background:'#eef1f6',color:'#606266'}"
                  stripe border style="margin-bottom: 15px; margin-top: 15px">
            <el-table-column label="序号" width="50" type="index" align="center"></el-table-column>
            <el-table-column label="头像" width="70" align="center">
                <template v-slot="scope">
                    <el-avatar shape="square" :size="50" fit="contain" :src="scope.row.avatar"></el-avatar>
                </template>
            </el-table-column>
            <el-table-column label="昵称" prop="nickname" align="center"> </el-table-column>
            <el-table-column label="QQ" prop="qq" width="115" align="center"></el-table-column>
            <el-table-column label="评论内容" prop="content" show-overflow-tooltip align="center"></el-table-column>
            <el-table-column label="IP" prop="ip" width="130" align="center"></el-table-column>
            <el-table-column label="所属博客" show-overflow-tooltip align="center">
                <template v-slot="scope">
                    <el-link type="success" :href="`/blog/${scope.row.blog.id}`" target="_blank">{{ scope.row.blog.title }}</el-link>
                </template>
            </el-table-column>
            <el-table-column label="发表时间" width="170" align="center">
                <template v-slot="scope">{{ scope.row.createTime | dateFormat }}</template>
            </el-table-column>
            <el-table-column label="操作" width="200" align="center">
                <template v-slot="scope">
                    <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row)">编辑</el-button>
                    <el-popconfirm icon="el-icon-delete" iconColor="red" title="确定删除吗？"
                                   @confirm="deleteCommentById(scope.row.id)">
                        <el-button slot="reference" icon="el-icon-delete" size="mini" type="danger">删除</el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

        <!--分页-->
        <el-pagination @size-change="handleSizeChange" @current-change="handleNumChange" :current-page="queryInfo.pageNum"
                       :page-sizes="[10, 20, 30, 50]" :page-size="queryInfo.pageSize" :total="total"
                       layout="total, sizes, prev, pager, next, jumper" background>
        </el-pagination>

        <!--编辑评论对话框-->
        <el-dialog title="编辑评论" width="50%" :visible.sync="editDialogVisible" :close-on-click-modal="false" @close="editDialogClosed">
            <!--内容主体-->
            <el-form :model="editForm" :rules="formRules" ref="editFormRef" label-width="80px">
                <el-form-item label="昵称" prop="nickname">
                    <el-input v-model="editForm.nickname"></el-input>
                </el-form-item>
                <el-form-item label="头像" prop="avatar">
                    <el-input v-model="editForm.avatar"></el-input>
                </el-form-item>
                <el-form-item label="评论内容" prop="content">
                    <el-input v-model="editForm.content" type="textarea" maxlength="250" :rows="5" show-word-limit></el-input>
                </el-form-item>
            </el-form>
            <!--底部-->
            <span slot="footer">
				<el-button @click="editDialogVisible=false">取 消</el-button>
				<el-button type="primary" @click="editComment">确 定</el-button>
			</span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "CommentList",
        data() {
            return {
                blogId: null,
                queryInfo: {
                    blogId: null,
                    pageNum: 1,
                    pageSize: 10
                },
                total: 0,
                commentList: [
                    {
                        id: 1,
                        avatar: "123456",
                        nickname: "haha",
                        qq: "2245815651",
                        content: "测试评论内容",
                        ip: "127.0.0.1",
                        blog: {
                            id: 123,
                            title: "blogTitle",
                            name: "blogName",
                        },
                        createTime: "2022-05-11 09:18:58",
                    }
                ],
                blogList: [],
                editDialogVisible: false,
                editForm: {
                    id: null,
                    avatar: '',
                    nickname: '',
                    content: ''
                },
                formRules: {
                    nickname: [{required: true, message: '请输入评论昵称', trigger: 'blur'}],
                    avatar: [{required: true, message: '请输入评论头像', trigger: 'blur'}],
                    content: [
                        {required: true, message: '请输入评论内容', trigger: 'blur'},
                        {max: 255, message: '评论内容不可多于255个字符', trigger: 'blur'}
                    ],
                }
            }
        },
        created() {
            this.getCommentList()
            this.getBlogList()
        },
        methods: {
            getCommentList() {
            },

            getBlogList() {
            },

            search() {
                this.queryInfo.pageNum = 1
                this.queryInfo.pageSize = 10
                this.getCommentList()
            },

            //递归展开所有子评论
            getAllReplyCommentList(comment, replyCommentList) {
            },

            deleteCommentById(id) {
            },

            editComment() {
            },

            showEditDialog(row) {
                this.editForm = {...row}
                this.editDialogVisible = true
            },

            editDialogClosed() {
                this.editForm = {}
                this.$refs.editFormRef.resetFields()
            },

            // 监听单页大小改变事件
            handleSizeChange(newPageSize) {
                this.queryInfo.pageSize = newPageSize;
                this.getData();
            },

            // 监听页码改变的事件
            handleNumChange(newPageNum) {
                this.queryInfo.pageNum = newPageNum;
                this.getData();
            }
        }
    }
</script>

<style scoped>
    .el-button + span {
        margin-left: 10px;
    }
</style>