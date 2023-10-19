import React, {useState} from 'react'
import { ProjectTable } from '../../../components/Table'
import AddIcon from '../../../assets/add-icon.svg'
import EditIcon from '../../../assets/edit-icon.svg'
import RefreshIcon from '../../../assets/refresh-icon.svg'
import DeleteIcon from '../../../assets/delete.svg'
import { LazyLoadImage } from 'react-lazy-load-image-component'

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

const FMECASummary: React.FC = () => {
  const [mode, setMode] = useState<boolean>(false)
  return (
    <>
      <div className='flex items-center'>
        <div className={`ml-7 ${mode ? '' : 'text-[#00711e]' } hover:cursor-pointer text-lg font-bold select-none`} onClick={() => setMode(false)}>FMECA summary</div>
        <div className='mx-7 bg-[#979797] h-9 w-[1px]'></div>
        <div className={`${mode ? 'text-[#00711e]' : '' } hover:cursor-pointer text-lg font-bold select-none`}>FMECA calculator</div>
        <div className='mx-7 bg-[#979797] h-9 w-[1px]'></div>
        <div className='font-medium select-none hover:cursor-pointer flex gap-2'> <LazyLoadImage src={AddIcon} alt="Add" /> Add</div>
        <div className='ml-9 font-medium select-none hover:cursor-pointer flex gap-2'> <LazyLoadImage src={EditIcon} alt="Add" /> Edit</div>
        <div className='ml-9 font-medium select-none hover:cursor-pointer flex gap-2'> <LazyLoadImage src={DeleteIcon} alt="Add" /> Delete</div>
        <div className='ml-9 font-medium select-none hover:cursor-pointer flex gap-2'> <LazyLoadImage src={RefreshIcon} alt="Add" /> Refresh</div>
      </div>
      <div className='mt-5'>
        <ProjectTable data={projectData} />
      </div>
    </>
  )
}

export default FMECASummary