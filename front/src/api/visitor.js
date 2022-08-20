import axios from 'axios'

export const API_VISITOR = {

    // AdminVisitorController

    page(pageNum, pageSize) {
        return axios.get("/admin/visitor/" + pageNum + "/" + pageSize);
    },

    // VisitorController

    updateByIp(ip, city) {
        return axios.put("/visitor/pv/" + ip + "/" + city);
    },

    getPvAndUv() {
        return axios.get("/visitor/pvuv");
    }

}
