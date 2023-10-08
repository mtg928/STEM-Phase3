import { makeAutoObservable, action, observable } from "mobx"
import { emailValidator, passwordValidator } from "../utils/validation"
import { NavigateFunction } from "react-router-dom"
import http from "../services/httpService"
import AuthService from "../services/authService"

export class LoginStore {

    email: string = ''
    password: string = ''
    loading: boolean = false

    errMsg = {
        email: '',
        password: '',
    }

    respMsg = {
        error: '',
        reason: '',
    }

    constructor() {
        makeAutoObservable(this, {
            email: observable,
            password: observable,
            errMsg: observable,
            handleLogin: action,
            logout: action,
            setEmail: action,
            setPassword: action,
            setEmailErrorMsg: action,
            setPasswordErrMsg: action,
        })
    }

    get isAuthenticated(): boolean {
        return false
    }

    setEmail = (email: string) => {
        this.email = email.trim()
        this.setEmailErrorMsg(emailValidator(email))
    }

    setPassword = (password: string) => {
        this.password = password
        this.setPasswordErrMsg(passwordValidator(password))
    }

    setEmailErrorMsg = (errMsg: string) => {
        this.errMsg.email = errMsg
        if (errMsg.length > 0) {
            return true
        }
        return false
    }

    setPasswordErrMsg = (errMsg: string) => {
        this.errMsg.password = errMsg
        if (errMsg.length > 0) {
            return true
        }
        return false
    }

    handleLogin = async (navigate: NavigateFunction) => {
        if (this.loading) {
            return
        }
        this.setEmailErrorMsg(emailValidator(this.email))
        this.setPasswordErrMsg(passwordValidator(this.password))
        if (this.errMsg.email.length > 0 || this.errMsg.password.length > 0) {
            return
        }
        this.loading = true
        try {
            const result = await http.post('/api/login', {
                email: this.email,
                password: this.password
            })
            if (result.data.success === 'true') {
                AuthService.storeToken(result.data.access_token)
            }
            else { }
        } catch (error) {
            console.log(error)
        }
        this.loading = false
        navigate('/')
        return true
    }

    logout = async () => {
        AuthService.removeStoredToken()
        return true
    }
}