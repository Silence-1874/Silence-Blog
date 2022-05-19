<template>
    <!--标签云-->
    <div class="ui segments m-box">
        <div class="ui secondary segment">
            <i class="tags icon"></i> 标签云
        </div>
        <div class="ui pink segment m-padding-small">
            <router-link :to="`/tag/${tag.tagName}`" class="ui label m-text-500" :class="`${tag.color}`" v-for="(tag,index) in tagList" :key="index">
                {{ tag.tagName }}
            </router-link>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Tags",
        props: {
            tagList: {
                type: Array,
                required: true
            },
        },
        data() {
            return {
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
                ]
            }
        }
    }
</script>

<style scoped>
    .secondary.segment {
        padding: 10px;
    }

    .m-padding-small {
        padding: 7px;
    }

    .label {
        margin: 3px !important;
    }

    /* 用自定义的颜色覆盖semanticUI自带的颜色 */
    .ui.orange.label, .ui.orange.labels .label .ui.orange.labels .label:hover, a.ui.orange.label:hover {
        background-color: #ff9b6a !important;
        border-color: #ff9b6a !important;
        color: #fff!important;
    }
    .ui.yellow.label, .ui.yellow.labels .label .ui.yellow.labels .label:hover, a.ui.yellow.label:hover {
        background-color: #fecf45 !important;
        border-color: #fecf45 !important;
        color: #fff!important;
    }
    .ui.green.label, .ui.green.labels .label .ui.green.labels .label:hover, a.ui.green.label:hover {
        background-color: #849965 !important;
        border-color: #849965 !important;
        color: #fff!important;
    }
    .ui.blue.label, .ui.blue.labels .label .ui.blue.labels .label:hover, a.ui.blue.label:hover {
        background-color: #b4c6fb !important;
        border-color: #b4c6fb !important;
        color: #fff!important;
    }
    .ui.violet.label, .ui.violet.labels .label .ui.violet.labels .label:hover, a.ui.violet.label:hover {
        background-color: #6f5f90 !important;
        border-color: #6f5f90 !important;
        color: #fff!important;
    }
    .ui.pink.label, .ui.pink.labels .label .ui.pink.labels .label:hover, a.ui.pink .label:hover {
        background-color: #ff7b89 !important;
        border-color: #ff7b89 !important;
        color: #fff!important;
    }
    .ui.brown.label, .ui.brown.labels .label .ui.brown.labels .label:hover, a.ui.brown.label:hover {
        background-color: #e37c5b !important;
        border-color: #e37c5b !important;
        color: #fff!important;
    }
    .ui.grey.label, .ui.grey.labels .label .ui.grey.labels .label:hover, a.ui.grey.label:hover {
        background-color: #8e90a5 !important;
        border-color: #8e90a5 !important;
        color: #fff!important;
    }
</style>