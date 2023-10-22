import { makeAutoObservable, action, observable } from "mobx"
import { emailValidator, passwordValidator } from "../utils/validation"
import { NavigateFunction } from "react-router-dom"
import axios from "axios"

export class SignupStore {

    email: string = ''
    password: string = ''
    confirmationPassword: string = ''
    loading: boolean = false

    errMsg = {
        email: '',
        password: '',
        confirmPassword: '',
    }

    registerErr = {
        error: '',
        message: '',
    }

    constructor() {
        makeAutoObservable(this, {
            email: observable,
            password: observable,
            confirmationPassword: observable,
            errMsg: observable,
            registerErr: observable,
            handleSignup: action,
            setEmail: action,
            setPassword: action,
            setEmailErrorMsg: action,
            setPasswordErrMsg: action,
            passwordConfirmation: action,
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

    passwordConfirmation = (password: string) => {
        this.confirmationPassword = password
        this.clearErrmsg()
        if (password.length < 1) {
            this.errMsg.confirmPassword = "Empty field isn't allowed"
            return true
        }
        if (password !== this.password) {
            this.errMsg.confirmPassword = 'Passwords are not match!'
            return true
        }
        this.errMsg.confirmPassword = ''
        return false
    }

    clearErrmsg = () => {
        this.registerErr.error = ''
        this.registerErr.message = ''
    }

    handleSignup = async (navigate: NavigateFunction) => {
        if (this.loading) {
            return
        }
        this.setEmailErrorMsg(emailValidator(this.email))
        this.setPasswordErrMsg(passwordValidator(this.password))
        this.passwordConfirmation(this.confirmationPassword)
        if (this.errMsg.email.length > 0 || this.errMsg.password.length > 0 || this.errMsg.confirmPassword.length > 0) {
            return
        }

        this.loading = true
        try {
            const result = await axios.post('/api/auth/register', {
                email: this.email,
                password: this.password,
            })
            console.log(result.data)
            if (result.data.success === true) {
                this.clearErrmsg()
                navigate('/login')
            }
            else {
                this.registerErr.error = result.data.error
                this.registerErr.message = result.data.error_message
            }
            this.loading = false
        } catch (error: any) {
            this.loading = false
            if (error.response.data?.success === false) {
                this.registerErr.error = error.response.data.error
                this.registerErr.message = error.response.data.error_message
            }
        }
        this.loading = false
        return true
    }
}