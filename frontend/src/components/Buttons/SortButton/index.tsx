import React from "react"
import { LazyLoadImage } from "react-lazy-load-image-component"
import SortIcon from '../../../assets/sort-icon.svg'

const SortButton: React.FC = () => {
  return (
    <>
      <div className="h-8 flex items-center gap-3 bg-opacity-0 text-black text-sm select-none hover:cursor-pointer">
        <LazyLoadImage src={SortIcon} alt="user" />
        <span className="mt-1">Sort / 2</span>
      </div>
    </>
  )
}

export default SortButton