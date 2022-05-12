<template>
    <div>
        <!--添加-->
        <el-row :gutter="10">
            <el-col :span="6">
                <el-button type="primary" size="small" icon="el-icon-plus" @click="addDialogVisible=true">添加分类
                </el-button>
            </el-col>
        </el-row>

        <el-table :data="categoryList"
                  :header-cell-style="{background:'#eef1f6',color:'#606266'}"
                  stripe
                  border
                  style="margin-bottom: 15px; margin-top: 15px">
            <el-table-column label="序号" type="index" width="50" align="center"></el-table-column>
            <el-table-column label="名称" prop="categoryName" align="center"></el-table-column>
            <el-table-column label="操作" align="center">
                <template v-slot="scope">
                    <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row)">编辑
                    </el-button>
                    <el-popconfirm title="确定删除吗？" icon="el-icon-delete" iconColor="red"
                                   @confirm="deleteCategoryById(scope.row.id)">
                        <el-button size="mini" type="danger" icon="el-icon-delete" slot="reference">删除</el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

        <!--分页-->
        <el-pagination @size-change="handleSizeChange" @current-change="handleNumChange"
                       :current-page="queryInfo.pageNum"
                       :page-sizes="[10, 20, 30, 50]" :page-size="queryInfo.pageSize" :total="total"
                       layout="total, sizes, prev, pager, next, jumper" background>
        </el-pagination>

        <!--添加分类对话框-->
        <el-dialog title="添加分类" width="50%" :visible.sync="addDialogVisible" :close-on-click-modal="false"
                   @close="addDialogClosed">
            <!--内容主体-->
            <el-form :model="addForm" :rules="formRules" ref="addFormRef" label-width="80px">
                <el-form-item label="分类名称" prop="categoryName">
                    <el-input v-model="addForm.categoryName"></el-input>
                </el-form-item>
            </el-form>
            <!--底部-->
            <span slot="footer">
				<el-button @click="addDialogVisible=false">取 消</el-button>
				<el-button type="primary" @click="addCategory">确 定</el-button>
			</span>
        </el-dialog>

        <!--编辑分类对话框-->
        <el-dialog title="编辑分类" width="50%" :visible.sync="editDialogVisible" :close-on-click-modal="false"
                   @close="editDialogClosed">
            <!--内容主体-->
            <el-form :model="editForm" :rules="formRules" ref="editFormRef" label-width="80px">
                <el-form-item label="分类名称" prop="categoryName">
                    <el-input v-model="editForm.categoryName"></el-input>
                </el-form-item>
            </el-form>
            <!--底部-->
            <span slot="footer">
				<el-button @click="editDialogVisible=false">取 消</el-button>
				<el-button type="primary" @click="editCategory">确 定</el-button>
			</span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "CategoryList",
        data() {
            return {
                queryInfo: {
                    pageNum: 1,
                    pageSize: 10
                },
                categoryList: [],
                total: 0,
                addDialogVisible: false,
                editDialogVisible: false,
                addForm: {
                    categoryName: ''
                },
                editForm: {
                    id: 0,
                    categoryName: ''
                },
                formRules: {
                    categoryName: [{required: true, message: '请输入分类名称', trigger: 'blur'}]
                }
            }
        },
        created() {
            this.getData()
        },
        methods: {
            getData() {
                this.$axios.get("/admin/category/" + this.queryInfo.pageNum + "/" + this.queryInfo.pageSize).then(res => {
                    this.categoryList = res.data.data.records;
                    this.total = res.data.data.total;
                })
            },

            addDialogClosed() {
                this.$refs.addFormRef.resetFields()
            },
            editDialogClosed() {
                this.editForm = {}
                this.$refs.editFormRef.resetFields()
            },

            addCategory() {
                this.$refs.addFormRef.validate(valid => {
                    if (valid) {
                        this.$axios.post("/admin/category", this.addForm).then(res => {
                            this.msgSuccess(res.data.msg);
                            this.addDialogVisible = false;
                            this.getData();
                        })
                    }
                })
            },

            editCategory() {
                this.$refs.editFormRef.validate(valid => {
                    if (valid) {
                        this.$axios.put("/admin/category", this.editForm).then(res => {
                            this.msgSuccess(res.data.msg);
                            this.editDialogVisible = false;
                            this.getData();
                        })
                    }
                })
            },

            showEditDialog(row) {
                this.editForm = row;
                this.editDialogVisible = true;
            },

            deleteCategoryById(id) {
                this.$axios.delete("/admin/category/" + id).then(res => {
                    this.msgSuccess(res.data.msg);
                    this.getData();
                })
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
            },
        }
    }
</script>

<style scoped>
    .el-button + span {
        margin-left: 10px;
    }
</style>