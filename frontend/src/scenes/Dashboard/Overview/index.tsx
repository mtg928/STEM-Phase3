import React, { useState, useEffect } from 'react'
import { ProjectDropDownButton, PersonButton, FilterButton, SortButton, HideButton } from '../../../components/Buttons'
import { OverviewTableAccordion } from '../../../components/Accordion'
import { useNavigate } from 'react-router-dom'
import ProjectCreateModal from '../../../components/ProjectModal/projectCreate'
import axios from 'axios'
import useToken from '../../../hooks/useToken'

const ProjectPage: React.FC = () => {
  const [loading, setLoading] = useState(true)
  const [projectGroups, setProjectGroups] = useState()
  const [showNewProjectModal, hideNewProjectModal] = useState<boolean>(false)
  const { token } = useToken()
  const navigate = useNavigate()
  useEffect(() => {
    async function fetchData() {
      setLoading(true)
      try {
        const result = await axios.get('/api/projects', { headers: { 'Authorization': `Bearer ${token}` } })
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
        if (err.response.status === 401) {
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
        <div className='text-2xl font-normal'>New Project1</div>
        <div className='mt-[2.2rem] w-full h-[3px] bg-[#00C134]'></div>
        <div className='mt-5 w-full flex flex-col'>
          <div className='flex flex-row gap-7 px-1'>
            <ProjectDropDownButton handleNew={() => hideNewProjectModal(!showNewProjectModal)} />
            <PersonButton />
            <FilterButton />
            <SortButton />
            <HideButton />
          </div>
          <div className='flex flex-col px-1'>
            {loading ? <>Loading...</> : <OverviewTableAccordion projectData={projectGroups} />}
          </div>
        </div>
        <div className={`w-full absolute h-full -m-7 ${showNewProjectModal ? '' : 'hidden'}`}>
          <ProjectCreateModal handleClose={hideNewProjectModal} />
        </div>
      </div>
    </>
  )
}

export default ProjectPage