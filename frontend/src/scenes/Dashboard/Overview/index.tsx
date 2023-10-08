import React from 'react'
import { ProjectDropDownButton } from '../../../components/Buttons'

const OverviewPage: React.FC = () => {
  return (
    <>
      <div className='bg-white rounded-lg flex-1 flex flex-col p-7'>
        <div className='text-2xl font-normal'>Project Overview</div>
        <div className='mt-[2.2rem] w-full h-[3px] bg-[#00C134]'></div>
        <div className='mt-5 w-full flex gap-7'>
          <ProjectDropDownButton />
        </div>
      </div>
    </>
  )
}

export default OverviewPage