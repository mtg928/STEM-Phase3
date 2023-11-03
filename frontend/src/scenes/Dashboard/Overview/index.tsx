import React, { useState, useEffect } from 'react'
import { ProjectDropDownButton, PersonButton, FilterButton, SortButton, HideButton } from '../../../components/Buttons'
import { OverviewTableAccordion } from '../../../components/Accordion'
import { useNavigate } from 'react-router-dom'
import ProjectCreateModal from '../../../components/ProjectModal/projectCreate'
import axios from 'axios'
import useToken from '../../../hooks/useToken'
import { RootState } from "../../../app/store"
import { useSelector, useDispatch } from 'react-redux'
import { removeAll } from '../../../Reducers/projectModify'


type ProjecTypes = {
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
  creationDate: string,
  lastUpdated: string,
}

const ProjectPage: React.FC = () => {
  const [loading, setLoading] = useState(true)
  const [projects, setProjects] = useState()
  const [fetchedProjects, setFetchedProjects] = useState<Array<ProjecTypes>>([])
  const [projectCreationModalState, setProjectCreationModalState] = useState<boolean>(false)
  const modifier = useSelector((state: RootState) => state.projectModifier.projects)
  const { token } = useToken()
  const navigate = useNavigate()
  const dispatch = useDispatch()

  const fetchData = async () => {
    setLoading(true)
    try {
      const result = await axios.get('/api/projects', { headers: { 'Authorization': `Bearer ${token}` } })
      const data = result.data
      console.log(data)
      setFetchedProjects(data)
      const groupData = data.reduce((acc: { [x: string]: any[] }, item: { label: string }) => {
        const key = item.label
        if (!acc[key]) {
          acc[key] = []
        }
        acc[key].push(item)
        return acc
      }, {})
      setProjects(groupData)
    } catch (err: any) {
      if (err.response.status === 401) {
        navigate('/login')
      }
      console.log(err)
    }
    setLoading(false)
  }

  const handleCloseModal = (value: boolean) => {
    setProjectCreationModalState(value)
    if (value === false)
      fetchData()
  }

  const handleDelete = () => {
    modifier.map(async (value) => {
      try {
        await axios.delete(`/api/projects/${value}`, { headers: { 'Authorization': `Bearer ${token}` } })
        fetchData()
      } catch (err: any) {
        if (err.response?.status === 401) {
          navigate('/login')
        }
        console.log(err)
      }
    })
    setProjectCreationModalState(false)
    dispatch(removeAll())
  }

  const handleDuplicate = () => {
    modifier.map(async (id) => {
      const data: ProjecTypes | undefined = fetchedProjects.find(value => value.id === id)
      if (data) {
        try {
          await axios.post('/api/projects', {
            name: data?.name,
            type: data?.type,
            abbreviation: 'abbreviation',
            description: "",
            client: data?.client,
            owner: data?.owner,
            comments: data?.comments,
            label: data?.label,
          }, {
            headers: { 'Authorization': `Bearer ${token}` }
          })
          fetchData()
        } catch (error: any) {
          if (error.response?.status === 401) {
            navigate('/login')
          }
          console.log(error)
        }
      }
    })
    setProjectCreationModalState(false)
    dispatch(removeAll())
  }

  useEffect(() => {
    fetchData()
  }, [])

  return (
    <>
      <div className='bg-white rounded-lg flex-1 flex flex-col p-7 relative'>
        <div className='text-2xl font-normal'>New Project1</div>
        <div className='mt-[2.2rem] w-full h-[3px] bg-[#00C134]'></div>
        <div className='mt-5 w-full flex flex-col'>
          <div className='flex flex-row gap-7 px-1'>
            <ProjectDropDownButton handleNew={() => handleCloseModal(!projectCreationModalState)} />
            <PersonButton />
            <FilterButton />
            <SortButton />
            <HideButton />
          </div>
          <div className='flex flex-col px-1'>
            {loading ? <>Loading...</> : <OverviewTableAccordion
              handleDelete={handleDelete}
              handleDuplicate={handleDuplicate}
              projectData={projects} />}
          </div>
        </div>
        <div className={`w-full absolute h-full -m-7 ${projectCreationModalState ? '' : 'hidden'}`}>
          <ProjectCreateModal handleClose={handleCloseModal} />
        </div>
      </div>
    </>
  )
}

export default ProjectPage