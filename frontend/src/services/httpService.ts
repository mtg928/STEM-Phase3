import axios from "axios"

const http = axios.create({
    baseURL: import.meta.env.REAC_APP_API_URL,
    timeout: 30000,
})

export default http