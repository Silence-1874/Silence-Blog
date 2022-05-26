<template>
    <div>
        <!--搜索-->
        <el-row>
            <el-col :span="6">
                <el-select v-model="queryInfo.blogId" placeholder="请选择页面" :filterable="true" :clearable="true" @change="search" size="small" style="min-width: 400px">
                    <el-option :label="item.title" :value="item.id" v-for="item in blogList" :key="item.id"></el-option>
                </el-select>
            </el-col>
        </el-row>

        <el-table :data="commentList" row-key="id"
                  :header-cell-style="{background:'#eef1f6',color:'#606266'}"
                  stripe border style="margin-bottom: 15px; margin-top: 15px">
            <el-table-column label="序号" width="50" type="index" align="center"></el-table-column>
            <el-table-column label="头像" width="80" align="center">
                <template v-slot="scope">
                    <el-avatar shape="square" :size="50" fit="contain" :src="scope.row.avatar"></el-avatar>
                </template>
            </el-table-column>
            <el-table-column label="昵称" prop="nickname" width="100px" align="center">
                <template v-slot="scope">
                    {{ scope.row.nickname }}
                    <el-tag v-if="scope.row.isAdmin" size="mini" effect="dark" style="margin-left: 5px">我</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="QQ" prop="qq" width="115" align="center"></el-table-column>
            <el-table-column label="评论内容" prop="content" show-overflow-tooltip align="center"></el-table-column>
            <el-table-column label="IP" prop="ip" width="120" align="center"></el-table-column>
            <el-table-column label="地理位置" prop="city" width="110" align="center"></el-table-column>
            <el-table-column label="所属博客" show-overflow-tooltip align="center">
                <template v-slot="scope">
                    <el-link v-if="scope.row.blogId !== 0" type="success" :href="`/blog/${scope.row.blogId}`" target="_blank">{{ getBlogTitleById(scope.row.blogId) }}</el-link>
                    <el-link v-if="scope.row.blogId === 0" type="success" :href="`/about`" target="_blank">关于我</el-link>
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
				<el-button type="primary" @click="updateComment">确 定</el-button>
			</span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "CommentList",
        data() {
            return {
                queryInfo: {
                    blogId: '',
                    pageNum: 1,
                    pageSize: 10
                },
                total: 0,
                commentList: [],
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
                const info = this.queryInfo;
                this.$axios.get(
                    "/admin/comment?"
                    + "pageNum=" + info.pageNum
                    + "&pageSize=" + info.pageSize
                    + "&blogId=" + info.blogId
                ).then (res =>{
                    this.commentList = res.data.data.records;
                    this.total = res.data.data.total;
                });
            },

            getBlogList() {
                this.$axios.get("/admin/blog/all").then(res => {
                    this.blogList = res.data.data;
                    this.blogList.unshift({id: 0, title: '关于我'})
                })
            },

            search() {
                this.queryInfo.pageNum = 1
                this.queryInfo.pageSize = 10
                this.getCommentList()
            },

            getBlogTitleById(id) {
                for (const blog of this.blogList) {
                    if (id === blog.id) {
                        return blog.title;
                    }
                }
            },

            deleteCommentById(id) {
                this.$axios.delete('/admin/comment/' + id).then(res => {
                    this.msgSuccess(res.data.msg);
                    this.getCommentList();
                })
            },

            updateComment() {
                this.$axios.put('/admin/comment', this.editForm).then(res => {
                    this.msgSuccess(res.data.msg);
                    this.editDialogVisible = false;
                    this.getCommentList();
                })
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
                this.getCommentList();
            },

            // 监听页码改变的事件
            handleNumChange(newPageNum) {
                this.queryInfo.pageNum = newPageNum;
                this.getCommentList();
            }
        }
    }
</script>

<style scoped>
    .el-button + span {
        margin-left: 10px;
    }
</style>