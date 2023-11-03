import { makeAutoObservable, action, observable } from "mobx"
import { emailValidator, passwordValidator } from "../utils/validation"
import axios from 'axios'

export class LoginStore {

    email: string = ''
    password: string = ''
    loading: boolean = false

    errMsg = {
        email: '',
        password: '',
    }

    loginErr = {
        error: '',
        message: '',
    }

    constructor() {
        makeAutoObservable(this, {
            email: observable,
            password: observable,
            errMsg: observable,
            loginErr: observable,
            handleLogin: action,
            setEmail: action,
            setPassword: action,
            setEmailErrorMsg: action,
            setPasswordErrMsg: action,
            clearErrmsg: action,
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
        this.clearErrmsg()
        if (errMsg.length > 0) {
            return true
        }
        return false
    }

    setPasswordErrMsg = (errMsg: string) => {
        this.errMsg.password = errMsg
        this.clearErrmsg()
        if (errMsg.length > 0) {
            return true
        }
        return false
    }

    clearErrmsg = () => {
        this.loginErr.error = ''
        this.loginErr.message = ''
    }

    handleLogin = async () => {
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
            const result = await axios.post('/api/auth/login', {
                email: this.email,
                password: this.password
            })
            if (result.status === 200) {
                this.loading = false
                this.clearErrmsg()
                return result.data
            }
        } catch (error: any) {
            this.loading = false
            if (error.response?.data?.success === false) {
                this.loginErr.error = error.response.data.error
                this.loginErr.message = error.response.data.error_message
            }
        }
        return true
    }
}