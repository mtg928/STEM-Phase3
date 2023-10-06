import { useState } from 'react'
import { SignupStore } from '../../../stores/signupStore'
import { useNavigate } from "react-router-dom"

const SignUpPage = () => {
	const [signupStore] = useState(new SignupStore())
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
							<p className="text-3xl font-semibold text-center"><br />Welcome to Stem</p>
							<p className="mt-2 text-xl font-normal text-center">Let's get started</p>
						</div>
						{/* form */}
						<div className="flex flex-col w-[21rem] items-center mt-8">
							<div className="w-full inline-flex"><p className='text-start text-sm font-normal'>Email</p></div>
							<input
								className="mt-1 input w-[21rem] h-10 border border-[#DCDCDC] rounded-md py-1.5, px-1.5"
								onChange={(e) => signupStore.setEmail(e.target.value)}
							/>
							<div
								className="mt-5 w-[21rem] h-10 bg-[#0E6CD4] rounded-md gap-2.5 btn normal-case hover:cursor-pointer text-white font-medium inline-flex justify-center items-center"
								onClick={() => signupStore.handleSignup(navigate)}
							>Continue</div>
							<p className='mt-10'>By proceeding, you agree to the</p>
							<div><span className="text-blue-500">Terms of Service</span> and <span className="text-blue-500">Privacy Policy.</span></div>
							<div className='mt-4'>Already have an account? <span className="text-blue-500">Log in</span></div>
						</div>
					</div>
					<div className="w-2/5 bg-blue-500"></div>
				</div>
			</main>
		</>
	)
}

export default SignUpPage