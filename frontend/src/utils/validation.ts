export const emailValidator = (email: string) => {
    if (email.length < 1) {
        return "Empty field isn't allowed"
    }
    if (!/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email)) {
        return "Invalid email!"
    }
    return ""
}

export const passwordValidator = (password: string) => {
    if (password.length < 1) {
        return "Empty field isn't allowed"
    }
    return ""
}