class AuthService {
  static tokenKey = 'access_token'

  static getStoredToken() {
    return localStorage.getItem(AuthService.tokenKey)
  }

  static storeToken(token: string) {
    localStorage.setItem(AuthService.tokenKey, token)
  }

  static removeStoredToken() {
    localStorage.removeItem(AuthService.tokenKey)
  }

}

export default AuthService