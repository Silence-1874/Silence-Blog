<template>
    <!--网站底部-->
    <div class="footer">
        <el-row :gutter="24" style="padding-top: 30px">
            <el-col :sm="8">
                <el-row :gutter="24" class="footer-title">
                    <el-col :span="24">
                        <br>联系<br><br>
                        博主
                    </el-col>
                </el-row>
            </el-col>
            <!-- 手动模拟垂直分割线 -->
            <div style="border:1px solid ;float:left;height:100px;"></div>
            <el-col :sm="8">
                <el-row :gutter="24" class="footer-title">
                    <el-col :span="24">
                        <br>QQ<br><br>
                        2245815651
                    </el-col>
                </el-row>
            </el-col>
            <!-- 手动模拟垂直分割线 -->
            <div style="border:1px solid; float:left; height:100px;"></div>
            <el-col :sm="7">
                <el-row :gutter="24" class="footer-title">
                    <el-col :span="24">
                        <br>邮箱<br><br>
                        silence1874@qq.com
                    </el-col>
                </el-row>
            </el-col>
        </el-row>
        <el-divider></el-divider>
        <el-row :gutter="24" style="height: 50px;margin-top: 25px;font-size: 12px">
            <el-col :span="24">
                Copyright&nbsp;&copy;&nbsp;待填&nbsp;Silence's&nbsp;Blog.&nbsp;All&nbsp;rights&nbsp;reserved.&nbsp;
            </el-col>
        </el-row>
    </div>
</template>

<script>
    export default {
        name: "Footer",
    }
</script>

<style scoped>
    .footer-title {
        font-size: 20px;
        font-weight: bold;
        margin-bottom: 5px;
    }

    .footer {
        margin: 70px 0 0;
        text-align: center;
        min-height: 20px;
        max-width: 100%;
        padding: 0 15px;
        background-color: #031634;
        color: white !important;
        opacity: 0.9;
    }

</style>