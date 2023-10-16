import React, { MouseEventHandler } from "react";
import {
  Menu,
  MenuHandler,
  MenuList,
  MenuItem,
  Typography,
} from "@material-tailwind/react";
import { LazyLoadImage } from "react-lazy-load-image-component";
import AddCalculatorIcon from '../../../assets/add-calculator-icon.svg'

const CalculatorAddButton: React.FC = () => {
  const [openMenu, setOpenMenu] = React.useState(false);

  return (
    <Menu open={openMenu} handler={setOpenMenu} allowHover placement="bottom-end">
      <MenuHandler>
        <LazyLoadImage src={AddCalculatorIcon} alt="project management" />
      </MenuHandler>
      <MenuList className="overflow-visible w-[19rem] drop-shadow-xl -mt-5">
        <MenuItem color="#DFE1FD" className="h-8 flex items-center gap-2">
          <Typography variant="small" color="black" className="w-full font-medium">
            Safety Integrity Level (SIL)
          </Typography>
        </MenuItem>
        <MenuItem color="#DFE1FD" className="h-8 flex items-center gap-2">
          <Typography variant="small" color="black" className="w-full font-medium">
            Failure & Effects (FMECA)
          </Typography>
        </MenuItem>
        <MenuItem color="#DFE1FD" className="h-8 flex items-center gap-2">
          <Typography variant="small" color="black" className="w-full font-medium">
            Failure & Effects Criticality (FMECA)
          </Typography>
        </MenuItem>
        <MenuItem color="#DFE1FD" className="h-8 flex items-center gap-2" disabled>
          <Typography variant="small" color="black" className="w-full font-medium">
            Common Cause Failure (CCF)
          </Typography>
        </MenuItem>
        <MenuItem color="#DFE1FD" className="h-8 flex items-center gap-2" disabled>
          <Typography variant="small" color="black" className="w-full font-medium">
            Event Tree (ETA)
          </Typography>
        </MenuItem>
        <MenuItem color="#DFE1FD" className="h-8 flex items-center gap-2" disabled>
          <Typography variant="small" color="black" className="w-full font-medium">
            Fault Tree (FTA)
          </Typography>
        </MenuItem>
      </MenuList>
    </Menu>
  );
}

export default CalculatorAddButton