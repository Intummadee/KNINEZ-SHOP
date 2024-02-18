import React, {useEffect, useState} from "react";
import Navbar from "../navigation/Navbar";
import {BiBell } from "react-icons/bi";
import axios from "axios";

function Notification(){

    const [listData, setListData] = useState([]);
    

    useEffect( () => {
        fetchData()
    }, [])

    const fetchData = async () => {
        try {
            const response = await axios.get('http://localhost:5000/api')
            if (response.status === 200){
            // เรียงจาก promotion id มากไปน้อย เพื่อให้อันบนเปนอันใหม่ ๆ 
            const sortedData = response.data.sort((a, b) => b.id - a.id);
             setListData(sortedData)
            }
        } catch (error) {
            console.log(error)
        }
    }

  

    return (
    <>
        <Navbar />
        <div style={{fontFamily: 'Mitr'}}>
            <div style={{alignItems: 'center',justifyContent: 'center', display: 'flex',  flexDirection: 'column', marginBottom: 20}}>

                <div style={styles.container_noti}>
                    <label style={{fontSize: 40, marginBottom: 25, marginTop: 15}}>การแจ้งเตือน</label>
                    {/* box 1 noti */}
                    {listData.map(item => (
                        <div style={styles.box_noti} key={item._id.$oid}>
                        <div style={{padding: 15}}>
                            <label style={{fontSize: 20, paddingTop: 2}}><b><BiBell /> {item.name}</b> </label>
                            <br></br>
                            <label style={{paddingTop: 4}}>{item.description}</label>
                            <br></br>
                            {/* ถ้าหมดอายุให้ขึ้นตัวแดงๆ ว่าหมดแล้วนะจ้าจากเฟรม */}
                            {new Date(item.exp) < new Date() ? (
                                <>
                                <label style={{paddingTop: 4, marginRight: 5}}>วันสิ้นสุดโปรโมชั่น : {item.exp}</label>
                                <label style={{paddingTop: 4, color: 'red'}}>สิ้นสุดโปรโมชั่นแล้ว</label>
                                </>
                            ) : (
                            <label style={{paddingTop: 4}}>วันสิ้นสุดโปรโมชั่น : {item.exp}</label>
                            )}
                            
                        </div>
                    </div>
                    ))}
                    
                </div>
            </div>
        </div>
    </>
    )
}

const styles = {
    container_noti: {
        marginTop: 50,
        width: 1000,
        height: 600,
        flex: 1,
    },
    box_noti: {
        width: 1000,
        borderRadius: 10,
        // border: '1px solid grey',
        marginBottom: 20,
        backgroundColor: '#C6E4E4',
    }
}
export default Notification