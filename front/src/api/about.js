import axios from 'axios'

export const API_ABOUT = {

    // AdminAboutController

    update (form) {
        return axios.put("/admin/about", form);
    } ,

    // AboutController

    get () {
        return axios.get('/about');
    },

}