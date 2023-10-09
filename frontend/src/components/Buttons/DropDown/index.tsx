import React from "react";
import {
  Menu,
  MenuHandler,
  MenuList,
  MenuItem,
  Button,
  Typography,
} from "@material-tailwind/react";

const menuItems = [
  {
    title: "New Project",
    hotkey: "Ctrl+N",
  },
  {
    title: "New Project Group",
    hotkey: "Ctrl+G",
  },
  {
    title: "Open Project",
    hotkey: "Ctrl+O",
  },
  {
    title: "New Client",
    hotkey: "Ctrl+C",
  },
  {
    title: "Open Sample Project",
    hotkey: "Ctrl+J",
  },
];

const DropDownButton: React.FC = () => {
  const [openMenu, setOpenMenu] = React.useState(false);

  return (
    <Menu open={openMenu} handler={setOpenMenu} allowHover placement="bottom-start" offset={{crossAxis: 110, mainAxis: 10}}>
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
        {menuItems.map(({ title, hotkey }, idx: number) => (
          <MenuItem key={idx} color="#DFE1FD" className="h-8 flex items-center gap-2">
            <Typography variant="small" color="black" className="w-full font-medium">
              {title}
            </Typography>
            <span className="text-end hover:text-black font-medium">{hotkey}</span>
          </MenuItem>
        ))}
      </MenuList>
    </Menu>
  );
}

export default DropDownButton