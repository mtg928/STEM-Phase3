import { useState } from "react"

const useToken = () => {
  const getToken = (): string | undefined => {
    let accessToken =
      localStorage.getItem("access_token") === "undefined" || localStorage.getItem("access_token") === null
        ? undefined
        : (localStorage.getItem("access_token") as string)
    return accessToken
  }

  const [token, setToken] = useState(getToken())

  const saveToken = (newToken: string | undefined) => {
    if (newToken === undefined) localStorage.removeItem("access_token")
    else localStorage.setItem("access_token", newToken)
    setToken(newToken)
  }

  const removeToken = () => {
    localStorage.getItem('access_token') ? localStorage.removeItem('access_token') : null
  }

  return {
    token,
    setToken: saveToken,
    removeToken: removeToken,
  }
}

export default useToken
