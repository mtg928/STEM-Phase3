import { createBrowserRouter } from "react-router-dom"
import SignUpPage from "../scenes/Auth/Signup"
import LoginPage from "../scenes/Auth/Login"

const userRouter: any = [
    {
        path: '/signup',
        name: 'signup',
        title: 'SignUp',
        element: <SignUpPage />,
    },
    {
        path: '/login',
        name: 'login',
        title: 'Login',
        element: <LoginPage />,
    }
]

const Router = createBrowserRouter(userRouter)

export default Router