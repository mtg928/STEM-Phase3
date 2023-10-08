import { createBrowserRouter } from "react-router-dom"
import Layout from "../components/Layout"
import SignUpPage from "../scenes/Auth/Signup"
import LoginPage from "../scenes/Auth/Login"
import HomePage from "../scenes/Dashboard/Home"
import OverviewPage from "../scenes/Dashboard/Overview"
import ScreenshotPage from "../scenes/Dashboard/Screenshot"
import PrintPage from "../scenes/Dashboard/Print"

const userRouter: any = [
    {
        path: '/signup',
        element: <SignUpPage />,
    },
    {
        path: '/login',
        element: <LoginPage />,
    },
    {
        path: '/',
        element: <Layout />,
        children: [
            {
                path: '/',
                element: <HomePage />,
            },
            {
                path: '/overview',
                element: <OverviewPage />,
            },
            {
                path: '/screenshot',
                element: <ScreenshotPage />,
            },
            {
                path: '/print',
                element: <PrintPage />,
            },
        ],
    },
]

const Router = createBrowserRouter(userRouter)

export default Router