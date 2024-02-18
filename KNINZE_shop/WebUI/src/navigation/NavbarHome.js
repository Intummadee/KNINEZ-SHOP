import React from "react";
import { Link } from 'react-router-dom';


function NavbarHome(){
    return (
 
        <nav style={styles.navBar}>
            <div style={{padding: 15}}>
            <Link to="/foods" style={styles.productCat}>อาหาร</Link>
                <Link to="/bakery" style={styles.productCat}>ของทานเล่น</Link>
                <Link to="/drinks" style={styles.productCat}>เครื่องดื่ม</Link>
                <Link to="/medicine" style={styles.productCat}>สุขภาพและความงาม</Link>
                <Link to="/cosmetics" style={styles.productCat}>ของใช้</Link>
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
    paddingLeft: "5px",
    paddingRight: "5px",
    fontFamily: 'Mitr',
    flexDirection: 'row',
    alignContent: 'stretch',
    display: 'flex',
    justifyContent: 'center',
    backgroundColor: "#F5E1ED"
    },
    topNavBar: {
        marginRight: 25,
        fontSize: 30,
        paddingLeft: '5px',
        paddingRight: '5px',
    },
    productCat: {
        marginRight: 40,
        fontSize: 20,
        textDecoration: 'none', 
        color: 'black'
    },
   

    
  };

export default NavbarHome;