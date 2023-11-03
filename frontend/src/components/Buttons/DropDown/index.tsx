import React, { MouseEventHandler } from "react";
import {
  Menu,
  MenuHandler,
  MenuList,
  MenuItem,
  Button,
  Typography,
} from "@material-tailwind/react";

const DropDownButton: React.FC<{ handleNew: MouseEventHandler<HTMLLIElement> & MouseEventHandler<HTMLButtonElement> }> = ({ handleNew }) => {
  const [openMenu, setOpenMenu] = React.useState(false);

  return (
    <Menu open={openMenu} handler={setOpenMenu} allowHover placement="bottom-start" offset={{ crossAxis: 110, mainAxis: 10 }}>
      <MenuHandler>
        <Button
          className="w-40 h-8 px-[0.8rem] bg-[#0E6CD4] rounded hover:shadow-none flex items-center gap-3 text-sm font-normal capitalize tracking-normal shadow-none"
        >
          Project File
          <div className="h-8 w-[2px] ml-4 bg-[#4990DE]"></div>
          <svg
            className="h-4 w-4 ml-1.5 text-white" width="24" height="24"
            viewBox="0 0 24 24" strokeWidth="2" stroke="currentColor" fill="none" strokeLinecap="round" strokeLinejoin="round">
            <path stroke="none" d="M0 0h24v24H0z" />  <path fill="white" d="M18 15l-6-6l-6 6h12" transform="rotate(180 12 12)" />
          </svg>
        </Button>
      </MenuHandler>
      <MenuList className="overflow-visible">
        <MenuItem color="#DFE1FD" className="h-8 flex items-center gap-2" onClick={handleNew}>
          <Typography variant="small" color="black" className="w-full font-medium">
            New Project
          </Typography>
          <span className="text-end hover:text-black font-medium">Ctrl+N</span>
        </MenuItem>
        <MenuItem color="#DFE1FD" className="h-8 flex items-center gap-2">
          <Typography variant="small" color="black" className="w-full font-medium">
            New Project Group
          </Typography>
          <span className="text-end hover:text-black font-medium">Ctrl+G</span>
        </MenuItem>
        <MenuItem color="#DFE1FD" className="h-8 flex items-center gap-2">
          <Typography variant="small" color="black" className="w-full font-medium">
            Open Project
          </Typography>
          <span className="text-end hover:text-black font-medium">Ctrl+O</span>
        </MenuItem>
        <MenuItem color="#DFE1FD" className="h-8 flex items-center gap-2">
          <Typography variant="small" color="black" className="w-full font-medium">
            New Client
          </Typography>
          <span className="text-end hover:text-black font-medium">Ctrl+C</span>
        </MenuItem>
        <MenuItem color="#DFE1FD" className="h-8 flex items-center gap-2">
          <Typography variant="small" color="black" className="w-full font-medium">
            Open Sample Project
          </Typography>
          <span className="text-end hover:text-black font-medium">Ctrl+J</span>
        </MenuItem>
      </MenuList>
    </Menu>
  );
}

export default DropDownButton