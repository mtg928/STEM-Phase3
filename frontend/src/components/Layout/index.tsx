import React from 'react'
import { Outlet } from 'react-router-dom'
import Header from "../Navbar"
import Sidebar from "../Sidebar"

const Layout: React.FC = () => {
  return (
    <>
      <div className="flex flex-col h-screen bg-[#E5F4EB]">
        <Header />
        <div className="flex-grow flex">
          <Sidebar />
          <main className="flex flex-col flex-1 px-3">
            <Outlet />
          </main>
        </div>
      </div>
    </>
  )
}

export default Layout