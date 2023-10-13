import React, { useState, useEffect } from 'react'
import { ProjectDropDownButton, PersonButton, FilterButton, SortButton, HideButton } from '../../../components/Buttons'
import { OverviewTableAccordion } from '../../../components/Accordion'
import http from '../../../services/httpService'
import { useNavigate } from 'react-router-dom'
import ProjectModal from '../../../components/ProjectModal'
import { OverviewStore } from '../../../stores/overviewStore'
import { observer } from 'mobx-react'
import ProjectCreateModal from '../../../components/ProjectModal/projectCreate'

interface ProjectType {
  id: number,
  name: string,
  type: string,
  abbreviation: string,
  description: string,
  client: string,
  owner: string,
  comments: string,
  status: string,
  label: string,
  creationDate: Date,
  lastUpdated: string,
}

const OverviewPage: React.FC = observer(() => {
  const [overviewStore] = useState(new OverviewStore())
  const [loading, setLoading] = useState(true)
  const [projectGroups, setProjectGroups] = useState()
  const navigate = useNavigate()
  useEffect(() => { console.log(overviewStore.selectedProjects) }, [overviewStore.selectedProjects])
  useEffect(() => {
    async function fetchData() {
      setLoading(true)
      try {
        const result = await http.get('/api/projects')
        const data = result.data
        const groupData = data.reduce((acc: { [x: string]: any[] }, item: { label: string }) => {
          const key = item.label
          if (!acc[key]) {
            acc[key] = []
          }
          acc[key].push(item)
          return acc
        }, {})
        setProjectGroups(groupData)
      } catch (err: any) {
        if (err.response.data.success === false) {
          navigate('/login')
        }
        console.log(err)
      }
      setLoading(false)
    }
    fetchData()

  }, [])
  return (
    <>
      <div className='bg-white rounded-lg flex-1 flex flex-col p-7 relative'>
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
            {loading ? <>Loading...</> : <OverviewTableAccordion projectData={projectGroups} />}
          </div>
        </div>
        <div className={`w-full flex justify-center ${overviewStore.selectedProjects.length < 1 ? 'hidden' : ''}`}>
          <ProjectModal />
        </div>
        <div className='w-full absolute h-full -m-7'>
          <ProjectCreateModal />
        </div>
      </div>
    </>
  )
})

export default OverviewPage