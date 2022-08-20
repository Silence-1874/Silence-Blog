import axios from 'axios'

export const API_COMMENT = {

    // AdminCommentController

    pageByQuery(queryInfo) {
        return axios.get(
            "/admin/comment?"
            + "pageNum=" + queryInfo.pageNum
            + "&pageSize=" + queryInfo.pageSize
            + "&blogId=" + queryInfo.blogId);
    },

    deleteById(id) {
        return axios.delete('/admin/comment/' + id);
    },

    update(form) {
        return axios.put('/admin/comment', form);
    },

    // CommentController

    pageByBlogId(query) {
        return axios.get("/comment/" + query.blogId + "/" + query.pageNum + "/" + query.pageSize);
    },

    add(form) {
        return axios.post("/comment", form);
    },

}
