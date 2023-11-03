import React from 'react'
import { CalculatorDataTypes } from './type'
import { Tooltip } from "@material-tailwind/react";
import {
  Accordion,
  AccordionHeader,
  AccordionBody,
} from "@material-tailwind/react";

function Icon({ id, open }: any) {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      fill="none"
      viewBox="0 0 24 24"
      strokeWidth={2}
      stroke="currentColor"
      className={`${id === open ? "" : "-rotate-90"} h-5 w-5 divansition-divansform`}
    >
      <path strokeLinecap="round" strokeLinejoin="round" d="M19.5 8.25l-7.5 7.5-7.5-7.5" />
    </svg>
  )
}

const FMECATable: React.FC<{ data: Array<CalculatorDataTypes>, handleUpdate: Function }> = ({ data, handleUpdate }) => {
  const [open, setOpen] = React.useState(1);

  const handleOpen = (value: number) => setOpen(open === value ? 0 : 1);

  const handleInputBlur = (e: React.ChangeEvent<HTMLInputElement>, id: number) => {
    const { name, value } = e.target
    const findedObj: any = data.find(item => item.id === id)
    if (findedObj) {
      if (findedObj[name] != value && value != '') {
        let updateRow: CalculatorDataTypes = findedObj
        updateRow = { ...updateRow, [name]: value }
        handleUpdate(updateRow)
      }
    }
  }

  return (
    <>
      <Accordion open={open === 1} >
        <div className='w-full flex flex-row'>
          <div className='h-14 w-2/5 bg-[#333375] text-white border inline-flex justify-center items-center'>Failure Identification</div>
          <div className='h-14 w-2/5 bg-[#333375] text-white border inline-flex justify-center items-center'>Failure Effects</div>
          <div className='h-14 w-1/5 bg-[#333375] text-white inline-flex justify-center items-center'>Detection and Recovery Measures</div>
        </div>
        <div className="w-full flex flex-row h-8">
          <div className='text-sm font-medium w-2/5 h-full flex flex-row'>
            <div className='w-7 text-center'></div>
            <div className='w-12 inline-flex justify-center items-center'>ID</div>
            <div className='flex w-[calc(100%-4.75rem)]'>
              <Tooltip content="SubSystemCode" placement="bottom" className="bg-white text-black border border-black">
                <div className='w-1/6 flex justify-center items-center select-none overflow-auto'>
                  <p className='truncate'>SubSystemCode</p>
                </div>
              </Tooltip>
              <Tooltip content="SubSystemComponent" placement="bottom" className="bg-white text-black border border-black">
                <div className='w-1/6 flex justify-center items-center select-none overflow-auto'>
                  <p className='truncate'>SubSystemComponent</p>
                </div>
              </Tooltip>
              <Tooltip content="Function" placement="bottom" className="bg-white text-black border border-black">
                <div className='w-1/6 flex justify-center items-center select-none overflow-auto'>
                  <p className='truncate'>Function</p>
                </div>
              </Tooltip>
              <Tooltip content="Phase" placement="bottom" className="bg-white text-black border border-black">
                <div className='w-1/6 flex justify-center items-center select-none overflow-auto'>
                  <p className='truncate'>Phase</p>
                </div>
              </Tooltip>
              <Tooltip content="FailureMode" placement="bottom" className="bg-white text-black border border-black">
                <div className='w-1/6 flex justify-center items-center select-none overflow-auto'>
                  <p className='truncate'>FailureMode</p>
                </div>
              </Tooltip>
              <Tooltip content="FailureCause" placement="bottom" className="bg-white text-black border border-black">
                <div className='w-1/6 flex justify-center items-center select-none overflow-auto'>
                  <p className='truncate'>FailureCause</p>
                </div>
              </Tooltip>
            </div>
          </div>
          <div className='text-sm font-medium w-2/5 h-full flex flex-row'>
            <Tooltip content="SystemCode" placement="bottom" className="bg-white text-black border border-black">
              <div className='w-full flex justify-center items-center select-none overflow-auto'>
                <p className='truncate'>SystemCode</p>
              </div>
            </Tooltip>
            <Tooltip content="SystemComponent" placement="bottom" className="bg-white text-black border border-black">
              <div className='w-full flex justify-center items-center select-none overflow-auto'>
                <p className='truncate'>SystemComponent</p>
              </div>
            </Tooltip>
            <Tooltip content="SeverityClass" placement="bottom" className="bg-white text-black border border-black">
              <div className='w-full flex justify-center items-center select-none overflow-auto'>
                <p className='truncate'>SeverityClass</p>
              </div>
            </Tooltip>
            <Tooltip content="FailureProbability" placement="bottom" className="bg-white text-black border border-black">
              <div className='w-full flex justify-center items-center select-none overflow-auto'>
                <p className='truncate'>FailureProbability</p>
              </div>
            </Tooltip>
            <Tooltip content="FailureEffectProbability" placement="bottom" className="bg-white text-black border border-black">
              <div className='w-full flex justify-center items-center select-none overflow-auto'>
                <p className='truncate'>FailureEffectProbability</p>
              </div>
            </Tooltip>
          </div>
          <div className='text-sm font-medium w-1/5 h-full flex flex-row'>
            <Tooltip content="FailureModeRatio" placement="bottom" className="bg-white text-black border border-black">
              <div className='w-full flex justify-center items-center select-none overflow-auto'>
                <p className='truncate'>FailureModeRatio</p>
              </div>
            </Tooltip>
            <Tooltip content="FailureRate" placement="bottom" className="bg-white text-black border border-black">
              <div className='w-full flex justify-center items-center select-none overflow-auto'>
                <p className='truncate'>FailureRate</p>
              </div>
            </Tooltip>
            <Tooltip content="OperatingTimeInHours" placement="bottom" className="bg-white text-black border border-black">
              <div className='w-full flex justify-center items-center select-none overflow-auto'>
                <p className='truncate'>OperatingTimeInHours</p>
              </div>
            </Tooltip>
            <div className='w-full select-none font-bold inline-flex justify-center items-center'>FMC</div>
          </div>
        </div>
        <div className='w-full h-8 flex flex-row bg-[#DFE0E1]'>
          <div className='text-sm font-medium w-2/5 h-full flex flex-row'>
            <div className='w-7 border text-center inline-flex justify-center items-center'>1</div>
            <div className='w-12 border inline-flex justify-center items-center'>0000</div>
            <div className='flex flex-1 h-full'>
              <div className='w-full border inline-flex justify-center items-center'>RC</div>
              <div className='w-full border inline-flex justify-center items-center'>Top level</div>
              <div className='w-full border inline-flex justify-center items-center' /><div className='w-full border inline-flex justify-center items-center' /><div className='w-full border inline-flex justify-center items-center' /><div className='w-full border inline-flex justify-center items-center' />
            </div>
          </div>
          <div className='text-sm font-medium w-2/5 h-8 flex flex-row'>
          <div className='w-full border inline-flex justify-center items-center' /><div className='w-full border inline-flex justify-center items-center' />
            <div className='w-full border inline-flex justify-center items-center' /><div className='w-full border inline-flex justify-center items-center' /><div className='w-full border inline-flex justify-center items-center' /><div className='' />
          </div>
          <div className='text-sm font-medium w-1/5 h-8 flex flex-row'>
            <div className='w-full border inline-flex justify-center items-center' /><div className='w-full border inline-flex justify-center items-center' /><div className='w-full border inline-flex justify-center items-center' />
            <div className='w-full border inline-flex justify-center items-center' />
          </div>
        </div>
        <AccordionHeader className="border-none w-full py-0 text-black" onClick={() => handleOpen(1)}>
          <div className='absolute left-0 -ml-5 -mt-7'><Icon id={1} open={open} /></div>
        </AccordionHeader>
        <AccordionBody className="py-[0.2px] w-full">
          {
            data?.sort((a, b) => a.id - b.id)?.map((rowValue, idx: number) => (
              <div key={idx} className='w-full flex flex-row text-sm font-medium'>
                <div className='w-2/5 h-8 flex'>
                  <div className='border w-7 border-[#DFE0E1] flex justify-center items-center'>
                    <div className='truncate'>{idx + 2}</div>
                  </div>
                  <div className='w-12 border border-[#DFE0E1] flex justify-center items-center'>
                    <div className='truncate'>{rowValue.id.toString().padStart(4, '0')}</div>
                  </div>
                  <div className='flex flex-1 h-full'>
                    <div className='border border-[#DFE0E1] flex justify-center items-center'>
                      <input name="subSystemCode" onBlur={(e) => handleInputBlur(e, rowValue.id)} className='w-full h-full truncate text-center focus:text-left focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40' defaultValue={rowValue.subSystemCode} />
                    </div>
                    <div className='border border-[#DFE0E1] flex justify-center items-center'>
                      <input name="subSystemComponent" onBlur={(e) => handleInputBlur(e, rowValue.id)} className='w-full h-full truncate text-center focus:text-left focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40' defaultValue={rowValue.subSystemComponent} />
                    </div>
                    <div className='border border-[#DFE0E1] flex justify-center items-center'>
                      <input name="function" onBlur={(e) => handleInputBlur(e, rowValue.id)} className='w-full h-full truncate text-center focus:text-left focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40' defaultValue={rowValue.function} />
                    </div>
                    <div className='border border-[#DFE0E1] flex justify-center items-center'>
                      <input name="phase" onBlur={(e) => handleInputBlur(e, rowValue.id)} className='w-full h-full truncate text-center focus:text-left focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40' defaultValue={rowValue.phase} />
                    </div>
                    <div className='border border-[#DFE0E1] flex justify-center items-center'>
                      <input name="failureMode" onBlur={(e) => handleInputBlur(e, rowValue.id)} className='w-full h-full truncate text-center focus:text-left focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40' defaultValue={rowValue.failureMode} />
                    </div>
                    <div className='border border-[#DFE0E1] flex justify-center items-center'>
                      <input name="failureCause" onBlur={(e) => handleInputBlur(e, rowValue.id)} className='w-full h-full truncate text-center focus:text-left focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40' defaultValue={rowValue.failureCause} />
                    </div>
                  </div>
                </div>
                <div className='w-2/5 h-8 flex'>
                  <div className='border border-[#DFE0E1] flex justify-center items-center w-1/5'>
                    <input name="systemCode" onBlur={(e) => handleInputBlur(e, rowValue.id)} className='w-full h-full truncate text-center focus:text-left focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40' defaultValue={rowValue.systemCode} />
                  </div>
                  <div className='border border-[#DFE0E1] flex justify-center items-center w-1/5'>
                    <input name="systemComponent" onBlur={(e) => handleInputBlur(e, rowValue.id)} className='w-full h-full truncate text-center focus:text-left focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40' defaultValue={rowValue.systemComponent} />
                  </div>
                  <div className='border border-[#DFE0E1] flex justify-center items-center w-1/5'>
                    <input name="severityClass" onBlur={(e) => handleInputBlur(e, rowValue.id)} className='w-full h-full truncate text-center focus:text-left focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40' defaultValue={rowValue.severityClass} />
                  </div>
                  <div className='border border-[#DFE0E1] flex justify-center items-center w-1/5'>
                    <input name="failureProbability" onBlur={(e) => handleInputBlur(e, rowValue.id)} className='w-full h-full truncate text-center focus:text-left focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40' defaultValue={rowValue.failureProbability} />
                  </div>
                  <div className='border border-[#DFE0E1] flex justify-center items-center w-1/5'>
                    <input name="failureEffectProbability" onBlur={(e) => handleInputBlur(e, rowValue.id)} className='w-full h-full truncate text-center focus:text-left focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40' defaultValue={rowValue.failureEffectProbability} />
                  </div>
                </div>
                <div className='w-1/5 h-8 flex'>
                  <div className='border border-[#DFE0E1] flex justify-center items-center w-1/4'>
                    <input name="failureModeRatio" onBlur={(e) => handleInputBlur(e, rowValue.id)} className='w-full h-full truncate text-center focus:text-left focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40' defaultValue={rowValue.failureModeRatio} />
                  </div>
                  <div className='border border-[#DFE0E1] flex justify-center items-center w-1/4'>
                    <input name="failureRate" onBlur={(e) => handleInputBlur(e, rowValue.id)} className='w-full h-full truncate text-center focus:text-left focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40' defaultValue={rowValue.failureRate} />
                  </div>
                  <div className='border border-[#DFE0E1] flex justify-center items-center w-1/4'>
                    <input name="operatingTimeInHours" onBlur={(e) => handleInputBlur(e, rowValue.id)} className='w-full h-full truncate text-center focus:text-left focus:border-blue-400 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40' defaultValue={rowValue.operatingTimeInHours} />
                  </div>
                  <div className='border border-[#DFE0E1] flex justify-center items-center w-1/4'>
                    <div className='w-full text-center'>{rowValue.failureModeCriticality}</div>
                  </div>
                </div>
              </div>
            ))
          }
        </AccordionBody>
      </Accordion >
    </>
  )
}

export default FMECATable