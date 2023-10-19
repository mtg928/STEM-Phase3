import React from 'react'
import AddIcon from '../../../assets/add-icon.svg'
import { LazyLoadImage } from 'react-lazy-load-image-component'

const FMECACalculator: React.FC = () => {
  return (
    <>
      <div className='flex items-center'>
        <div className={`ml-7 hover:cursor-pointer text-lg font-bold select-none`} >FMECA summary</div>
        <div className='mx-7 bg-[#979797] h-9 w-[1px]'></div>
        <div className={`text-[#00711e] hover:cursor-pointer text-lg font-bold select-none`}>FMECA calculator</div>
        <div className='mx-7 bg-[#979797] h-9 w-[1px]'></div>
        <div className='font-medium select-none hover:cursor-pointer flex gap-2'> <LazyLoadImage src={AddIcon} alt="Add" /> Add New System Function</div>
        <div className='ml-9 font-medium select-none hover:cursor-pointer flex gap-2'> <LazyLoadImage src={AddIcon} alt="Add" /> Add New System Component</div>
      </div>
      <div className='mt-5 flex-1 flex flex-col'>
        <div className='rounded inline-flex justify-center items-center text-xl bg-[#000052] text-white w-full h-11'>RG-FMECA Test</div>
        <div className='rounded inline-flex items-center px-8 w-full h-14 bg-[#dfe1fd]'><span className='font-bold'>System ID</span> RN</div>
      </div>
    </>
  )
}

export default FMECACalculator