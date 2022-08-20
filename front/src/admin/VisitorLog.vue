<template>
    <div>
        <el-table :data="visitorList" row-key="id"
                  :header-cell-style="{background:'#eef1f6',color:'#606266'}"
                  stripe border style="margin-bottom: 15px; margin-top: 15px">
            <el-table-column label="序号" width="180" type="index" align="center"></el-table-column>
            <el-table-column label="IP" prop="ip" align="center"></el-table-column>
            <el-table-column label="地理位置" prop="city" width="210" align="center"></el-table-column>
            <el-table-column label="初次访问时间" width="300" align="center">
                <template v-slot="scope">{{ scope.row.firstTime | dateFormat }}</template>
            </el-table-column>
            <el-table-column label="最后访问时间" width="300" align="center">
                <template v-slot="scope">{{ scope.row.lastTime | dateFormat }}</template>
            </el-table-column>
            <el-table-column label="总pv" prop="pv" width="150px" align="center"></el-table-column>
        </el-table>

        <!--分页-->
        <el-pagination @size-change="handleSizeChange" @current-change="handleNumChange" :current-page="queryInfo.pageNum"
                       :page-sizes="[10, 20, 30, 50]" :page-size="queryInfo.pageSize" :total="total"
                       layout="total, sizes, prev, pager, next, jumper" background>
        </el-pagination>
    </div>
</template>

<script>
    import {API_VISITOR} from "@/api/visitor";

    export default {
        name: "VisitorLog",
        data() {
            return {
                queryInfo: {
                    pageNum: 1,
                    pageSize: 10
                },
                total: 0,
                visitorList: []
            }
        },
        created() {
            this.getData();
        },
        methods: {
            getData() {
                API_VISITOR.page(this.queryInfo.pageNum, this.queryInfo.pageSize).then(res => {
                    this.visitorList = res.data.data.records;
                    this.total = res.data.data.total;
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
            }
        }
    }
</script>

<style>
</style>