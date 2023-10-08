import { useEffect, useState } from 'react'
import AuthService from '../services/authService'

const useAuth = () => {
  const [isAuthenticated, setAuthenticated] = useState(false)
  useEffect(() => {
    setAuthenticated(true)
  }, [AuthService.getStoredToken])

  return { isAuthenticated }
}

export default useAuth