import React from 'react'
import { Badge } from '@material-tailwind/react'
import { LazyLoadImage } from 'react-lazy-load-image-component'
import { Outlet } from 'react-router-dom'
import CalculatorAddButton from '../../components/Buttons/CalculatorAddButton'
import ProjectManagementButton from '../../components/Buttons/ProjectManagementButton'
import CalcMgmtIcon from '../../assets/calc-management-icon.svg'

const ProjectPage: React.FC = () => {
  // const [hiddenButtons, setButton] = useState(['Failure DB', 'Assumptions'])
  return (
    <>
      <div className='bg-white rounded-lg flex-1 flex flex-col p-7 relative'>
        <div className='text-2xl font-bold flex items-center'>
          <span className='w-full'>New Project 1</span>
          <span className='w-full flex justify-end items-center'><ProjectManagementButton /></span>
        </div>
        <div className='mt-[2.2rem] w-full h-[3px] bg-[#00C134]'></div>
        <div className='w-full flex flex-col'>
          <div className='flex flex-row gap-7 px-1 py-5'>
            <Badge className='-translate-x-1 bg-[#0e6cd4]'>
              <div className='border border-[#00711e] w-28 select-none hover:cursor-pointer h-8 inline-flex justify-center items-center font-medium rounded'>Hazzards</div>
            </Badge>
            <div className='border border-[#00711e] w-28 select-none hover:cursor-pointer h-8 inline-flex justify-center items-center font-medium rounded'>Fault Tree</div>
            <div className='border border-[#00711e] w-28 select-none hover:cursor-pointer h-8 inline-flex justify-center items-center font-medium rounded'>Risk Matrix</div>
            <div className='border border-[#00711e] w-28 select-none hover:cursor-pointer h-8 inline-flex justify-center items-center font-medium rounded'>Heat Map</div>
            <div className='border border-[#00711e] w-28 select-none hover:cursor-pointer h-8 inline-flex justify-center items-center font-medium rounded'>Systems</div>
            <div className='border bg-[#00711e] text-white border-[#00711e] w-28 select-none hover:cursor-pointer h-8 inline-flex justify-center items-center font-medium rounded'>Function</div>
            <div className='border border-[#00711e] w-28 select-none hover:cursor-pointer h-8 inline-flex justify-center items-center font-medium rounded'>References</div>
          </div>
          <div className='flex px-1'>
            <div className='hover:cursor-pointer inline-flex justify-center items-center'><CalculatorAddButton /><span className='ml-2 text-sm font-medium'>Add calculator</span></div>
            <div className='ml-6 hover:cursor-pointer inline-flex justify-center items-center'><LazyLoadImage src={CalcMgmtIcon} /><span className='ml-2 text-sm font-medium'>Calculation Management</span></div>
          </div>
          <div className='mt-5 w-full h-[3px] bg-[#00C134]'></div>
          <div className='mt-5 flex flex-col px-1 relative'>
            <Outlet />
          </div>
        </div>
      </div>
    </>
  )
}

export default ProjectPage