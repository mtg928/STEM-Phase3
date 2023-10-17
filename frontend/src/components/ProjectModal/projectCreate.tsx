import React, { useRef, SetStateAction, Dispatch } from 'react'
// import {
//   Menu,
//   MenuHandler,
//   MenuList,
//   MenuItem,
//   Typography,
// } from "@material-tailwind/react"
import { useNavigate } from 'react-router-dom'
import axios from 'axios'
import useToken from '../../hooks/useToken'

// const clientList = ['Siemens', 'Netowrk South East', 'London Transport', 'Zurich Rail']
// const typeList = ['Type 1', 'Type 2']
// const groupList = ['Group of Projects 1', 'Group of Projects 2']
// const ownerList = ['Malcolm', 'Andy', 'Petronella', 'Veronica']

// type elementType = {
//   title: string,
//   list: Array<string>,
//   clickEvent: Function
// }

// const DropdownButton = ({ title, list, clickEvent }: elementType) => {
//   const [value, setValue] = useState<string>()

//   const handleClick = (value: string) => {
//     setValue(value)
//     clickEvent(value)
//   }

//   return (
//     <Menu placement="bottom-end">
//       <MenuHandler>
//         <div className='font-medium mt-5 w-2/5 w-min-[15rem]' >
//           <label className="block">{title}</label>
//           <div className='w-full h-9 flex items-center border border-[#DCDCDC]'>
//             <input
//               className={`block w-[calc(100%-2rem)] h-full bg-opacity-0 px-4 py-4 bg-white border-none rounded focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40`}
//               type="text"
//               value={value}
//               readOnly
//             />
//             <svg
//               className="h-8 w-8 ml-1.5 text-white" width="24" height="24"
//               viewBox="0 0 24 24" strokeWidth="2" stroke="currentColor" fill="none" strokeLinecap="round" strokeLinejoin="round">
//               <path stroke="none" d="M0 0h24v24H0z" />  <path fill="black" d="M18 15l-6-6l-6 6h12" transform="rotate(180 12 12)" />
//             </svg>
//           </div>
//         </div>
//       </MenuHandler>
//       <MenuList className="mt-1">
//         {list.map((value: string, idx: number) => (
//           <MenuItem key={idx} onClick={() => handleClick(value)} className="mt-2 flex items-center gap-2 hover:bg-[#DFE1FD]">
//             <Typography variant="small" color="black" className="w-full font-medium">
//               {value}
//             </Typography>
//           </MenuItem>
//         ))}
//       </MenuList>
//     </Menu>
//   )
// }

const ProjectCreateModal: React.FC<{ handleClose: Function }> = ({ handleClose }) => {
  // const [client, setClient] = useState<string>()
  // const [projectType, setProjectType] = useState<string>()
  // const [group, setGroup] = useState<string>()
  // const [owner, setOwner] = useState<string>()
  const projectNameRef = useRef<HTMLInputElement>(null)
  const clientRef = useRef<HTMLInputElement>(null)
  const projectTypeRef = useRef<HTMLInputElement>(null)
  const projectGroupRef = useRef<HTMLInputElement>(null)
  const ownerRef = useRef<HTMLInputElement>(null)
  const detailRef = useRef<HTMLInputElement>(null)
  const navigate = useNavigate()
  const { token } = useToken()

  const handleCreate = async () => {
    try {
      const result = await axios.post('/api/projects', {
        name: projectNameRef.current?.value,
        type: projectTypeRef.current?.value,
        abbreviation: 'abbreviation',
        description: "",
        client: clientRef.current?.value,
        owner: ownerRef.current?.value,
        comments: detailRef.current?.value,
        label: projectGroupRef.current?.value,
      }, {
        headers: { 'Authorization': `Bearer ${token}` }
      })
      if (result.data.name === projectNameRef.current?.value) {
        handleClose(false)
      }
    } catch (error: any) {
      if (error.response.code === 401) {
        navigate('/login')
      }
    }
  }
  return (
    <>
      <div className='absolute justify-center items-center w-full h-full rounded-lg flex flex-col shadow-lg'>
        <div className='w-full h-14 px-8 bg-[#0E6CD4] relative flex items-center rounded-tl-xl rounded-tr-xl text-white text-2xl'>
          <div>New Project</div>
          <div className='absolute top-3 right-3 hover:cursor-pointer' onClick={() => handleClose(false)}>
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="2" stroke="currentColor" className="w-5 h-5">
              <path strokeLinecap="round" strokeLinejoin="round" d="M6 18L18 6M6 6l12 12" />
            </svg>
          </div>
        </div>
        <div className='flex-1 flex flex-col p-5 bg-white w-full h-full shadow-black rounded-bl-xl rounded-br-xl'>
          <div className='font-medium mt-2 w-2/5 w-min-[15rem]'>
            <label className="block">Project Name</label>
            <input
              className={`block w-full h-8 px-4 py-4 mt-2 bg-white border border-[#DCDCDC] rounded focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40`}
              type="text"
              ref={projectNameRef}
            />
          </div>
          <div className='font-medium mt-5 w-2/5 w-min-[15rem]'>
            <label className="block">Client</label>
            <input
              className={`block w-full h-8 px-4 py-4 mt-2 bg-white border border-[#DCDCDC] rounded focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40`}
              type="text"
              ref={clientRef}
            />
          </div>
          <div className='font-medium mt-5 w-2/5 w-min-[15rem]'>
            <label className="block">Project Type</label>
            <input
              className={`block w-full h-8 px-4 py-4 mt-2 bg-white border border-[#DCDCDC] rounded focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40`}
              type="text"
              ref={projectTypeRef}
            />
          </div>
          <div className='font-medium mt-5 w-2/5 w-min-[15rem]'>
            <label className="block">Project Group</label>
            <input
              className={`block w-full h-8 px-4 py-4 mt-2 bg-white border border-[#DCDCDC] rounded focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40`}
              type="text"
              ref={projectGroupRef}
            />
          </div>
          <div className='font-medium mt-5 w-2/5 w-min-[15rem]'>
            <label className="block">Owner</label>
            <input
              className={`block w-full h-8 px-4 py-4 mt-2 bg-white border border-[#DCDCDC] rounded focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40`}
              type="text"
              ref={ownerRef}
            />
          </div>

          {/* <DropdownButton title="Client" list={clientList} clickEvent={setClient} />
          <DropdownButton title="Project Type" list={typeList} clickEvent={setProjectType} />
          <DropdownButton title="Project Group" list={groupList} clickEvent={setGroup} />
          <DropdownButton title="Owner" list={ownerList} clickEvent={setOwner} /> */}

          <div className='font-medium mt-5 w-2/5 w-min-[15rem]'>
            <label className="block">Details</label>
            <input
              className={`block w-full h-10 px-4 py-4 mt-2 bg-white border border-[#DCDCDC] rounded focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40`}
              type="text"
              ref={detailRef}
            />
            <div className='w-full flex justify-end mt-5 select-none' onClick={handleCreate}>
              <div className='w-[6.5rem] h-8 text-white bg-[#0E6CD4] hover:cursor-pointer hover:bg-blue-600 active:bg-blue-900 inline-flex justify-center items-center font-light rounded'>Create</div>
            </div>
          </div>
        </div>
      </div>
    </>
  )
}

export default ProjectCreateModal