import React from 'react'
import { useState } from 'react'
import { SignupStore } from '../../../stores/signupStore'
import { useNavigate } from "react-router-dom"
import { LazyLoadImage } from 'react-lazy-load-image-component';
import { observer } from 'mobx-react';
import { Link } from 'react-router-dom';
import StemIcon from '../../../assets/stem-logo-positive.svg'
import StemPic from '../../../assets/login-art-graphic.png'

const SignUpPage: React.FC = observer(() => {
	const [signupStore] = useState(new SignupStore())
	const navigate = useNavigate()

	return (
		<>
			<main className='w-screen h-screen'>
				<div className="flex flex-row h-full w-full">
					<div className="w-full flex justify-center items-center flex-col lg:w-3/5">
						{/* logo */}
						<LazyLoadImage
							alt="Logo"
							src={StemIcon} />
						{/* welcome */}
						<div className='mt-[3.2rem]'>
							<p className="text-[2rem] font-medium">Welcome to Stem</p>
							<p className="mt-3 text-lg font-medium text-center">Let's get started</p>
						</div>
						{/* form */}
						<div className="mt-14 flex flex-col w-[22.75rem] items-center">
							<div className='w-full'>
								<label htmlFor="email" className="block text-sm">Email</label>
								<input
									className={`block w-full h-10 px-4 py-4 mt-2 bg-white border rounded focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40
									${signupStore.errMsg.email.length > 0 ? `border-red-500` : ``}`}
									type="email"
									id="email"
									placeholder='Name@company.com'
									onChange={(e) => signupStore.setEmail(e.target.value)}
								/>
								{signupStore.errMsg.email.length > 0 ? (<span className="inline-flex text-sm text-red-600">{signupStore.errMsg.email}</span>) : ''}
							</div>
							<div
								className="mt-7 w-full h-10 bg-[#0E6CD4] hover:bg-blue-500 active:bg-[#0E6CD4] rounded gap-2.5 btn normal-case hover:cursor-pointer text-white font-medium inline-flex justify-center items-center"
								onClick={() => signupStore.handleSignup(navigate)}
							>Continue</div>
							<p className='mt-10 text-sm'>By proceeding, you agree to the</p>
							<div><span className="mt-1 text-blue-500 text-sm hover:underline hover:cursor-pointer">Terms of Service</span> and <span className="text-blue-500 text-sm hover:underline hover:cursor-pointer">Privacy Policy.</span></div>
							<div className='mt-5 text-sm'>Already have an account? <Link to='/login' className='text-blue-500 hover:underline hover:cursor-pointer'>Log in</Link></div>
						</div>
					</div>
					<div className="w-2/5 hidden lg:block bg-[#02A42E]">
						<div className='bg-contain bg-no-repeat w-full h-[calc(80vh)] bg-top' style={{backgroundImage: `url(${StemPic})`}}></div>
					</div>
				</div>
			</main>
		</>
	)
})

export default SignUpPage