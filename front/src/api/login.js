import axios from 'axios'

export const API_LOGIN = {

    login(form) {
        return axios.post("/login", form);
    }

}
