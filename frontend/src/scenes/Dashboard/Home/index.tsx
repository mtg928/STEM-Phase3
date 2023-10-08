import React from 'react'
import { LazyLoadImage } from "react-lazy-load-image-component"
import HomeAccordion from "../../../components/HomeAccordion"
import StemLogo from '../../../assets/stem-icon-white.svg'

const HomePage: React.FC = () => {
  return (
    <>
      <div className="bg-[#000052] text-2xl font-light rounded w-full h-16 text-white flex items-center px-9">
        <span className="font-medium">Hello Andy,</span>&nbsp;welcome back to Stems
        <span className="text-sm flex-1 text-end">Stem version 2</span>
      </div>
      <div className="mt-3 w-full h-24 bg-[#02A42E] px-9 flex rounded items-center">
        <LazyLoadImage src={StemLogo} alt="Stem Logo" width="62" />
        <div className="ml-9 w-60 h-12 hover:cursor-pointer border border-white rounded text-white bg-opacity-0 inline-flex justify-center items-center select-none font-normal text-xl">Take me to my projects</div>
      </div>
      <div className="mt-3 flex flex-1 gap-3">
        <div className="bg-white flex-1 rounded-tl-lg mb-3 py-5 px-11">
          <HomeAccordion />
        </div>
        <div className="w-[19rem] h-full">
          <div className="w-full h-11 rounded bg-[#000052] px-6 text-white inline-flex items-center">Learn & getting started</div>
          <div className="w-full h-16 mt-3 rounded bg-white inline-flex items-center">
            <div className="w-16 h-11 ml-3 bg-[#000052] inline-flex items-center justify-center rounded-md"><LazyLoadImage src={StemLogo} alt="Logo" /></div>
            <span className="ml-3 text-sm font-medium">Reference Manual</span>
          </div>
          <div className="w-full h-16 mt-3 rounded bg-white inline-flex items-center">
            <div className="w-16 h-11 ml-3 bg-[#02A42E] inline-flex items-center justify-center rounded-md"><LazyLoadImage src={StemLogo} alt="Logo" /></div>
            <span className="ml-3 text-sm font-medium">Overview</span>
          </div>
          <div className="w-full h-16 mt-3 rounded bg-white inline-flex items-center">
            <div className="w-16 h-11 ml-3 bg-[#000052] inline-flex items-center justify-center rounded-md"><LazyLoadImage src={StemLogo} alt="Logo" /></div>
            <span className="ml-3 text-sm font-medium">Tutorials</span>
          </div>
          <div className="w-full h-16 mt-3 rounded bg-white inline-flex items-center">
            <div className="w-16 h-11 ml-3 bg-[#02A42E] inline-flex items-center justify-center rounded-md"><LazyLoadImage src={StemLogo} alt="Logo" /></div>
            <span className="ml-3 text-sm font-medium">Example Projects</span>
          </div>
          <div className="w-full h-16 mt-3 rounded bg-white inline-flex items-center">
            <div className="w-16 h-11 ml-3 bg-[#000052] inline-flex items-center justify-center rounded-md"><LazyLoadImage src={StemLogo} alt="Logo" /></div>
            <span className="ml-3 text-sm font-medium">Support</span>
          </div>
        </div>
      </div>
    </>
  )
}

export default HomePage