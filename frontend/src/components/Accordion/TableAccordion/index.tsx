import React from "react"
import {
  Accordion,
  AccordionHeader,
  AccordionBody,
} from "@material-tailwind/react"
import DataTable from "../../Table"

function Icon({ id, open, color }: any) {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      fill='none'
      viewBox="0 0 24 24"
      strokeWidth={3}
      stroke="currentColor"
      color={color}
      className={`${id === open ? "" : "-rotate-90"} h-4 w-4 mt-1 transition-transform`}
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

const headers = ['Project', 'Owner', 'Status', 'Date', 'Comments', 'Open']

const data = [
  {
    project: 'Transport Norway',
    client: 'Siemens',
    owner: 'Malcolm',
    status: 'IN PROGRESS',
    date: '30.08.23',
    comments: 'Based on carbon and steel structure structure structure'
  },
  {
    project: 'Transport London',
    client: 'Network South East',
    owner: 'Malcolm',
    status: 'DONE',
    date: '30.08.23',
    comments: 'Based on carbon and steel structure structure structure'
  },
  {
    project: 'Transport Norway',
    client: 'Siemens',
    owner: 'Malcolm',
    status: 'Working on it',
    date: '30.08.23',
    comments: 'Based on carbon and steel structure structure structure'
  },
  {
    project: 'Transport Norway',
    client: 'Siemens',
    owner: 'Malcolm',
    status: 'Working on it',
    date: '30.08.23',
    comments: 'Based on carbon and steel structure structure structure'
  },
  {
    project: 'Transport Norway',
    client: 'Siemens',
    owner: 'Malcolm',
    status: 'Working on it',
    date: '30.08.23',
    comments: 'Based on carbon and steel structure structure structure'
  },
  {
    project: 'Transport Norway',
    client: 'Siemens',
    owner: 'Malcolm',
    status: 'Working on it',
    date: '30.08.23',
    comments: 'Based on carbon and steel structure structure structure'
  },
]

const TableAccordion: React.FC = () => {
  const [open, setOpen] = React.useState(0)
  const [color, setColor] = React.useState<string>(randomColor())

  React.useEffect(() => { }, [color])

  const handleOpen = (value: number) => setOpen(open === value ? 0 : value)

  return (
    <>
      <Accordion open={open === 1}>
        <div className="flex items-center hover:cursor-pointer" onClick={() => handleOpen(1)}>
          <Icon id={1} open={open} color={color} />
          <AccordionHeader className="border-none text-black">
            <span className={`text-xl font-medium ml-1`}>
              Group of Projects 1
            </span>
          </AccordionHeader>
        </div>
        <AccordionBody>
        </AccordionBody>
      </Accordion>
    </>
  );
  }

  export default TableAccordion