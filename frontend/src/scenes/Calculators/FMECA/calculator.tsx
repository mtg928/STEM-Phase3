import React, { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import AddIcon from '../../../assets/add-icon.svg'
import { LazyLoadImage } from 'react-lazy-load-image-component'
import FMECATable from '../../../components/Table/FMECA/calcTable'
import axios from 'axios'
import { useParams } from 'react-router-dom'
import useToken from '../../../hooks/useToken'
import { CalculatorDataTypes } from '../../../components/Table/FMECA/type'

const FMECACalculator: React.FC = () => {
  const navigate = useNavigate()
  const { id } = useParams()
  const { token } = useToken()

  const [calcData, setCalcData] = useState<any>()

  const fetchData = async () => {
    try {
      const result = await axios.get(`/api/projects/${id}/fmeca`, { headers: { 'Authorization': `Bearer ${token}` } })
      setCalcData(result?.data)
    } catch (error: any) {
      if (error.response.status === 401) {
        navigate('/login')
      }
    }
  }

  const handleUpdate = async (updateValueList: CalculatorDataTypes) => {
    try {
      await axios.put(`/api/projects/${id}/fmeca/${updateValueList.id}`, {
        ...updateValueList
      },
        { headers: { 'Authorization': `Bearer ${token}` } })
      // const updatedRow = result.data
      // calcData?.map((item: CalculatorDataTypes) => {
      //   if (item?.id === updatedRow?.id) return updatedRow
      //   return item
      // })
      await fetchData()
      console.log(calcData)
    } catch (error: any) {
      if (error.response.status === 401) {
        navigate('/login')
      }
    }
  }

  useEffect(() => {
    fetchData()
  }, [])

  const handleAddNew = async () => {
    try {
      await axios.post(`/api/projects/${id}/fmeca`, {}, { headers: { 'Authorization': `Bearer ${token}` } })
      await fetchData()
    } catch (error: any) {
      if (error.response.status === 401) {
        navigate('/login')
      }
    }
  }
  return (
    <>
      <div className='flex items-center'>
        <div className={`text-[#00711e] ml-7 hover:cursor-pointer text-lg font-bold select-none`} >FMECA summary</div>
        <div className='mx-7 bg-[#979797] h-9 w-[1px]'></div>
        <div className={` hover:cursor-pointer text-lg font-bold select-none`}>FMECA calculator</div>
        <div className='mx-7 bg-[#979797] h-9 w-[1px]'></div>
        <div className='font-medium select-none hover:cursor-pointer flex gap-2' onClick={handleAddNew}> <LazyLoadImage src={AddIcon} alt="Add" /> Add New System Function</div>
        <div className='ml-9 font-medium select-none hover:cursor-pointer flex gap-2'> <LazyLoadImage src={AddIcon} alt="Add" /> Add New System Component</div>
      </div>
      <div className='mt-5 flex-1 flex flex-col'>
        <div className='rounded-tl rounded-tr inline-flex justify-center items-center text-xl bg-[#000052] text-white w-full h-11'>RG-FMECA Test</div>
        <div className='inline-flex items-center px-8 w-full text-sm h-14 bg-[#dfe1fd]'>
          <div><span className='font-bold'>System ID</span>&nbsp;RN</div>
          <div className='mx-5 bg-[#979797] h-9 w-[1px]'></div>
          <div className='inline-flex items-center'><span className='font-bold'>Sub System Breakdown</span>&nbsp;Left hand door leaf</div>
        </div>
        <div className='flex-1'>
          <FMECATable handleUpdate={handleUpdate} data={calcData} />
        </div>
      </div>
    </>
  )
}

export default FMECACalculator