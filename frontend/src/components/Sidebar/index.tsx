import React from 'react'
import {
  Card,
  List,
  ListItem,
  ListItemPrefix,
  Typography,
} from "@material-tailwind/react"
import { LazyLoadImage } from "react-lazy-load-image-component"
import { Link } from 'react-router-dom'
import { observer } from 'mobx-react'
import { useLocation } from 'react-router-dom'
import HomeIcon from '../../assets/home-icon.svg'
import OverviewIcon from '../../assets/overview-icon.svg'
import ScreenshotIcon from '../../assets/screenshot-icon.svg'
import PrintIcon from '../../assets/print-icon.svg'
import CollapseIcoin from '../../assets/colapse-arrows-menu.svg'

const Sidebar: React.FC = observer(() => {
  const [hide, setHide] = React.useState(false)
  const location = useLocation()

  return (
    <Card className={`w-[12rem] h-full p-4 shadow-xl shadow-blue-gray-900/5 select-none relative rounded-none rounded-tr-lg transition-all duration-500 ${hide ? '-mr-40 -translate-x-40' : ''}`}>
      <List className='min-w-[8.5rem]'>
        <Link to='/'>
          <ListItem selected={location.pathname === '/'} className='w-[8.5rem] rounded focus:bg-[#DFE1FD] active:bg-[#DFE1FD] py-2'>
            <ListItemPrefix>
              <LazyLoadImage src={HomeIcon} alt="Home" />
            </ListItemPrefix>
            <Typography className="text-black text-sm font-medium -ml-1">Home</Typography>
          </ListItem>
        </Link>
        <Link to='/overview'>
          <ListItem selected={location.pathname === '/overview'} className='w-[8.5rem] rounded focus:bg-[#DFE1FD] active:bg-[#DFE1FD] py-2'>
            <ListItemPrefix>
              <LazyLoadImage src={OverviewIcon} alt="Overview" />
            </ListItemPrefix>
            <Typography className="text-black text-sm font-medium -ml-1">Overview</Typography>
          </ListItem>
        </Link>
        <div className="w-[12rem] h-1 -ml-6 mt-2 mb-2 bg-[#E5F4EB]" />
        <Link to='/screenshot'>
          <ListItem selected={location.pathname === '/screenshot'} className='w-[8.5rem] rounded focus:bg-[#DFE1FD] active:bg-[#DFE1FD] py-2'>
            <ListItemPrefix>
              <LazyLoadImage src={ScreenshotIcon} alt="Screen Shot" />
            </ListItemPrefix>
            <Typography className="text-black text-sm font-medium -ml-1">Screen Shot</Typography>
          </ListItem>
        </Link>
        <Link to='/print'>
          <ListItem selected={location.pathname === '/print'} className='w-[8.5rem] rounded focus:bg-[#DFE1FD] active:bg-[#DFE1FD] py-2'>
            <ListItemPrefix>
              <LazyLoadImage src={PrintIcon} alt="Print" />
            </ListItemPrefix>
            <Typography className="text-black text-sm font-medium -ml-1">Print</Typography>
          </ListItem>
        </Link>
      </List>
      <div onClick={() => setHide(!hide)} className='absolute right-2 top-2 hover:cursor-pointer'>
        <LazyLoadImage src={CollapseIcoin} alt="Collapse" className={`transition-all duration-500 ${hide ? 'rotate-180 transform' : ''}`} />
      </div>
    </Card>
  )
})

export default Sidebar