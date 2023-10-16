import React, { useState, useEffect } from "react"
import { RootState } from "../../../app/store"
import { useSelector } from 'react-redux'
import {
  Accordion,
  AccordionHeader,
  AccordionBody,
} from "@material-tailwind/react"
import DataTable from "./table"
import ProjectModal from "../../ProjectModal"

function Icon({ open, color }: any) {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      fill='none'
      viewBox="0 0 24 24"
      strokeWidth={3}
      stroke="currentColor"
      color={color}
      className={`${open === 0 ? "" : "-rotate-90"} h-4 w-4 mt-1 transition-transform`}
    >
      <path strokeLinecap="round" strokeLinejoin="round" d="M19.5 8.25l-7.5 7.5-7.5-7.5" />
    </svg>
  )
}

const randomColor = (): string => {
  const hexValues = '0123456789ABCDEF';
  let colorCode = '#';
  for (let i = 0; i < 6; i++) {
    colorCode += hexValues[Math.floor(Math.random() * 16)];
  }
  return colorCode;
}

const headers = ['Project', 'Client', 'Owner', 'Status', 'Date', 'Comments', 'Details']

const widthPerentage: Array<number> = [16, 13, 16, 13, 10, 25, 7]

const TableAccordion: React.FC<any> = ({ projectData }: any) => {
  const modifier = useSelector((state: RootState) => state.projectModifier.projects)
  const [open, setOpen] = useState<Array<boolean>>([])
  const [color, setColor] = useState<string>()

  useEffect(() => {
    setColor(randomColor())
  }, [])

  const handleOpen = (value: number) => {
    setOpen(prevOpen => {
      const temp = [...prevOpen]
      temp[value] = !temp[value]
      return temp
    })
  }

  return (
    <>
      {projectData && Object?.keys(projectData)?.map((label, idx) => (
        <Accordion open={open[idx] === true} key={idx} className="mt-10">
          <div className="flex items-center hover:cursor-pointer" onClick={() => handleOpen(idx)}>
            <Icon open={open[idx] === true} color={color} />
            <AccordionHeader className="border-none text-black">
              <span className={`text-xl font-medium ml-1`}>
                {label}
              </span>
            </AccordionHeader>
          </div>
          <AccordionBody>
            <DataTable header={headers} data={projectData[label]} checkbox widthArray={widthPerentage} />
          </AccordionBody>
        </Accordion>
      ))}

      <div className={`w-full flex justify-center ${modifier.length > 0 ? '' : 'hidden'}`}>
        <ProjectModal />
      </div>
    </>
  );
}

export default TableAccordion