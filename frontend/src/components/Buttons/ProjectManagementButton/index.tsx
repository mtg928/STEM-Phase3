import React, { MouseEventHandler } from "react";
import {
  Menu,
  MenuHandler,
  MenuList,
  MenuItem,
  Button,
  Typography,
} from "@material-tailwind/react";
import ProjectManagementIcon from '../../../assets/project-manange-icon.svg'
import { LazyLoadImage } from "react-lazy-load-image-component";

const ProjectManagementButton: React.FC = () => {
  const [openMenu, setOpenMenu] = React.useState(false);

  return (
    <Menu open={openMenu} handler={setOpenMenu} allowHover placement="bottom-end" offset={{crossAxis: 1}} >
      <MenuHandler>
        <Button
          className="w-60 h-8 px-[0.8rem] bg-[#0E6CD4] rounded hover:shadow-none flex items-center gap-3 text-sm font-light capitalize tracking-normal shadow-none"
        >
          <LazyLoadImage src={ProjectManagementIcon} alt="project management" />
          Project Management
          <div className="h-8 w-[2px] ml-2 bg-[#4990DE]"></div>
          <svg
            className="h-4 w-4 ml-1.5 text-white" width="24" height="24"
            viewBox="0 0 24 24" strokeWidth="2" stroke="currentColor" fill="none" strokeLinecap="round" strokeLinejoin="round">
            <path stroke="none" d="M0 0h24v24H0z" />  <path fill="white" d="M18 15l-6-6l-6 6h12" transform="rotate(180 12 12)" />
          </svg>
        </Button>
      </MenuHandler>
      <MenuList className="overflow-visible w-[19rem] mt-2">
        <MenuItem color="#DFE1FD" className="h-8 flex items-center gap-2">
          <Typography variant="small" color="black" className="w-full font-medium">
            Project Settings
          </Typography>
          <span className="text-end hover:text-black font-medium">Ctrl+G</span>
        </MenuItem>
        <MenuItem color="#DFE1FD" className="h-8 flex items-center gap-2">
          <Typography variant="small" color="black" className="w-full font-medium">
            Client Settings
          </Typography>
          <span className="text-end hover:text-black font-medium">Ctrl+H</span>
        </MenuItem>
        <MenuItem color="#DFE1FD" className="h-8 flex items-center gap-2">
          <Typography variant="small" color="black" className="w-full font-medium">
            System Architecture
          </Typography>
          <span className="text-end hover:text-black font-medium">Ctrl+H</span>
        </MenuItem>
        <MenuItem color="#DFE1FD" className="h-8 flex items-center gap-2">
          <Typography variant="small" color="black" className="w-full font-medium">
            Electronic Components
          </Typography>
          <span className="text-end hover:text-black font-medium">Ctrl+B</span>
        </MenuItem>
      </MenuList>
    </Menu>
  );
}

export default ProjectManagementButton