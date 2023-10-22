import React, { useState, useEffect, ChangeEvent } from 'react'
import { FMECASummaryTable } from '../../../components/Table'
import AddIcon from '../../../assets/add-icon.svg'
import EditIcon from '../../../assets/edit-icon.svg'
import RefreshIcon from '../../../assets/refresh-icon.svg'
import DeleteIcon from '../../../assets/delete.svg'
import CloseIcon from '../../../assets/close-icon.svg'
import { LazyLoadImage } from 'react-lazy-load-image-component'
import axios from 'axios'
import { useParams, useNavigate } from 'react-router-dom'
import useToken from '../../../hooks/useToken'
import {
  Dialog,
  DialogHeader,
  DialogBody,
  DialogFooter,
} from "@material-tailwind/react";

const FMECASummary: React.FC = () => {
  const [mode, setMode] = useState<boolean>(false)
  const [summaryData, setSummaryData] = useState()
  const [newSummaryData, setNewSummaryData] = useState({})
  const [open, setOpen] = useState(false)
  const { id } = useParams()
  const { token } = useToken()
  const navigate = useNavigate()
  const fetchData = async () => {
    try {
      const result = await axios.get(`/api/projects/${id}/fmeca`, { headers: { 'Authorization': `Bearer ${token}` } })
      const data = result.data;
      const filteredData = data.filter((obj: any) => obj.parentFmecaId === 0)
      setSummaryData(filteredData)
    } catch (error: any) {
      if (error.response.status === 401) {
        navigate('/login')
      }
    }
  }
  const handleAdd = async () => {
    try {
      await axios.post(`/api/projects/${id}/fmeca`, newSummaryData, { headers: { 'Authorization': `Bearer ${token}` } })
      await fetchData()
      setOpen(false)
      setNewSummaryData({})
    } catch (error: any) {
      if (error.response.status === 401) {
        navigate('/login')
      }
    }
  }
  const handleEdit = (e: ChangeEvent<HTMLInputElement>) => {
    const { id, value } = e.target
    setNewSummaryData({ ...newSummaryData, [id]: value })
  }
  useEffect(() => {
    fetchData()
  }, [])
  return (
    <>
      <div className='flex items-center'>
        <div className={`ml-7 ${mode ? '' : 'text-[#00711e]'} hover:cursor-pointer text-lg font-bold select-none`} onClick={() => setMode(false)}>FMECA summary</div>
        <div className='mx-7 bg-[#979797] h-9 w-[1px]'></div>
        <div className={`${mode ? 'text-[#00711e]' : ''} hover:cursor-pointer text-lg font-bold select-none`}>FMECA calculator</div>
        <div className='mx-7 bg-[#979797] h-9 w-[1px]'></div>
        <div className='font-medium select-none hover:cursor-pointer flex gap-2' onClick={() => setOpen(!open)}> <LazyLoadImage src={AddIcon} alt="Add" /> Add</div>
        <div className='ml-9 font-medium select-none hover:cursor-pointer flex gap-2'> <LazyLoadImage src={EditIcon} alt="Add" /> Edit</div>
        <div className='ml-9 font-medium select-none hover:cursor-pointer flex gap-2'> <LazyLoadImage src={DeleteIcon} alt="Add" /> Delete</div>
        <div className='ml-9 font-medium select-none hover:cursor-pointer flex gap-2'> <LazyLoadImage src={RefreshIcon} alt="Add" /> Refresh</div>
      </div>
      <div className='mt-5'>
        <FMECASummaryTable calcData={summaryData} />
      </div>
      <Dialog handler={() => { }} open={open}>
        <DialogHeader className='flex'>
          <div className='text-start w-full'>New Summary</div>
          <div className='flex w-full justify-end mb-5 hover:cursor-pointer' onClick={() => setOpen(!open)}><LazyLoadImage src={CloseIcon} /></div>
        </DialogHeader>
        <DialogBody>
          <div className='px-3 w-full flex flex-col'>
            <div className='text-black font-medium'>
              <label htmlFor='componentId'>Component ID</label><br />
              <input id="componentId" onBlur={handleEdit} className='mt-3 w-[calc(80%)] border px-3 border-[#DCDCDC] rounded  focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40' />
            </div>
            <div className='mt-5 text-black font-medium'>
              <label htmlFor='functionName'>Function Name</label><br />
              <input id="functionName" onBlur={handleEdit} className='mt-3 w-[calc(80%)] border px-3 border-[#DCDCDC] rounded  focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40' />
            </div>
            <div className='mt-5 text-black font-medium'>
              <label htmlFor='mpgType'>MPG Type</label><br />
              <input id="mpgType" onBlur={handleEdit} className='mt-3 w-[calc(80%)] border px-3 border-[#DCDCDC] rounded  focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40' />
            </div>
            <div className='mt-5 text-black font-medium'>
              <label htmlFor='calcfileId'>Calcfile ID</label><br />
              <input id="calcfileId" onBlur={handleEdit} className='mt-3 w-[calc(80%)] border px-3 border-[#DCDCDC] rounded  focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40' />
            </div>
            <div className='mt-5 text-black font-medium'>
              <label htmlFor='calcfile'>Calcfile</label><br />
              <input id="calcfile" onBlur={handleEdit} className='mt-3 w-[calc(80%)] border px-3 border-[#DCDCDC] rounded  focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40' />
            </div>
            <div className='mt-5 text-black font-medium'>
              <label htmlFor='standards'>Standards</label><br />
              <input id="standards" onBlur={handleEdit} className='mt-3 w-[calc(80%)] border px-3 border-[#DCDCDC] rounded  focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40' />
            </div>
            <div className='mt-5 text-black font-medium'>
              <label htmlFor='comments'>Comments</label><br />
              <input id="comments" onBlur={handleEdit} className='mt-3 w-[calc(80%)] border px-3 border-[#DCDCDC] rounded  focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40' />
            </div>
          </div>
        </DialogBody>
        <DialogFooter>
          <div onClick={handleAdd} className='w-[6.5rem] h-8 text-white bg-[#0E6CD4] hover:cursor-pointer hover:bg-blue-600 active:bg-blue-900 inline-flex justify-center items-center font-light rounded'>Add</div>
        </DialogFooter>
      </Dialog>
    </>
  )
}

export default FMECASummary