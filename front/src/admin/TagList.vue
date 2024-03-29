<template>
    <div>
        <!--添加-->
        <el-row :gutter="10">
            <el-col :span="6">
                <el-button type="primary" size="small" icon="el-icon-plus" @click="addDialogVisible=true">添加标签</el-button>
            </el-col>
        </el-row>

        <el-table :data="tagList"
                  :header-cell-style="{background:'#eef1f6',color:'#606266'}"
                  stripe border style="margin-bottom: 15px; margin-top: 15px">
            <el-table-column label="序号" type="index" width="50" align="center"></el-table-column>
            <el-table-column label="名称" prop="tagName" align="center"></el-table-column>
            <el-table-column label="颜色" align="center">
                <template v-slot="scope">
                    <span style="float:left;width: 100px;">{{ scope.row.color }}</span>
                    <span style="float:left;width: 100px; height: 23px" :class="`me-${scope.row.color}`"></span>
                </template>
            </el-table-column>
            <el-table-column label="操作" align="center">
                <template v-slot="scope">
                    <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row)">编辑</el-button>
                    <el-popconfirm title="确定删除吗？" icon="el-icon-delete" iconColor="red" @confirm="deleteTagById(scope.row.id)">
                        <el-button size="mini" type="danger" icon="el-icon-delete" slot="reference">删除</el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

        <!--分页-->
        <el-pagination @size-change="handleSizeChange" @current-change="handleNumChange" :current-page="queryInfo.pageNum"
                       :page-sizes="[10, 20, 30, 50]" :page-size="queryInfo.pageSize" :total="total"
                       layout="total, sizes, prev, pager, next, jumper" background>
        </el-pagination>

        <!--添加标签对话框-->
        <el-dialog title="添加标签" width="50%" :visible.sync="addDialogVisible" :close-on-click-modal="false" @close="addDialogClosed">
            <!--内容主体-->
            <el-form :model="addForm" :rules="formRules" ref="addFormRef" label-width="80px">
                <el-form-item label="标签名称" prop="tagName">
                    <el-input v-model="addForm.tagName"></el-input>
                </el-form-item>
                <el-form-item label="标签颜色">
                    <el-select v-model="addForm.color" placeholder="请选择颜色" :clearable="true" style="width: 100%">
                        <el-option v-for="item in colors" :key="item.value" :label="item.label" :value="item.value">
                            <span style="float: left; width: 100px;">{{ item.label }}</span>
                            <span style="float: left; width: 100px; height: inherit" :class="`me-${item.value}`"></span>
                            <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <!--底部-->
            <span slot="footer">
				<el-button @click="addDialogVisible=false">取 消</el-button>
				<el-button type="primary" @click="addTag">确 定</el-button>
			</span>
        </el-dialog>

        <!--编辑标签对话框-->
        <el-dialog title="编辑标签" width="50%" :visible.sync="editDialogVisible" :close-on-click-modal="false" @close="editDialogClosed">
            <!--内容主体-->
            <el-form :model="editForm" :rules="formRules" ref="editFormRef" label-width="80px">
                <el-form-item label="标签名称" prop="tagName">
                    <el-input v-model="editForm.tagName"></el-input>
                </el-form-item>
                <el-form-item label="标签颜色" prop="color">
                    <el-select v-model="editForm.color" placeholder="请选择颜色" :clearable="true" style="width: 100%">
                        <el-option v-for="item in colors" :key="item.value" :label="item.label" :value="item.value">
                            <span style="float: left; width: 100px;">{{ item.label }}</span>
                            <span style="float: left; width: 100px; height: inherit" :class="`me-${item.value}`"></span>
                            <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <!--底部-->
            <span slot="footer">
				<el-button @click="editDialogVisible=false">取 消</el-button>
				<el-button type="primary" @click="editTag">确 定</el-button>
			</span>
        </el-dialog>
    </div>
</template>

<script>
    import {API_TAG} from "@/api/tag";

    export default {
        name: "TagList",
        data() {
            return {
                queryInfo: {
                    pageNum: 1,
                    pageSize: 10
                },
                tagList: [],
                total: 0,
                addDialogVisible: false,
                editDialogVisible: false,
                addForm: {
                    tagName: '',
                    color: ''
                },
                editForm: {},
                formRules: {
                    tagName: [{required: true, message: '请输入标签名称', trigger: 'blur'}]
                },
                colors: [
                    {label: '红', value: 'red'},
                    {label: '橘', value: 'orange'},
                    {label: '黄', value: 'yellow'},
                    {label: '橄榄绿', value: 'olive'},
                    {label: '绿', value: 'green'},
                    {label: '水鸭蓝', value: 'teal'},
                    {label: '蓝', value: 'blue'},
                    {label: '紫罗兰', value: 'violet'},
                    {label: '紫', value: 'purple'},
                    {label: '粉', value: 'pink'},
                    {label: '棕', value: 'brown'},
                    {label: '灰', value: 'grey'},
                ],
            }
        },
        created() {
            this.getData()
        },
        methods: {
            getData() {
                API_TAG.page(this.queryInfo.pageNum, this.queryInfo.pageSize).then(res => {
                    this.tagList = res.data.data.records;
                    this.total = res.data.data.total;
                })
            },

            addDialogClosed() {
                this.addForm.color = ''
                this.$refs.addFormRef.resetFields()
            },

            editDialogClosed() {
                this.editForm = {}
                this.$refs.editFormRef.resetFields()
            },

            addTag() {
                this.$refs.addFormRef.validate(valid => {
                    if (valid) {
                        API_TAG.add(this.addForm).then(res => {
                            if (res.data.isSuccess) {
                                this.msgSuccess(res.data.msg);
                                this.addDialogVisible = false;
                                this.getData();
                            }
                        })
                    }
                })
            },

            editTag() {
                this.$refs.editFormRef.validate(valid => {
                    if (valid) {
                        API_TAG.update(this.editForm).then(res => {
                            if (res.data.isSuccess) {
                                this.msgSuccess(res.data.msg);
                                this.editDialogVisible = false;
                                this.getData();
                            }
                        })
                    }
                })
            },

            showEditDialog(row) {
                this.editForm = {...row}
                this.editDialogVisible = true
            },

            deleteTagById(id) {
                API_TAG.deleteById(id).then(res => {
                    if (res.data.isSuccess) {
                        this.msgSuccess(res.data.msg);
                        this.getData();
                    }
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