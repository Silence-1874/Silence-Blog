<template>
    <div>
        <el-form :model="form" :rules="formRules" ref="formRef" label-position="top">
            <el-form-item label="标题" prop="title" style="width: 50%">
                <el-input v-model="form.title" placeholder="请输入标题"></el-input>
            </el-form-item>

            <el-form-item label="正文" prop="content">
                <mavon-editor v-model="form.content"/>
            </el-form-item>

            <el-form-item style="text-align: right;">
                <el-button type="primary" icon="el-icon-check" @click="submit">保存</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    export default {
        name: "About",
        data() {
            return {
                form: {
                    id: 1,
                    title: '',
                    content: '',
                },
                formRules: {
                    title: [{required: true, message: '请输入标题', trigger: 'change'}],
                }
            }
        },
        created() {
            this.getData()
        },
        methods: {
            getData() {
                this.$axios.get("/admin/about").then(res => {
                    this.form.title = res.data.data.title;
                    this.form.content = res.data.data.content;
                });
            },
            submit() {
                this.$refs.formRef.validate(valid => {
                    if (valid) {
                        this.$axios.put("/admin/about", this.form).then(res => {
                            return this.msgSuccess(res.data.msg);
                        });
                    } else {
                        return this.msgError("请填写必要的表单！");
                    }
                })
            }
        }
    }
</script>

<style scoped>

</style>