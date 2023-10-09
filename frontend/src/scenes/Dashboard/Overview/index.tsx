import React from 'react'
import { ProjectDropDownButton, PersonButton, FilterButton, SortButton, HideButton } from '../../../components/Buttons'
import { TableAccordion } from '../../../components/Accordion'

const OverviewPage: React.FC = () => {
  return (
    <>
      <div className='bg-white rounded-lg flex-1 flex flex-col p-7'>
        <div className='text-2xl font-normal'>Project Overview</div>
        <div className='mt-[2.2rem] w-full h-[3px] bg-[#00C134]'></div>
        <div className='mt-5 w-full flex flex-col'>
          <div className='flex flex-row gap-7 px-1'>
            <ProjectDropDownButton />
            <PersonButton />
            <FilterButton />
            <SortButton />
            <HideButton />
          </div>
          <div className='flex flex-col px-1'>
            <TableAccordion />
          </div>
        </div>
      </div>
    </>
  )
}

export default OverviewPage