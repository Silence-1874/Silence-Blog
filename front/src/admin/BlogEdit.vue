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
                    <el-form-item label="文章首图URL" prop="url">
                        <el-input v-model="form.url" placeholder="请输入文章首图的url"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-form-item label="文章正文" prop="content">
                <mavon-editor v-model="form.content"/>
            </el-form-item>

            <el-row :gutter="20">
                <el-col :span="12">
                    <el-form-item label="分类" prop="cate">
                        <el-select v-model="form.category" placeholder="请选择分类（输入可添加新分类）" :allow-create="true"
                                   :filterable="true" style="width: 100%;" default-first-option>
                            <el-option :label="item.categoryName" :value="item.id" v-for="item in categoryList"
                                       :key="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="标签" prop="tagList">
                        <el-select v-model="form.tagList" placeholder="请选择标签（输入可添加新标签）" :allow-create="true"
                                   :filterable="true" :multiple="true" style="width: 100%;" default-first-option>
                            <el-option :label="item.tagName" :value="item.id" v-for="item in tagList"
                                       :key="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-form-item style="text-align: right;">
                <el-button type="primary" @click="submit()">保存</el-button>
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
                form: {
                    title: '',
                    url: '',
                    content: '',
                    category: null,
                    tagList: [],
                },
                formRules: {
                    title: [{required: true, message: '请输入标题', trigger: 'change'}],
                    category: [{required: true, message: '请选择分类', trigger: 'change'}],
                    tagList: [{required: true, message: '请选择标签', trigger: 'change'}],
                },
            }
        },
        created() {
            this.getData()
            if (this.$route.params.id) {
                this.getBlog(this.$route.params.id)
            }
        },
        methods: {
            getData() {
                this.$axios.get("/admin/category_tag").then(res => {
                    this.categoryList = res.data.data.categoryList;
                    this.tagList = res.data.data.tagList;
                })
            },

            getBlog(id) {

            },

            addCategoryIfNotExit(curCategory) {
                // 在原列表里寻找id相同的分类
                for (const category of this.categoryList) {
                    if (curCategory == category.id) {
                        return category;
                    }
                }
                // 原列表中没有，说明是新增的分类
                return {"id": '', "categoryName": curCategory};
            },

            addTagIfNotExit(curTag) {
                // 在原列表里寻找id相同的标签
                for (const tag of this.tagList) {
                    if (curTag == tag.id) {
                        return tag;
                    }
                }
                // 原列表中没有，说明是新增的标签，默认颜色为灰色
                return {"id": '', "tagName": curTag, "color": "grey"};
            },

            addBlog() {
                this.form.category = this.addCategoryIfNotExit(this.form.category);
                var tempTagList = [];
                for (const tag of this.form.tagList) {
                    const t = this.addTagIfNotExit(tag);
                    tempTagList.push(this.addTagIfNotExit(tag));
                }

                // 深拷贝
                var formDTO = JSON.parse(JSON.stringify(this.form));
                formDTO.tagList = tempTagList

                this.$refs.formRef.validate(valid => {
                    if (valid) {
                        this.$axios.post("/admin/blog", formDTO).then(res => {
                            this.msgSuccess(res.data.msg);
                            this.$router.push('/admin/article')
                        })
                    }
                })
            },

            submit() {
                this.$refs.formRef.validate(valid => {
                    if (valid) {
                        if (this.$route.params.id) {
                            this.updateBlog();
                        } else {
                            this.addBlog();
                        }
                    } else {
                        this.dialogVisible = false
                        return this.msgError('请填写必要的表单项')
                    }
                })
            }
        }
    }
</script>

<style scoped>

</style>