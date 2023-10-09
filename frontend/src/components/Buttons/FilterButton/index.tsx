import React from "react"
import { LazyLoadImage } from "react-lazy-load-image-component"
import FilterIcon from '../../../assets/filter-icon.svg'

const FilterButton: React.FC = () => {
  return (
    <>
      <div className="h-8 flex items-center gap-3 bg-opacity-0 text-black text-sm select-none hover:cursor-pointer">
        <LazyLoadImage src={FilterIcon} alt="user" />
        <span className="mt-1">Filter / 1</span>
      </div>
    </>
  )
}

export default FilterButton