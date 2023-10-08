import React from 'react'
import {
  Menu,
  MenuHandler,
  MenuList,
  MenuItem,
  Avatar,
  Typography,
} from "@material-tailwind/react"
import AndyIcon from '../../../assets/onward_icon_avatar.svg'

const ProfileMenuButton: React.FC = () => {
  return (
    <Menu placement="bottom-end">
      <MenuHandler>
        <Avatar
          variant="circular"
          alt="tania andrew"
          className="cursor-pointer"
          size="sm"
          src={AndyIcon}
        />
      </MenuHandler>
      <MenuList className="mt-5">
        <MenuItem className="flex items-center gap-3 hover:bg-[#DFE1FD]">
          <Avatar
            variant="circular"
            alt="User Logo"
            className="cursor-pointer"
            size="sm"
            src={AndyIcon}
          />
          <Typography variant="small" color="black" className="font-bold">Onward Design Consultants</Typography>
        </MenuItem>
        <div className="w-full h-[1px] mt-2 bg-[#DEDEDE]"></div>
        <MenuItem className="mt-2 flex items-center gap-2 hover:bg-[#DFE1FD]">
          <Typography variant="small" color="black" className="w-full font-medium">
            Profile
          </Typography>
          <span className="text-end hover:text-black font-medium">Ctrl+U</span>
        </MenuItem>
        <MenuItem className="flex items-center gap-2 hover:bg-[#DFE1FD]">
          <Typography variant="small" color="black" className="w-full font-medium">
            Account package
          </Typography>
          <span className="text-end hover:text-black font-medium">Ctrl+D</span>
        </MenuItem>
        <MenuItem className="flex items-center gap-2 hover:bg-[#DFE1FD]">
          <Typography variant="small" color="black" className="w-full font-medium">
            Layout Settings
          </Typography>
          <span className="text-end hover:text-black font-medium">Ctrl+L</span>
        </MenuItem>
        <MenuItem className="flex items-center gap-2 hover:bg-[#DFE1FD]">
          <Typography variant="small" color="black" className="w-full font-medium">
            Comments
          </Typography>
          <span className="text-end hover:text-black font-medium">Ctrl+Z</span>
        </MenuItem>
      </MenuList>
    </Menu>
  )
}

export default ProfileMenuButton