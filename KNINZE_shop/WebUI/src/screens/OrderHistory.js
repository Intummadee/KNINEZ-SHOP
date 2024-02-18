import React , {useEffect, useState} from "react";
import Navbar from "../navigation/Navbar";
import {BiBasket } from "react-icons/bi";
// import axios from "axios";

function OrderHistory(){

    // const [listData, setListData] = useState([]);
    // const emailByToken = localStorage.getItem('emailToken')
    const [cart, setCart] = useState([]);
   

    useEffect( () => {
        const storedCart = JSON.parse(localStorage.getItem("cart")) || [];
        setCart(storedCart);

        // fetchData()
    }, [])

    // const fetchData = async () => {
    //     try {
    //         const response = await axios.get(`http://localhost:8081/customer-service/customer/findOnlyHistory/${emailByToken}`)
    //         if (response.status === 200){
            
    //         const sortedData = response.data.sort((a, b) => b.id - a.id);
    //          setListData(sortedData)
    //         }
    //     } catch (error) {
    //         console.log(error)
    //     }
    // }


    return (
    <>
        <Navbar />
        <div style={{fontFamily: 'Mitr'}}>
            <div style={{alignItems: 'center',justifyContent: 'center', display: 'flex',  flexDirection: 'column',}}>

                
                <div style={styles.container_order}>
                    <label style={{fontSize: 40, marginBottom: 15, marginTop: 15}}>ประวัติการสั่งซื้อ</label>
                    {/* box 1 order */}
                    
                        <div style={styles.box_order}>
                        <div style={{padding: 15}}>
                            <label style={{fontSize: 20}}><b><BiBasket /></b> </label>
                            <br></br>
                            {/* box 1 รายการ */}
                            {/* {item.map( si => ( */}
                            {cart.map(item => (
                                <div>
                                <div style={{marginTop: 10, flexDirection: 'row', display: 'flex', marginBotom: 10}}>
                                    <img alt="" style={{width: 100, height: 100, border: '0.5px solid #DCDCDC', borderRadius: 10}}
                                    src={item.url}></img>
                                    <label style={{marginLeft: 20, marginTop: 10}}>{item.name} </label>
                                    <label style={{ marginLeft: 'auto', alignSelf: 'flex-end'}}>จำนวน {item.have} ชิ้น</label>
                                </div>
                            </div> 
                            ))}
                             {/* ))}  */}
                            
                            {/* ราคารวมกับวันที่สั่ง */}
                            <div>
                            <div style={{marginTop: 15, flexDirection: 'row', display: 'flex', marginBotom: 10}}>
                                    {/* <label style={{marginTop: 10, fontSize: 20}}>สั่งซื้อวันที่ {item.date} </label> */}
                                    {/* <label style={{ marginLeft: 'auto', alignSelf: 'flex-end', fontSize: 20}}>฿{item[0].totalPrice}.00</label> */}
                                </div>
                            </div>
                           
                        </div>
                    </div>
                    
                    
                </div>
            </div>
        </div>
    </>
        
    )
}

const styles = {
    container_order: {
        marginTop: 50,
        width: 1000,
        height: 600,
        flex: 1,

    },
    box_order: {
        width: 1000,
        borderRadius: 10,
        // border: '1px solid grey',
        marginBottom: 15,
        paddingLeft: 10,
        paddingRight: 10,
        marginTop: 15,
        backgroundColor: '#f5dec4'

    }

}
export default OrderHistory