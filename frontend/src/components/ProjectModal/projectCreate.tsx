import React from 'react'

const ProjectCreateModal: React.FC = () => {
  return (
    <>
      <div className='absolute justify-center items-center w-full h-full rounded-lg flex flex-col shadow-lg'>
        <div className='w-full h-14 px-8 bg-[#0E6CD4] relative flex items-center rounded-tl-xl rounded-tr-xl text-white text-2xl'>
          <div>New Project</div>
          <div className='absolute top-3 right-3 hover:cursor-pointer'>
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="2" stroke="currentColor" className="w-5 h-5">
              <path strokeLinecap="round" strokeLinejoin="round" d="M6 18L18 6M6 6l12 12" />
            </svg>
          </div>
        </div>
        <div className='flex-1 flex flex-col p-5 bg-white w-full h-full shadow-black rounded-bl-xl rounded-br-xl'>
          <div className='font-medium mt-2 w-2/5 w-min-[15rem]'>
            <label htmlFor="email" className="block">Project Name</label>
            <input
              className={`block w-full h-10 px-4 py-4 mt-2 bg-white border rounded focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40`}
              type="text"
            />
          </div>
          <div className='font-medium mt-5 w-2/5 w-min-[15rem]'>
            <label htmlFor="email" className="block">Client</label>
            <input
              className={`block w-full h-10 px-4 py-4 mt-2 bg-white border rounded focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40`}
              type="text"
            />
          </div>
          <div className='font-medium mt-5 w-2/5 w-min-[15rem]'>
            <label htmlFor="email" className="block">Project Type</label>
            <input
              className={`block w-full h-10 px-4 py-4 mt-2 bg-white border rounded focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40`}
              type="text"
            />
          </div>
          <div className='font-medium mt-5 w-2/5 w-min-[15rem]'>
            <label htmlFor="email" className="block">Project Group</label>
            <input
              className={`block w-full h-10 px-4 py-4 mt-2 bg-white border rounded focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40`}
              type="text"
            />
          </div>
          <div className='font-medium mt-5 w-2/5 w-min-[15rem]'>
            <label htmlFor="email" className="block">Owner</label>
            <input
              className={`block w-full h-10 px-4 py-4 mt-2 bg-white border rounded focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40`}
              type="text"
            />
          </div>
          <div className='font-medium mt-5 w-2/5 w-min-[15rem]'>
            <label htmlFor="email" className="block">Details</label>
            <input
              className={`block w-full h-10 px-4 py-4 mt-2 bg-white border rounded focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40`}
              type="text"
            />
            <div className='w-full flex justify-end mt-5'>
              <div className='w-[6.5rem] h-8 text-white bg-[#0E6CD4] hover:cursor-pointer hover:bg-blue-600 inline-flex justify-center items-center font-light rounded'>Create</div>
            </div>
          </div>
        </div>
      </div>
    </>
  )
}

export default ProjectCreateModal