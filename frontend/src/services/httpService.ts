import axios from "axios"
import AuthService from "./authService"

const http = axios.create({
    timeout: 30000,
    headers: {
        'Authorization': `Bearer ${AuthService.getStoredToken()}`,
    }
})

export default http