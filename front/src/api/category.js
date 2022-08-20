import axios from 'axios'

export const API_CATEGORY = {

    // AdminCategoryController

    page(pageNum, pageSize) {
        return axios.get("/admin/category/" + pageNum + "/" + pageSize);
    },

    add(form) {
        return axios.post("/admin/category", form);
    },

    deleteById(id) {
        return axios.delete("/admin/category/" + id);
    },

    update(form) {
        return axios.put("/admin/category", form);
    },

    // CategoryController

    list() {
        return axios.get("/category");
    },

    getById(id) {
        return axios.get("/category/" + id);
    },

    getByName(name) {
        return axios.get("/categoryName/" + name);
    },

}
