import axios from "axios"

export const API_BROWSER = {
    page(pageNum, pageSize) {
        return axios.get("/admin/browser/" + pageNum + "/" + pageSize);
    }
}
