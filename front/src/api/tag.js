import axios from 'axios'

export const API_TAG = {

    // AdminTagController

    page(pageNum, pageSize) {
        return axios.get("/admin/tag/" + pageNum + "/" + pageSize);
    },

    add(form) {
        return axios.post("/admin/tag", form);
    },

    update(form) {
        return axios.put("/admin/tag", form);
    },

    deleteById(id) {
        return axios.delete("/admin/tag/" + id);
    },

    // TagController

    list() {
        return axios.get("/tag");
    },

    getByName(name) {
        return axios.get("tagName/" + name);
    },

}
