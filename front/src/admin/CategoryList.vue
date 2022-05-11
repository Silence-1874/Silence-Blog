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
                  stripe="true"
                  border="true"
                  style="margin-bottom: 15px; margin-top: 15px">
            <el-table-column label="序号" type="index" width="50" align="center"></el-table-column>
            <el-table-column label="名称" prop="name" align="center"></el-table-column>
            <el-table-column label="操作" align="center">
                <template v-slot="scope">
                    <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row)">编辑
                    </el-button>
                    <el-popconfirm title="确定删除吗？" icon="el-icon-delete" iconColor="red"
                                   @onConfirm="deleteCategoryById(scope.row.id)">
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
                <el-form-item label="分类名称" prop="name">
                    <el-input v-model="addForm.name"></el-input>
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
                <el-form-item label="分类名称" prop="name">
                    <el-input v-model="editForm.name"></el-input>
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
                categoryList: [
                    {
                        id: 1,
                        name: '测试分类'
                    }
                ],
                total: 0,
                addDialogVisible: false,
                editDialogVisible: false,
                addForm: {
                    name: ''
                },
                editForm: {},
                formRules: {
                    name: [{required: true, message: '请输入分类名称', trigger: 'blur'}]
                }
            }
        },
        created() {
            this.getData()
        },
        methods: {
            getData() {
            },

            addDialogClosed() {
                this.$refs.addFormRef.resetFields()
            },
            editDialogClosed() {
                this.editForm = {}
                this.$refs.editFormRef.resetFields()
            },

            addCategory() {
            },

            editCategory() {
            },

            showEditDialog(row) {
            },

            deleteCategoryById(id) {
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