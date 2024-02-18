import React, {useState} from "react";
import { Link , useNavigate} from 'react-router-dom';
import { BiHomeAlt, BiBell, BiCartAlt, BiUser } from "react-icons/bi";
import Button from '@mui/material/Button';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import Fade from '@mui/material/Fade';


function Navbar(){

    
    const navigate = useNavigate();
    const [anchorEl, setAnchorEl] = useState(null);
    const open = Boolean(anchorEl);
    const handleClick = (event) => {
      setAnchorEl(event.currentTarget);
    };
    const handleClose = () => {
      setAnchorEl(null);
    };

    const handleLogout = () => {
        setAnchorEl(null);
        localStorage.removeItem('accessToken');
        localStorage.removeItem('emailToken');
        navigate("/login");
    }
  

    return (
        <nav style={styles.navBar}>
            <label style={{justifyContent: 'center', fontSize: 30, marginLeft: 15, paddingTop: 4,}}><b>KNINEZ SHOP</b></label>
            <div style={{paddingTop: 4, paddingBottom: 5}}>
                <Link to="/" style={styles.topNavBar}><BiHomeAlt /></Link>
                <Link to="/basket" style={styles.topNavBar}><BiCartAlt /></Link>
                <Link to="/noti" style={styles.topNavBar}><BiBell /></Link>
                <Button id="fade-button" style={styles.topNavBarProfile} aria-controls={open ? 'fade-menu' : undefined} aria-haspopup="true" aria-expanded={open ? 'true' : undefined} onClick={handleClick}><BiUser /></Button>
                <Menu id="fade-menu" MenuListProps={{'aria-labelledby': 'fade-button',}} anchorEl={anchorEl} open={open} onClose={handleClose} TransitionComponent={Fade}>
                    <MenuItem onClick={handleClose}><Link to="/profile" style={{fontFamily: 'Mitr', textDecoration: 'none', color: 'black'}}>โปรไฟล์ผู้ใช้</Link></MenuItem>
                    <MenuItem onClick={handleClose}><Link to="/history" style={{fontFamily: 'Mitr', textDecoration: 'none', color: 'black'}}>ประวัติการสั่งซื้อ</Link></MenuItem>
                    <MenuItem onClick={handleLogout}><Link to="/login" style={{fontFamily: 'Mitr', textDecoration: 'none', color: 'black'}}>ออกจากระบบ</Link></MenuItem>
                </Menu>
            </div>
        </nav>
        
    )
}

//#F2C898
//#F9C2AD
//#E0A2C9
//#F5E1ED
//#C6E4E4
//#77BCC3

    
const styles = {
    navBar: {
    backgroundColor: "#E0A2C9",
    paddingLeft: "5px",
    paddingRight: "5px",
    fontFamily: 'Mitr',
    flexDirection: 'row',
    alignContent: 'stretch',
    display: 'flex',
    justifyContent: 'space-between',
    paddingTop: 20,
    paddingBottom: 20,



   
    },
    topNavBar: {
        marginRight: 25,
        fontSize: 30,
        paddingLeft: '5px',
        paddingRight: '5px',
        color: 'black'
    },
    productCat: {
        marginRight: 25,
        fontSize: 15
    },
    topNavBarProfile: {
        marginRight:20,
        fontSize: 30,
        marginLeft: -10,
        marginTop: -9,
        color: 'black'
    },

    
  };

export default Navbar;