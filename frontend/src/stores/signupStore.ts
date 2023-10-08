import { makeAutoObservable, action, observable } from "mobx"
import http from "../services/httpService"
import { emailValidator } from "../utils/validation"
import { NavigateFunction } from "react-router-dom"

export class SignupStore {

    email: string = ''
    loading: boolean = false

    errMsg = {
        email: '',
    }

    constructor() {
        makeAutoObservable(this, {
            email: observable,
            errMsg: observable,
            handleSignup: action,
            setEmail: action,
            setEmailErrorMsg: action,
        })
    }

    get isAuthenticated(): boolean {
        return false
    }

    setEmail = (email: string) => {
        this.email = email.trim()
        this.setEmailErrorMsg(emailValidator(email))
    }

    setEmailErrorMsg = (errMsg: string) => {
        this.errMsg.email = errMsg
        if (errMsg.length > 0) {
            return true
        }
        return false
    }

    handleSignup = async (navigate: NavigateFunction) => {
        if (this.loading) {
            return
        }
        if (this.setEmailErrorMsg(emailValidator(this.email))) {
            return
        }
        this.loading = true
        try {
            const result = await http.post('/api/register', {
                email: this.email,
            })
            if (result.data.success === 'true') {
                navigate('/login')
            }
            else { }
        } catch (error) {
            console.log(error)
        }
        this.loading = false
        navigate('/')
        return true
    }
}