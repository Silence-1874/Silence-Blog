import axios from 'axios'

export const API_BLOG = {

    // AdminBlogController

    list() {
        return axios.get('/admin/blog/all');
    },

    pageByQuery(queryInfo) {
        return axios.get(
            "/admin/blog?"
            + "pageNum=" + queryInfo.pageNum
            + "&pageSize=" + queryInfo.pageSize
            + "&categoryId=" + queryInfo.categoryId
            + "&title=" + queryInfo.title
        );
    },

    getById(id) {
        return axios.get("/admin/blog/" + id);
    },

    add(formDTO) {
        return axios.post("/admin/blog", formDTO);
    },

    updateById(id, formDTO) {
        return axios.put("/admin/blog/" + id, formDTO);
    },

    getCategoryAndTag() {
        return axios.get("/admin/category_tag");
    },

    listTagIdByBlogId(id) {
        return axios.get("/admin/blogTag/" + id);
    },

    switchTopById(id) {
        return axios.put("/admin/blog/top/" + id);
    },

    deleteById(id) {
        return axios.delete("/admin/blog/" + id);
    },

    // BlogController

    page(pageNum, categoryId) {
        if (categoryId == null) {
            return axios.get("blog?pageNum=" + pageNum + "&pageSize=5");
        } else {
            return axios.get("/blog?pageNum=" + pageNum + "&pageSize=5&categoryId=" + categoryId);
        }
    },

    getDtoById(id) {
        return axios.get("/blog/" + id);
    },

    pageByTagId(pageNum, tagId) {
        return axios.get("/blogInTag?pageNum=" + pageNum + "&pageSize=5" + "&tagId=" + tagId);
    },

    count() {
        return axios.get("/countBlog");
    },

    increaseViewById(id) {
        return axios.get("/increaseViewById/" + id);
    },

}
