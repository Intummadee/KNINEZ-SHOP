//หน้าข้อมูลผู้ใช้ แก้ไขข้อมูลผู้ใช้+ที่อยู๋
import React ,{ useState, useMemo, useEffect } from "react";
import Navbar from "../navigation/Navbar";
import { BiImages} from "react-icons/bi";
import { GoogleMap, Marker, useLoadScript } from "@react-google-maps/api";
import axios from "axios";

function MyProfile(){

    const [id, setID] = useState("");
    const [username, setUsername] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [phone, setPhone] = useState("");
    const [province, setProvince] = useState("");
    const [address, setAddress] = useState("");
    const [zipCode, setZipCode] = useState("");
    const [imageURL, setImageURL] = useState("");

    const [selectedPosition, setSelectedPosition] = useState(null);
    
    const emailByToken = localStorage.getItem('emailToken')

    const onMapClick = (e) => {
        setSelectedPosition({
          lat: e.latLng.lat(),
          lng: e.latLng.lng(),
        });
      };

      useEffect(() => {
        fetchData()
      }, [])

      const fetchData = async () => {
        
        try {
            
            const response = await axios.get(`http://localhost:8081/customer-service/customer/customer/${emailByToken}`)

            setID(response.data._id);
            setUsername(response.data.username)
            setFirstName(response.data.f_name)
            setLastName(response.data.l_name)
            setEmail(response.data.email)
            setPhone(response.data.phone)
            setImageURL(response.data.pic)
            setAddress(response.data.addressDetail.address)
            setProvince(response.data.addressDetail.province)
            setZipCode(response.data.addressDetail.zipcode)

        } catch (error) {
            console.error(error)
        }
      }

    const updateProfile = async () => {

        try {
            
            const response = await axios.post(`http://localhost:8081/customer-service/customer/updateCustomer/${emailByToken}`,{

                _id: id,
                pic: imageURL, 
                username: username,
                f_name: firstName,
                l_name: lastName,
                addressDetail: {
                address: address,
                province: province,
                zipcode: zipCode
                },
                email: email,
                phone: phone
                })

            if (response.status === 200){
                alert("บันทึกข้อมูลเรียบร้อยแล้วค่ะ/ครับ")
                localStorage.setItem("emailToken", email)
            }

            
        } catch (error) {
            console.error(error)
        }
    }

    const updateMap = async () => {

        if (selectedPosition) {
            const lat = selectedPosition.lat;
            const lng = selectedPosition.lng;
            const key3 ="pk.edb81f0c3da10cab4bd7574d25e013b9";
   
            try {

                const response = await axios.get(`https://us1.locationiq.com/v1/reverse.php?key=${key3}&format=json&lat=${lat}&lon=${lng}`);

                setAddress(response.data.display_name);
                setProvince(response.data.address.state);
                setZipCode(response.data.address.postcode);
                
                
            } catch (error) {
                console.log(error)
            }


        } else {
            alert("กรุณาเลือกตำแหน่งที่ต้องการบนแผนที่");
        }

    }


    function isValidURL(url) {
        try {
          new URL(url);
          return true;
        } catch (error) {
          return false;
        }
      }

      const { isLoaded } = useLoadScript({
        googleMapsApiKey: 'AIzaSyCnAdipq7-IGo8w_TftA2hGeGbuzjdsQUc',
      });
      const center = useMemo(() => ({ lat: 13.7563, lng: 100.5018 }), []);

    
    return (
        <>
        <Navbar />
        <div style={{fontFamily: 'Mitr'}}>
        <label style={{fontSize: 30, marginTop: 25, marginLeft: 70}}>หน้าข้อมูลผู้ใช้</label>
        <div style={{alignItems: 'center',justifyContent: 'center', display: 'flex',  flexDirection: 'row', marginTop: 20}}>
            <div style={{
                width: 650, height: 550,  marginTop: 35, 
                display: 'flex', borderRadius: 10, marginRight: 20, padding: 30,
                flexDirection:'column', backgroundColor: '#F5E1ED'}}>
                <label style={{alignSelf: 'center', fontSize: 20, marginBottom: 20}}><b>ข้อมูลส่วนตัว</b></label>
            {/* รูปภาพและไอคอนจ้า */}
                <div style={{alignSelf: 'center',}}>
                    {!imageURL || !isValidURL(imageURL)  ?(
                        // ไม่เคยตั้งค่ารูป
                        <img style={{width: 250, height: 250, borderRadius: 500,border: '7px solid #e2e2e2',}} alt="user_image" src="https://static.vecteezy.com/system/resources/previews/008/442/086/non_2x/illustration-of-human-icon-user-symbol-icon-modern-design-on-blank-background-free-vector.jpg"></img>
                    ) : (
                        // เคยตั้งค่ารูปและมี uri อยู่ใน form
                        <img style={{width: 250, height: 250, borderRadius: 500,border: '7px solid #e2e2e2',}} alt="user_image" src={imageURL}></img>
                    )}
                </div>
               <form style={{alignSelf: 'center'}}>
               <label style={{marginTop: 20}}>
                        URL <BiImages style={{fontSize: 20}} /> : 
                        
                        <input style={{marginLeft: 10, borderRadius: 10, paddingLeft: 10, border: '1px solid grey', width: 469}} type="text" value={imageURL} onChange={(input) => setImageURL(input.target.value)} />
                    </label>
                    <br></br>
                    <label style={{marginTop: 20}}>
                        ชื่อ :
                        <input style={{marginLeft: 10, marginRight: 10, borderRadius: 10, paddingLeft: 10, border: '1px solid grey', width: 200}} type="text" value={firstName} onChange={(input) => setFirstName(input.target.value)} />
                    </label>
                    <label>
                         นามสกุล :
                        <input style={{marginLeft: 10, borderRadius: 10, paddingLeft: 10, border: '1px solid grey', width: 223}} type="text" value={lastName} onChange={(input) => setLastName(input.target.value)} />
                    </label>
                    <br></br>
                    <label style={{marginTop: 20}}>
                         รหัสผู้ใช้ :
                        <input style={{marginLeft: 10, marginRight: 10, borderRadius: 10, paddingLeft: 10, border: '1px solid grey', width: 180}} type="text" value={username} onChange={(input) => setUsername(input.target.value)} />
                    </label>
                    <label >
                        หมายเลขโทรศัพท์ : 
                        <input style={{marginLeft: 10, borderRadius: 10, paddingLeft: 10, border: '1px solid grey', width: 150}} type="text" value={phone} onChange={(input) => setPhone(input.target.value)} />
                    </label>
                    <br></br>
                    <label style={{marginTop: 20}}>
                        อีเมลล์ : 
                        <input style={{marginLeft: 10, borderRadius: 10, paddingLeft: 10, border: '1px solid grey', width: 482,userSelect: 'none' }} type="email" value={email} onChange={(input) => setEmail(input.target.value)} readOnly/>
                    </label>
                </form>
            </div>

            {/* ที่อยู่ */}
            <div style={{
                width: 450, height: 550, marginTop: 35, borderRadius: 10, display: 'flex', flexDirection:'column', marginBottom: 0}}>
                    {/* ข้อมูลที่อยู่ */}
                    <div style={{height: 500, backgroundColor: '#C6E4E4', borderRadius: 10, padding: 20, paddingTop: 25, marginBottom: 15}}>
                    <label style={{alignSelf: 'center', fontSize: 20, marginBottom: 15}}><b>ข้อมูลที่อยู่</b></label>

                {/* google map */}
                    {!isLoaded ? (
                        <h1>Loading...</h1>
                    ) : (
                        <GoogleMap
                        mapContainerStyle={{ height: "55%", width: "100%" }}
                        center={center}
                        zoom={10}
                        onClick={onMapClick}
                      >
                        {selectedPosition && (
                          <Marker
                            position={{
                              lat: selectedPosition.lat,
                              lng: selectedPosition.lng,
                            }}
                          />
                        )}
                      </GoogleMap>
       )} 
                    <button style={{fontSize: 15, borderRadius: 50, border: '1.5px solid grey', marginTop: 15, marginLeft: '74%', marginBottom: 0}} onClick={updateMap}>ตรวจสอบที่อยู่</button>
                    <form style={{alignSelf: 'center', marginTop: 0}}>
                    <label>
                        ที่อยู่ :
                        <br></br>
                        <textarea style={{borderRadius: 10, paddingLeft: 10, border: '1px solid grey', width: 405, marginTop: 5, height: 50}} type="text" value={address} onChange={(input) => setAddress(input.target.value)} />
                    </label>
                    <br></br>
                    <label style={{marginTop: 5}}>
                        จังหวัด :
                        <input style={{marginLeft: 10, marginRight: 10, borderRadius: 10, paddingLeft: 10, border: '1px solid grey', width: 155}} type="text" value={province} onChange={(input) => setProvince(input.target.value)} />
                    </label>
                    <label>
                         รหัสไปรษณีย์ :
                        <input style={{marginLeft: 10, borderRadius: 10, paddingLeft: 10, border: '1px solid grey', width: 68}} type="text" value={zipCode} onChange={(input) => setZipCode(input.target.value)} />
                    </label>
                    </form>
                    </div>
                {/* ปุ่มกดแก้ไข */}
                <button style={{alignSelf: 'flex-end', fontSize: 18, paddingTop: 5, paddingBottom: 5,paddingLeft: 10, paddingRight: 10,
                 borderRadius: 50, backgroundColor: '#F2C898', marginBottom: 0}} onClick={updateProfile}>บันทึกข้อมูล</button>
            </div>
        </div>
        </div>
        </>
    )
}


export default MyProfile