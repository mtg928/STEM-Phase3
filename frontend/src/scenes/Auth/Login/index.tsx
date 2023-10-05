import { useState } from 'react'
import { LoginStore } from '../../../stores/loginStore'
import { useNavigate } from "react-router-dom"

const LoginPage = () => {
	const [loginStore] = useState(new LoginStore())
	const navigate = useNavigate()

	return (
		<>
			<main className='w-screen h-screen'>
				<div className="flex flex-row h-full w-full">
					<div className="w-3/5 flex justify-center items-center flex-col">
						{/* logo */}
						<div className="text-3xl text-[#010151] font-semibold">Stem</div>
						{/* welcome */}
						<div>
							<p className="text-3xl font-semibold"><br />Login to your account</p>
						</div>
						{/* form */}
						<div className="flex flex-col w-[21rem] items-center mt-8">
							<div className="w-full inline-flex"><p className='text-start text-sm font-normal'>Email</p></div>
							<input
								className="mt-1 input w-[21rem] h-10 border border-[#DCDCDC] rounded-md py-1.5, px-1.5"
								onChange={(e) => loginStore.setEmail(e.target.value)}
							/>
							<div className="w-full inline-flex mt-2"><p className='text-start text-sm font-normal'>Password</p></div>
							<input
								className="mt-1 input w-[21rem] h-10 border border-[#DCDCDC] rounded-md py-1.5, px-1.5"
								type="password"
								onChange={(e) => loginStore.setPassword(e.target.value)}
							/>
							<div className="w-full inline-flex mt-1"><p className='text-start text-sm font-medium text-blue-500'>Forgot your password?</p></div>
							<div
								className="mt-5 w-[21rem] h-10 bg-[#0E6CD4] rounded-md gap-2.5 btn normal-case hover:cursor-pointer text-white font-medium inline-flex justify-center items-center"
								onClick={() => loginStore.handleLogin(navigate)}
							>Continue</div>
							<p className='mt-10'>By proceeding, you agree to the</p>
							<div><span className="text-blue-500">Terms of Service</span> and <span className="text-blue-500">Privacy Policy.</span></div>
						</div>
					</div>
					<div className="w-2/5 bg-blue-500"></div>
				</div>
			</main>
		</>
	)
}

export default LoginPage