import React, { useState } from 'react'
import { Badge } from '@material-tailwind/react'
import { LazyLoadImage } from 'react-lazy-load-image-component'
import { ProjectTable } from '../../components/Table'
import { useParams } from 'react-router-dom'
import CalculatorAddButton from '../../components/Buttons/CalculatorAddButton'
import ProjectManagementButton from '../../components/Buttons/ProjectManagementButton'
import CalcMgmtIcon from '../../assets/calc-management-icon.svg'

const projectData = [
  {
    id: 1,
    componentId: 50,
    functionName: 'RG-FMECA Test',
    mpgType: 'Failure mode critically',
    calcfileId: 43,
    calcFile: 'RK-group',
    standards: 'MIL-STD 629A',
    comments: 'Based on carbon and steel structure',
  },
  {
    id: 2,
    componentId: 61,
    functionName: 'RG-FMECA Test',
    mpgType: 'Failure mode critically',
    calcfileId: 46,
    calcFile: 'RK-group',
    standards: 'MIL-STD 629A',
    comments: 'This really needs more strength and structure and structure and structure and structure and structure and structure and structure and structure and structure and structure and structure and structure and structure',
  },
  {
    id: 3,
    componentId: 12,
    functionName: 'RG-FMECA Test',
    mpgType: 'RAM',
    calcfileId: 80,
    calcFile: 'RK-group',
    standards: 'MIL-STD 629A',
    comments: 'Needs vast improvements',
  },
  {
    id: 4,
    componentId: 126,
    functionName: 'RG-FMECA Test',
    mpgType: 'Safety',
    calcfileId: 108,
    calcFile: 'RK-group',
    standards: 'MIL-STD 629A',
    comments: null,
  },
  {
    id: 5,
    componentId: 35,
    functionName: 'RG-FMECA Test',
    mpgType: 'Failure mode critically',
    calcfileId: 15,
    calcFile: 'RK-group',
    standards: 'MIL-STD 629A',
    comments: 'Based on carbon and steel structure',
  },
]

const ProjectPage: React.FC = () => {
  const [hiddenButtons, setButton] = useState(['Failure DB', 'Assumptions'])
  const { id } = useParams()
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
              <div className='border border-[#00711e] w-28 h-8 inline-flex justify-center items-center font-medium rounded'>Hazzards</div>
            </Badge>
            <div className='border border-[#00711e] w-28 h-8 inline-flex justify-center items-center font-medium rounded'>Fault Tree</div>
            <div className='border border-[#00711e] w-28 h-8 inline-flex justify-center items-center font-medium rounded'>Risk Matrix</div>
            <div className='border border-[#00711e] w-28 h-8 inline-flex justify-center items-center font-medium rounded'>Heat Map</div>
            <div className='border border-[#00711e] w-28 h-8 inline-flex justify-center items-center font-medium rounded'>Systems</div>
            <div className='border border-[#00711e] w-28 h-8 inline-flex justify-center items-center font-medium rounded'>Function</div>
            <div className='border border-[#00711e] w-28 h-8 inline-flex justify-center items-center font-medium rounded'>References</div>
          </div>
          <div className='flex px-1'>
            <div className='hover:cursor-pointer inline-flex justify-center items-center'><CalculatorAddButton /><span className='ml-2 text-sm font-medium'>Add calculator</span></div>
            <div className='ml-6 hover:cursor-pointer inline-flex justify-center items-center'><LazyLoadImage src={CalcMgmtIcon} /><span className='ml-2 text-sm font-medium'>Calculation Management</span></div>
          </div>
          <div className='mt-5 w-full h-[3px] bg-[#00C134]'></div>
          <div className='mt-5 flex flex-col px-1'>
            <ProjectTable data={projectData} />
          </div>
        </div>
      </div>
    </>
  )
}

export default ProjectPage