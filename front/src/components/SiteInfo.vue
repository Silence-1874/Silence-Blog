<template>
    <!--标签云-->
    <div class="ui segments m-box">
        <div class="ui secondary segment">
            <i class="bar chart icon"></i> 统计信息
        </div>
        <div class="ui blue segment m-padding-small">
            <div>
                <div>
                    <strong>本网站已运行</strong>
                </div>
                <!-- 暂且当每年是365天，每个月都是30天 -->
                <div style="font-size: 20px; margin-top: 10px;">
                    <el-tag effect="dark" style="font-size: 20px"> {{ durTime._data.years * 365 + durTime._data.months * 30 + durTime._data.days }} </el-tag> 天
                    <el-tag effect="dark" style="font-size: 20px"> {{ durTime._data.minutes }} </el-tag> 分
                    <el-tag effect="dark" style="font-size: 20px"> {{ durTime._data.seconds }} </el-tag> 秒
                </div>
                <br>
            </div>
            <div>
                <strong>总访问量:</strong>
                <el-tag effect="dark" type="success" style="margin-left: 20px; font-size: 20px"> {{ pv }} </el-tag>
            </div>
            <div>
                <strong>总访客量:</strong>
                <el-tag effect="dark" type="danger" style="margin-left: 20px; margin-top: 15px; font-size: 20px"> {{ uv }} </el-tag>
            </div>
        </div>
    </div>
</template>

<script>
    import moment from "moment";

    export default {
        name: "SiteInfo",
        data() {
            return {
                pv: 0,
                uv: 0,
                // 网站创建时间
                createTime: moment("2022.5.10 00:00:00"),
                durTime: ""
            }
        },
        mounted() {
            this.$nextTick(() => {
                // 挂载定时器，每秒给时间加1
                setInterval(this.addSecond, 1000);
            });
        },
        created() {
            this.getPVAndUV();
            this.getDurTime();
        },
        beforeDestroy() {
            if (this.timer) {
                // 在Vue实例销毁前，清除定时器
                clearInterval(this.timer);
            }
        },
        methods: {
            getPVAndUV() {
                this.$axios.get("/visitor/pvuv").then(res => {
                    this.pv = res.data.data.pv;
                    this.uv = res.data.data.uv;
                })
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
        }
    }
</script>

<style scoped>

</style>