import React from 'react'

interface TableProps {
  header: Array<string>
  data: Array<Array<string>>
}

const DataTable: React.FC<TableProps> = ({ header, data }) => {
  // const widthPerentae: Array<number> = [16, 12.8, 16, 13.6, 10, 25, 6.6]
  return (
    <>
      <div className='w-full rounded-xl border border-[#979797]'>
        {header.map((value, idx) => (
          <div key={idx} className='w-full h-8 inline-flex justify-center items-center'>{value}</div>
        ))}
      </div>
    </>
  )
}

export default DataTable