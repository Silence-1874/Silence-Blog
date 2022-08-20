import axios from 'axios'

export const API_ARCHIVE = {

    archive() {
        return axios.get("/archives");
    },

}
