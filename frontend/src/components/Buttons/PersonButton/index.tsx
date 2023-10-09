import React from "react"
import { LazyLoadImage } from "react-lazy-load-image-component"
import UserIcon from '../../../assets/person-icon.svg'

const PersonButton: React.FC = () => {
  return (
    <>
      <div className="h-8 flex items-center gap-3 bg-opacity-0 text-black text-sm select-none hover:cursor-pointer">
        <LazyLoadImage src={UserIcon} alt="user" />
        <span className="mt-1">Person</span>
      </div>
    </>
  )
}

export default PersonButton