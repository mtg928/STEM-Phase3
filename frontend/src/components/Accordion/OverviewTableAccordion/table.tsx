import React from 'react'
import { Card, Checkbox, Typography } from "@material-tailwind/react"
import { RootState } from "../../../app/store"
import { add, remove } from '../../../Reducers/projectModify'
import { useDispatch, useSelector } from 'react-redux'
import { Link } from 'react-router-dom'


type TableProperty = {
  id: number,
  name: string,
  client: string,
  owner: string,
  status: string,
  lastUpdated: string,
  comments: string,
}

interface TableProps {
  header: Array<string>
  data: Array<TableProperty>
  checkbox: boolean
  widthArray: Array<number>
}

//16, 13, 16, 13, 10, 25, 7

const DataTable = ({ header, data, widthArray }: TableProps) => {
  const modifier = useSelector((state: RootState) => state.projectModifier.projects)
  const dispatch = useDispatch()
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>, id: number) => {
    if (e.target.checked) {
      dispatch(add(id))
    } else {
      dispatch(remove(id))
    }
  }
  React.useEffect(() => {
    if (modifier.length === 0) {
      const checkboxes = document.getElementsByName('project_checkbox');
      checkboxes.forEach((value: any) => value.checked = false)
    }
  }, [modifier.length])
  return (
    <>
      <Card className="h-full w-full overflow-hidden shadow-none border border-[#979797]">
        <table className="w-full table-auto text-left">
          <thead>
            <tr>
              {header.map((head, idx) => (
                <th key={idx} className={`h-9 px-4 text-center`} style={{ width: `${widthArray[idx]}%` }}>
                  <Typography
                    variant="small"
                    color="black"
                    className="font-medium leading-none"
                  >
                    {head}
                  </Typography>
                </th>
              ))}
            </tr>
          </thead>
          <tbody>
            {data.map(({ id, name, client, owner, status, lastUpdated, comments }, index) => (
              <tr key={index} className="hover:bg-[#F3F6FA] border h-8 truncate">
                <td className="h-8 border w-16%">
                  <div className={`flex items-center px-2`}>
                    <Checkbox
                      className="hover:before:opacity-0 rounded-none w-4 h-4"
                      containerProps={{
                        className: "p-0",
                      }}
                      name='project_checkbox'
                      color='green'
                      ripple={false}
                      crossOrigin={Checkbox}
                      onChange={(e) => handleChange(e, id)}
                    />
                    <div className='px-3 text-sm font-normal text-black truncate'>{name}</div>
                  </div>
                </td>
                <td className="h-8 border text-center">
                  <div className={`text-sm font-normal text-black truncate`}>{client}</div>
                </td>
                <td className="h-8 border text-center">
                  <div className={`text-sm font-normal text-black truncate`}>{owner}</div>
                </td>
                <td className={`h-8 border text-center ${status === 'DONE' ? 'bg-[#02A42E]' : 'bg-[#FFAF35]'}`}>
                  <div className={`text-sm font-normal text-white truncate`}>
                    {status === 'DONE' ? 'Done' : 'Working on it'}
                  </div>
                </td>
                <td className="h-8 border text-center">
                  <div className={`text-sm font-normal text-black truncate`}>{new Date(lastUpdated).toLocaleDateString('en-US', {
                    day: '2-digit',
                    month: '2-digit',
                    year: '2-digit',
                  }).replace(/\//g, '.')}</div>
                </td>
                <td className="h-8 border text-center">
                  <div className={`px-8 w-[calc(25vw)] text-sm font-normal text-black truncate`}>{comments}</div>
                </td>
                <td className="h-8 border">
                  <div className={`h-8 w-full inline-flex justify-center items-center`}>
                    <Link to={`/overview/${id}`}>
                      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" className="w-6 h-6">
                        <path strokeLinecap="round" strokeLinejoin="round" d="M2.036 12.322a1.012 1.012 0 010-.639C3.423 7.51 7.36 4.5 12 4.5c4.638 0 8.573 3.007 9.963 7.178.07.207.07.431 0 .639C20.577 16.49 16.64 19.5 12 19.5c-4.638 0-8.573-3.007-9.963-7.178z" />
                        <path strokeLinecap="round" strokeLinejoin="round" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                      </svg>
                    </Link>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </Card>
    </>
  )
}

export default DataTable