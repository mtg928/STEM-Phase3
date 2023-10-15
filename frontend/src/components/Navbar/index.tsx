import React from "react"
import {
  Navbar,
  Collapse,
  Typography,
  Button,
  IconButton,
  Badge,
} from "@material-tailwind/react"
import { LazyLoadImage } from "react-lazy-load-image-component"
import { Link } from "react-router-dom"
import { ProfileMenuButton } from '../Buttons'
import AlertIcon from '../../assets/bell-alerts.svg'
import CommentIcon from '../../assets/comments-icon.svg'
import HelpIcon from '../../assets/question-icon.svg'
import StemIcon from '../../assets/stem-logo-icon.svg'
import useToken from "../../hooks/useToken"
import { useNavigate } from "react-router-dom"

const Header: React.FC = () => {
  const [openNav, setOpenNav] = React.useState(false);
  const { removeToken } = useToken()
  const navigate = useNavigate()
  const handleLogout = () => {
    removeToken()
    navigate('/login')
  }

  React.useEffect(() => {
    window.addEventListener(
      "resize",
      () => window.innerWidth >= 960 && setOpenNav(false)
    );
  }, []);

  const navList = (
    <ul className="mb-4 mt-2 flex flex-col gap-2 lg:mb-0 lg:mt-0 lg:flex-row lg:items-center lg:gap-6">
      <div>
        <Badge content="2" color="gray" className="translate-x-1 -translate-y-2">
          <Button className="rounded-full shadow-none hover:shadow-none hover:bg-opacity-5 active:bg-opacity-10 px-4 bg-opacity-0">
            <LazyLoadImage src={AlertIcon} alt="alert bell" />
          </Button>
        </Badge>
      </div>
      <div>
        <Badge content="2" color="gray" className="translate-x-1 hidden">
          <Button className="rounded-full shadow-none hover:shadow-none hover:bg-opacity-5 active:bg-opacity-10 px-4 bg-opacity-0">
            <LazyLoadImage src={CommentIcon} alt="alert bell" />
          </Button>
        </Badge>
      </div>
      <div>
        <Badge content="2" color="gray" className="translate-x-1 hidden">
          <Button className="rounded-full shadow-none hover:shadow-none hover:bg-opacity-5 active:bg-opacity-10 px-4 bg-opacity-0">
            <LazyLoadImage src={HelpIcon} alt="alert bell" />
          </Button>
        </Badge>
      </div>
      <div>
        <Link to="/login" onClick={handleLogout} className="hover:cursor-pointer">Log out</Link>
      </div>
    </ul>
  );

  return (
    <Navbar className="sticky top-0 z-10 h-max max-w-full rounded-none py-2 px-4 lg:px-8 lg:py-4 shadow-none border-none bg-[#E5F4EB]">
      <div className="flex items-center justify-between text-blue-gray-900">
        <Typography
          as="a"
          href="#"
          className="mr-4 cursor-pointer py-1.5 font-normal text-lg"
        >
          <span className="font-bold">Stem</span> Safety and Risk Management
        </Typography>
        <div className="flex items-center gap-4">
          <div className="mr-4 hidden lg:block">{navList}</div>
          <div className="ml-2 w-24 h-10 bg-white rounded-full flex justify-center items-center gap-2">
            <LazyLoadImage src={StemIcon} alt="stem logo" />
            <ProfileMenuButton />
          </div>
          <IconButton
            variant="text"
            className="ml-auto h-6 w-6 text-inherit hover:bg-transparent focus:bg-transparent active:bg-transparent lg:hidden"
            ripple={false}
            onClick={() => setOpenNav(!openNav)}
          >
            {openNav ? (
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                className="h-6 w-6"
                viewBox="0 0 24 24"
                stroke="currentColor"
                strokeWidth={2}
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  d="M6 18L18 6M6 6l12 12"
                />
              </svg>
            ) : (
              <svg
                xmlns="http://www.w3.org/2000/svg"
                className="h-6 w-6"
                fill="none"
                stroke="currentColor"
                strokeWidth={2}
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  d="M4 6h16M4 12h16M4 18h16"
                />
              </svg>
            )}
          </IconButton>
        </div>
      </div>
      <Collapse open={openNav}>
        {navList}
        <Button variant="gradient" size="sm" fullWidth className="mb-2">
          <span>Buy Now</span>
        </Button>
      </Collapse>
    </Navbar>
  );
}

export default Header