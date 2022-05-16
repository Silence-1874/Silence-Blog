<template>
    <!--网站底部-->
    <div class="footer">


        <el-row :gutter="24" style="padding-top: 30px">
            <el-col :sm="8">
                <el-row :gutter="24" class="footer-title">
                    <el-col :span="12" :offset="4">联系博主</el-col>
                </el-row>
                <el-row :gutter="24" class="footer-content">
                    <el-col :sm="12" :offset="4">
                        QQ： <br> 2245815651 <br>
                        邮箱： <br>silence1874@foxmail.com
                    </el-col>
                </el-row>
            </el-col>
            <!-- 手动模拟垂直分割线 -->
            <div style="border:1px solid ;float:left;height:100px;"></div>
            <el-col :sm="8">
                <el-row :gutter="24" class="footer-title">
                    <el-col :span="24">本网站已运行</el-col>
                </el-row>
                <el-row :gutter="24" class="footer-content" style="font-size: 18px">
                    <el-col :span="24" style="margin-top: 30px">
                        <!-- 暂且当每年是365天，每个月都是30天 -->
                        {{ durTime._data.years * 365 + durTime._data.months * 30 + durTime._data.days }} 天
                        {{ durTime._data.minutes }} 分
                        {{ durTime._data.seconds }} 秒
                    </el-col>
                </el-row>
            </el-col>
            <!-- 手动模拟垂直分割线 -->
            <div style="border:1px solid; float:left; height:100px;"></div>
            <el-col :sm="7">
                <el-row :gutter="24" class="footer-title">
                    <el-col :span="24">统计信息</el-col>
                </el-row>
                <el-row :gutter="24" class="footer-content" style="margin-top: 20px; font-size: 16px">
                    <el-col :span="24">
                        {{'总访问量: '+pv}}
                        <br>
                        <br>
                        {{'访客量: '+uv}}
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
    import moment from 'moment'

    export default {
        name: "Footer",
        data() {
            return {
                pv: 0,
                uv: 0,
                // 网站创建时间
                createTime: moment("2022.6.1 00:00:00"),
                durTime: "",
            }
        },
        mounted() {
            this.$nextTick(() => {
                // 挂载定时器，每秒给时间加1
                setInterval(this.addSecond, 1000);
            });
        },
        beforeDestroy() {
            if (this.timer) {
                // 在Vue实例销毁前，清除定时器
                clearInterval(this.timer);
            }
        },

        methods: {
            // TODO: 获取pv和uv
            getPVAndUV() {
            },
            // 计算网站运行时间
            getDurTime() {
                let curTime = moment();
                this.durTime = moment.duration(curTime.diff(this.createTime));
            },
            // 每秒给时间加一秒
            addSecond() {
                this.durTime.add(1, 'seconds');
            }
        },
        // 监听,当路由发生变化的时候执行
        watch: {
            '$route': 'getPVAndUV'
        },
        created() {
            this.getPVAndUV();
            this.getDurTime();
        }
    }
</script>

<style scoped>
    .footer-title {
        font-size: 20px;
        font-weight: bold;
        margin-bottom: 5px;
    }

    .footer-content {
        font-size: 13px;
        color: greenyellow;
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