<template>
    <header ref="header">
        <!-- 首页图片 -->
        <div class="view">
            <div class="bg" style="background-image: url(../../public/background.jpg);"></div>
            <img :src="bg">
        </div>

        <!-- 首页文字 -->
        <div class="title">
            Welcome To Silence's Blog!
            <div class="border"></div>
        </div>

        <!-- 向下滚动至正文 -->
        <div class="array">
            <i class="el-icon-bottom" @click="scrollToMain"></i>
        </div>
    </header>
</template>

<script>
    import {mapState} from 'vuex'

    export default {
        name: "Header",
        data() {
            return {
                loaded: false,
                bg: require('../../public/background.jpg')
            }
        },
        computed: {
            ...mapState(['clientSize'])
        },
        watch: {
            'clientSize.clientHeight'() {
                this.setHeaderHeight()
            }
        },
        methods: {
            //根据可视窗口高度，动态改变首图大小
            setHeaderHeight() {
                this.$refs.header.style.height = this.clientSize.clientHeight + 'px'
            },
            //平滑滚动至正文部分
            scrollToMain() {
                window.scrollTo({top: this.clientSize.clientHeight, behavior: 'smooth'})
            }
        },
    }
</script>

<style scoped>
    header {
        --percentage: 0.5;
        user-select: none;
    }

    .view {
        position: absolute;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        display: flex;
        justify-content: center;
        transform: translatex(calc(var(--percentage) * 100px));
    }

    .view div {
        background-position: center center;
        background-size: cover;
        position: absolute;
        width: 110%;
        height: 100%;
    }

    .view .bg {
        z-index: 10;
        opacity: calc(1 - (var(--percentage) - 0.5) / 0.5);
    }

    header .view,
    header .bg {
        transition: .2s all ease-in;
    }

    header.moving .view,
    header.moving .bg {
        transition: none;
    }

    .title {
        box-sizing: border-box;
        position: relative;
        top: 28%;
        width: 800px;
        height: 100px;
        line-height: 100px;
        box-shadow: inset 0 0 0 1px white;
        margin: 40px auto;
        margin-top: 80px;
        color: white;
        text-align: center;
        font-size: 45px;
        font-weight: normal;
        font-family: Bold;
        letter-spacing: 7px;
    }

    .border {
        width: 812px;
        height: 112px;
        position: absolute;
        top: -6px;
        left: -6px;
        border: 3px solid #ffffff;
        box-sizing: border-box;
        animation: clipMe 5s linear infinite;
    }
    /* 实现边框转圈圈 */
    @keyframes clipMe {
        0%,
        100% {
            clip: rect(0px, 806px, 6px, 0px);
        }

        25% {
            clip: rect(0px, 6px, 112px, 0px);
        }

        50% {
            clip: rect(112px, 812px, 112px, 0px);
        }

        75% {
            clip: rect(0px, 812px, 112px, 806px);
        }
    }

    .array {
        position: absolute;
        color: #ffffff;
        width: 100px;
        bottom: 150px;
        left: 0;
        right: 0;
        margin: auto;
        font-size: 26px;
        z-index: 100;
    }
    .array i {
        font-size: 60px;
        opacity: 0.5;
        cursor: pointer;
        position: absolute;
        top: 55px;
        left: 20px;
        animation: opener .5s ease-in-out alternate infinite;
        transition: opacity .2s ease-in-out, transform .5s ease-in-out .2s;
    }
    @keyframes opener {
        100% {
            top: 65px
        }
    }
    .array i:hover {
        opacity: 1;
    }

</style>