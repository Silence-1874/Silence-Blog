<template>
    <div>
        <el-table :data="browserList" row-key="id"
                  :header-cell-style="{background:'#eef1f6',color:'#606266'}"
                  stripe border style="margin-bottom: 15px; margin-top: 15px">
            <el-table-column label="序号" width="60" type="index" align="center"></el-table-column>
            <el-table-column label="IP" prop="ip" align="center" width="140px"></el-table-column>
            <el-table-column label="浏览页面" prop="page" align="center"></el-table-column>
            <el-table-column label="请求URL" prop="url" width="470px" align="center"></el-table-column>
            <el-table-column label="浏览时间" width="200" align="center">
                <template v-slot="scope">{{ scope.row.time | dateFormat }}</template>
            </el-table-column>
        </el-table>

        <!--分页-->
        <el-pagination @size-change="handleSizeChange" @current-change="handleNumChange" :current-page="queryInfo.pageNum"
                       :page-sizes="[10, 20, 30, 50]" :page-size="queryInfo.pageSize" :total="total"
                       layout="total, sizes, prev, pager, next, jumper" background>
        </el-pagination>
    </div>
</template>

<script>
    import {API_BROWSER} from "@/api/browser";

    export default {
        name: "BrowserLog",
        data() {
            return {
                queryInfo: {
                    pageNum: 1,
                    pageSize: 10
                },
                total: 0,
                browserList: []
            }
        },
        created() {
            this.getData()
        },
        methods: {
            getData() {
                API_BROWSER.page(this.queryInfo.pageNum, this.queryInfo.pageSize).then(res => {
                    this.browserList = res.data.data.records;
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

<style scoped>

</style>