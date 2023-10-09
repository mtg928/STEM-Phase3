import React from 'react'
import { useState } from 'react'
import { LoginStore } from '../../../stores/loginStore'
import { useNavigate } from "react-router-dom"
import { LazyLoadImage } from 'react-lazy-load-image-component';
import { observer } from 'mobx-react';
import StemIcon from '../../../assets/stem-logo-positive.svg'

const LoginPage: React.FC = observer(() => {
	const [loginStore] = useState(new LoginStore())
	const navigate = useNavigate()

	return (
		<>
			<main className='w-screen h-screen'>
				<div className="flex flex-row h-full w-full justify-center items-center">
					<div className="w-full flex justify-center items-center flex-col lg:w-3/5">
						{/* logo */}
						<LazyLoadImage
							alt="Logo"
							src={StemIcon} />
						{/* welcome */}
						<div className='mt-[3.2rem]'>
							<p className="text-[32px] font-medium">Login to your account</p>
						</div>
						{/* form */}
						<div className="mt-9 flex flex-col w-[22.75rem] items-center">
							<div className='w-full'>
								<label htmlFor="email" className="block text-sm">Email</label>
								<input
									className={`block w-full h-10 px-4 py-4 mt-2 bg-white border rounded focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40
									${loginStore.errMsg.email.length > 0 ? `border-red-500` : ``}`}
									type="email"
									onChange={(e) => loginStore.setEmail(e.target.value)}
								/>
								{loginStore.errMsg.email.length > 0 ? (<span className="inline-flex text-sm text-red-600">{loginStore.errMsg.email}</span>) : ''}
							</div>
							<div className="mt-3 w-full">
								<label htmlFor="password" className="block text-sm">Password</label>
								<input
									className={`block w-full h-10 px-4 py-4 mt-2 bg-white border rounded focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40
									${loginStore.errMsg.password.length > 0 ? `border-red-500` : ``}`}
									type="password"
									onChange={(e) => loginStore.setPassword(e.target.value)}
								/>
								{loginStore.errMsg.password.length > 0 ? (<span className="inline-flex text-sm text-red-600">{loginStore.errMsg.password}</span>) : ''}
							</div>
							{loginStore.loginErr.error.length > 0 ? (<span className="mt-5 inline-flex text-xs text-red-600">{loginStore.loginErr.error + ": " + loginStore.loginErr.message}</span>) : ''}
							<div className="w-full inline-flex mt-2"><p className='text-start text-sm font-medium text-blue-500 hover:cursor-pointer'>Forgot your password?</p></div>
							<div
								className="mt-7 w-full h-10 bg-[#0E6CD4] hover:bg-blue-500 active:bg-[#0E6CD4] rounded gap-2.5 btn normal-case hover:cursor-pointer text-white font-medium inline-flex justify-center items-center"
								onClick={() => loginStore.handleLogin(navigate)}
							>Continue</div>
							<p className='mt-10 text-sm'>By proceeding, you agree to the</p>
							<div><span className="mt-1 text-blue-500 text-sm hover:underline hover:cursor-pointer">Terms of Service</span> and <span className="text-blue-500 text-sm hover:underline hover:cursor-pointer">Privacy Policy.</span></div>
						</div>
					</div>
					<div className="w-2/5 h-full bg-blue-500 hidden lg:block"></div>
				</div>
			</main >
		</>
	)
})

export default LoginPage