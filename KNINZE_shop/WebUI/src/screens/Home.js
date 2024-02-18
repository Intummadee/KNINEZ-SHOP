//หน้าโฮม โปรโมชั่น
import Navbar from "../navigation/Navbar";
import NavbarHome from "../navigation/NavbarHome";
import Carousel from 'react-bootstrap/Carousel'
import React ,{ useEffect} from "react";
import {useNavigate } from "react-router-dom";


function Home(){

  const navigate = useNavigate();
  const token = localStorage.getItem('accessToken');

  useEffect(() => {
    console.log('token', token)
    if (!token){
      
      return navigate("/login");
  }
  })

    return (
    <div style={{overflow: 'hidden'}}>
    <Navbar />
    <NavbarHome />
    <Carousel>
      <Carousel.Item interval={2000}>
        <img style={styles.imgPro}  src="https://media.discordapp.net/attachments/1122166608937361559/1173249571934195833/KNINEZ_SHOP_2.png?ex=656344cf&is=6550cfcf&hm=133c7bf4b3c024b756e861ba4ab4554d7b957350eb47ae1c8e66580af3c50437&=&width=1440&height=637" alt="First slide" />
      </Carousel.Item>
      <Carousel.Item interval={2000}>
        <img style={styles.imgPro} src="https://media.discordapp.net/attachments/1122166608937361559/1173244583589707786/KNINEZ_SHOP.png?ex=6563402a&is=6550cb2a&hm=4c58b045688e522a26406268c4ba40e52d6e4798dc76c778a6a7fe775bb676d4&=&width=1050&height=465" alt="Second slide" />
      </Carousel.Item>
      <Carousel.Item interval={2000}>
        <img style={styles.imgPro} src="https://media.discordapp.net/attachments/1122166608937361559/1173248387311751309/KNINEZ_SHOP_1.png?ex=656343b5&is=6550ceb5&hm=beea0f197f88261173c023a57c96861f8006ccf3a6d9ec0fcfb4ed080a691b0f&=&width=1440&height=637" alt="Third slide" />
      </Carousel.Item>
    </Carousel>

    </div>
         
    )
}

const styles = {
    imgPro: {
        width: '100%', 
        height: '84.6vh', // ให้ความสูงเต็มจอ
        // objectFit: 'cover', // รักษาอัตราส่วนของรูปภาพ
        
  }
}

export default Home;