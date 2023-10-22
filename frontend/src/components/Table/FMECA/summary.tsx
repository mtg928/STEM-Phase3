import React from 'react'
import { Card, Typography } from "@material-tailwind/react"
import { Link, useLocation } from "react-router-dom";

type DataTypes = {
  id: number,
  componentId: number,
  functionName: string,
  mpgType: string,
  calcfileId: number,
  calcfile: string,
  standards: string,
  comments: string | null,
}

const headers = ['Number', 'Component ID', 'Function Name', 'MPG Type', 'Calcfile ID', 'Calcfile', 'Standards', 'Comments', 'Details']
const width = [6.766, 9.315, 12.478, 16.96, 6.766, 9.84, 21.265, 6.59]

const FMECASummaryTable: React.FC<{ calcData: any }> = ({ calcData }) => {
  const location = useLocation()
  return (
    <>
      <Card className="h-full w-full overflow-hidden shadow-none border border-[#979797]">
        <table className="w-full table-auto text-left">
          <thead>
            <tr>
              {headers.map((head, idx) => (
                <th key={idx} className={`h-9 text-center px-2`} style={{ width: `${width[idx]}%` }}>
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
          <tbody>{calcData?.map((value: DataTypes, index: number) => (
            <tr key={index} className="hover:bg-[#F3F6FA] border h-8 truncate">
              <td className="h-8 border text-center">
                <div className='px-8 text-sm font-normal text-black truncate'>{index + 1}</div>
              </td>
              <td className="h-8 border text-center">
                <div className='px-8 text-sm font-normal text-black truncate'>{value.componentId}</div>
              </td>
              <td className="h-8 border text-center">
                <div className='px-8 text-sm font-normal text-black truncate'>{value.functionName}</div>
              </td>
              <td className="h-8 border text-center">
                <div className='px-8 text-sm font-normal text-black truncate'>{value.mpgType}</div>
              </td>
              <td className="h-8 border text-center">
                <div className='px-8 text-sm font-normal text-black truncate'>{value.calcfileId}</div>
              </td>
              <td className="h-8 border text-center">
                <div className='px-8 text-sm font-normal text-black truncate'>{value.calcfile}</div>
              </td>
              <td className="h-8 border text-center">
                <div className='px-8 text-sm font-normal text-black truncate'>{value.standards}</div>
              </td>
              <td className="h-8 border text-center">
                <div className={`px-5 w-[calc(21vw)] text-sm font-normal text-black truncate`}>{value.comments}</div>
              </td>
              <td className="h-8 border">
                <div className={`h-8 w-full inline-flex justify-center items-center`}>
                  <Link to={`${location.pathname}/fmeca/${value.id}`}>
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

export default FMECASummaryTable