import React from 'react'
import { Route, Navigate } from 'react-router-dom';
import useAuth from '../hooks/useAuth';

const ProtectedRoute: React.FC = ({ children, ...rest }: any) => {
  const { isAuthenticated } = useAuth()

  if (!isAuthenticated) {
    return <Navigate to="/login" />
  }

  return <Route {...rest}>{children}</Route>
}

export default ProtectedRoute