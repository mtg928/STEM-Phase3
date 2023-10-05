import { makeAutoObservable, action, observable } from "mobx"
import http from "../services/httpService"
import { emailValidator, passwordValidator } from "../utils/validation"
import { NavigateFunction } from "react-router-dom"

export class LoginStore {

    email: string = ''
    password: string = ''
    loading: boolean = false

    errMsg = {
        email: '',
        password: ''
    }

    constructor() {
        makeAutoObservable(this, {
            email: observable,
            password: observable,
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
    }

    setPassword = (password: string) => {
        this.password = password
    }

    setEmailErrorMsg = (errMsg: string) => {
        this.errMsg.email = errMsg
    }

    setPasswordErrMsg = (errMsg: string) => {
        this.errMsg.password = errMsg
    }

    handleLogin = async (navigate: NavigateFunction) => {
        if (this.loading) {
            return
        }
        this.setEmailErrorMsg(emailValidator(this.email))
        this.setEmailErrorMsg(passwordValidator(this.password))
        this.loading = true
        await http.post('/api/login', {
            email: this.email,
            password: this.password
        })
        this.loading = false
        navigate('/')
        return true
    }

    logout = async () => {
        return true
    }
}