import React from "react"
import {
  Accordion,
  AccordionHeader,
  AccordionBody,
  Badge,
} from "@material-tailwind/react"

function Icon({ id, open }: any) {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      fill="none"
      viewBox="0 0 24 24"
      strokeWidth={2}
      stroke="currentColor"
      className={`${id === open ? "" : "-rotate-90"} h-5 w-5 transition-transform`}
    >
      <path strokeLinecap="round" strokeLinejoin="round" d="M19.5 8.25l-7.5 7.5-7.5-7.5" />
    </svg>
  )
}

const HomeAccordion: React.FC = () => {
  const [open, setOpen] = React.useState(0)

  const handleOpen = (value: number) => setOpen(open === value ? 0 : value)

  return (
    <>
      <Accordion open={open === 1}>
        <div className="flex items-center hover:cursor-pointer" onClick={() => handleOpen(1)}>
          <Icon id={1} open={open} />
          <AccordionHeader className="mt-1 border-none text-black">
            <Badge color="blue" content="2" className="translate-x-6">
              Notifications
            </Badge>
          </AccordionHeader>
        </div>
        <AccordionBody>
          We&apos;re not always in the position that we want to be at. We&apos;re constantly
          growing. We&apos;re constantly making mistakes. We&apos;re constantly trying to express
          ourselves and actualize our dreams.
        </AccordionBody>
      </Accordion>
      <Accordion open={open === 2}>
        <div className="flex justify-center items-center hover:cursor-pointer" onClick={() => handleOpen(2)}>
          <Icon id={2} open={open} />
          <AccordionHeader className="mt-1 border-none text-black">See Plans</AccordionHeader>
        </div>
        <AccordionBody>
          We&apos;re not always in the position that we want to be at. We&apos;re constantly
          growing. We&apos;re constantly making mistakes. We&apos;re constantly trying to express
          ourselves and actualize our dreams.
        </AccordionBody>
      </Accordion>
      <Accordion open={open === 3}>
        <div className="flex justify-center items-center hover:cursor-pointer" onClick={() => handleOpen(3)}>
          <Icon id={3} open={open} />
          <AccordionHeader className="mt-1 border-none text-black">Give feedback</AccordionHeader>
        </div>
        <AccordionBody>
          We&apos;re not always in the position that we want to be at. We&apos;re constantly
          growing. We&apos;re constantly making mistakes. We&apos;re constantly trying to express
          ourselves and actualize our dreams.
        </AccordionBody>
      </Accordion>
    </>
  );
}

export default HomeAccordion