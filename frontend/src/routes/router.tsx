import { createBrowserRouter } from "react-router-dom"
import Layout from "../components/Layout"
import SignUpPage from "../scenes/Auth/Signup"
import LoginPage from "../scenes/Auth/Login"
import HomePage from "../scenes/Dashboard/Home"
import OverviewPage from "../scenes/Dashboard/Overview"
import ScreenshotPage from "../scenes/Dashboard/Screenshot"
import PrintPage from "../scenes/Dashboard/Print"
import ProjectPage from "../scenes/Project"

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
                children: [
                    {
                        path: '/overview',
                        element: <OverviewPage />,
                    },
                    {
                        path: '/overview/:id',
                        element: <ProjectPage />,
                    }
                ]
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