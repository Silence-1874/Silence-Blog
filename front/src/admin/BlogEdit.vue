<template>
    <div>
        <el-form :model="form" :rules="formRules" ref="formRef" label-position="top">
            <el-row :gutter="20">
                <el-col :span="12">
                    <el-form-item label="文章标题" prop="title">
                        <el-input v-model="form.title" placeholder="请输入标题"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="文章首图URL" prop="firstPicture">
                        <el-input v-model="form.firstPicture" placeholder="请输入文章首图的url"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-form-item label="文章正文" prop="content">
                <mavon-editor v-model="form.content"/>
            </el-form-item>

            <el-row :gutter="20">
                <el-col :span="12">
                    <el-form-item label="分类" prop="cate">
                        <el-select v-model="form.cate" placeholder="请选择分类（输入可添加新分类）" :allow-create="true"
                                   :filterable="true" style="width: 100%;">
                            <el-option :label="item.name" :value="item.id" v-for="item in categoryList"
                                       :key="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="标签" prop="tagList">
                        <el-select v-model="form.tagList" placeholder="请选择标签（输入可添加新标签）" :allow-create="true"
                                   :filterable="true" :multiple="true" style="width: 100%;">
                            <el-option :label="item.name" :value="item.id" v-for="item in tagList"
                                       :key="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-form-item style="text-align: right;">
                <el-button type="primary" @click="dialogVisible=true">保存</el-button>
            </el-form-item>
        </el-form>

    </div>
</template>

<script>
    export default {
        name: "BlogEdit",
        data() {
            return {
                categoryList: [],
                tagList: [],
                dialogVisible: false,
                radio: 1,
                form: {
                    title: '',
                    firstPicture: '',
                    content: '',
                    cate: null,
                    tagList: [],
                    words: null,
                    readTime: null,
                    views: 0,
                    top: false,
                },
                formRules: {
                    title: [{required: true, message: '请输入标题', trigger: 'change'}],
                    cate: [{required: true, message: '请选择分类', trigger: 'change'}],
                    tagList: [{required: true, message: '请选择标签', trigger: 'change'}],
                },
            }
        },
        watch: {
            'form.words'(newValue) {
                this.form.readTime = newValue ? Math.round(newValue / 200) : null
            },
        },
        created() {
            this.getData()
            if (this.$route.params.id) {
                this.getBlog(this.$route.params.id)
            }
        },
        methods: {
            getData() {
            },
            getBlog(id) {
            },
            submit() {
            }
        }
    }
</script>

<style scoped>

</style>