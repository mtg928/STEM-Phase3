import React from "react"
import { LazyLoadImage } from "react-lazy-load-image-component"
import HideIcon from '../../../assets/hide-icon.svg'

const HideButton: React.FC = () => {
  return (
    <>
      <div className="h-8 flex items-center gap-3 bg-opacity-0 text-black text-sm select-none hover:cursor-pointer">
        <LazyLoadImage src={HideIcon} alt="user" />
        <span className="mt-1">Hide</span>
      </div>
    </>
  )
}

export default HideButton