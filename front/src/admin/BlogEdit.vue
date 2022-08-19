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
                    <el-form-item label="文章简介" prop="description">
                        <el-input v-model="form.description" placeholder="请输入简介"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-form-item label="文章正文" prop="content">
                <mavon-editor id="_mavon" v-model="form.content"/>
            </el-form-item>

            <el-row :gutter="20">
                <el-col :span="12">
                    <el-form-item label="分类" prop="category">
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
                    description: '',
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
            if (this.$route.params.blogId) {
                this.getBlog(this.$route.params.blogId)
            }
        },
        methods: {
            getData() {
                this.$axios.get("/admin/category_tag").then(res => {
                    this.categoryList = res.data.data.categoryList;
                    this.tagList = res.data.data.tagList;
                })
            },

            getCategoryById(id) {
                this.$axios.get("/admin/category/" + id).then(res => {
                    this.form.category = res.data.data.id;
                })
            },

            getTagListById(id) {
                this.$axios.get("/admin/blogTag/" + id).then(res => {
                    this.form.tagList = res.data.data;
                })
            },

            getBlog(id) {
                this.$axios.get("/admin/blog/" + id).then(res => {
                    const dto = res.data.data;
                    this.form.title = dto.title;
                    this.form.description = dto.description;
                    this.form.content = dto.content;
                    this.getCategoryById(dto.categoryId);
                    this.getTagListById(id);
                })
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
                            if (res.data.isSuccess) {
                                this.msgSuccess(res.data.msg);
                                this.$router.push('/admin/article')
                            }
                        })
                    }
                })
            },

            updateBlog() {
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
                        const blogId = this.$route.params.blogId;
                        this.$axios.put("/admin/blog/" + blogId, formDTO).then(res => {
                            if (res.data.isSuccess) {
                                this.msgSuccess(res.data.msg);
                                this.$router.push('/admin/article')
                            }
                        })
                    }
                })
            },

            submit() {
                this.$refs.formRef.validate(valid => {
                    if (valid) {
                        if (this.$route.params.blogId) {
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
    #_mavon {
        max-width: none!important;
        padding: 0!important;
    }
</style>