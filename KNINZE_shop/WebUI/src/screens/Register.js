import React, { useState} from "react";
import {Link, useNavigate } from "react-router-dom";
import myStyle from "../components/Register.module.css";
import axios from "axios";


const Register = (props) => {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [username, setUsername] = useState("");
  const [usernameError, setUsernameError] = useState("");
  const [emailError, setEmailError] = useState("");
  const [passwordError, setPasswordError] = useState("");
  const [phone, setPhone] = useState("");
  const [fname, setFname] = useState("");
  const [lname, setLname] = useState("");
  const [address, setAddress] = useState("");
  const [province, setProvince] = useState("");
  const [zipc, setZipc] = useState("");


  const onButtonClick = async () => {
    setUsernameError("");
    setEmailError("");
    setPasswordError("");


    if (!/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/.test(email)) {
      setEmailError("Please enter a valid email");
      return;
    }
    if (password.length < 3) {
      setPasswordError("Password is too short");
      return;
    }

    try {
      const response = await axios.post('http://localhost:8082/auth-service/register', {
        username: username,
        email: email,
        password: password,
        phone: phone,
        f_name: fname,
        l_name: lname, 
        addressDetail: {
          address: address,
          province: province,
          zipcode: zipc
      }})

      if (response.data === "Email is already registered") {
          return alert("อีเมลล์ซ้ำ กรุณาเปลี่ยนอีเมลล์")
      }
      if (response.status === 200) {
        alert('ลงทะเบียนเรียบร้อยแล้วค่ะ/ครับ')
        navigate("/login")
      }
    } catch (error) {
      console.error(error)
    }
  };

  return (
    <div className={myStyle.mainContainer}>
      <div className={myStyle.title}>ลงทะเบียน</div>

      <div className={myStyle.inputContainer}>
        
      <label style={{ padding: "5px 0px 5px 10px" }}>ชื่อผู้ใช้ : <label className={myStyle.errorLabel}> *ต้องกรอก*</label></label>
        <input
        type="text"
          value={username}
          onChange={(ev) => setUsername(ev.target.value)}
          className={myStyle.inputBox}
        />
        <label className={myStyle.errorLabel}>{usernameError}</label>

        <div style={{display: "flex",flexDirection: "row",justifyContent: "space-between", }}>
          <div style={{display: "flex",flexDirection: "column"}}>
          <label style={{ padding: "5px 0px 5px 10px" }}>ชื่อจริง : </label>
          <input
            value={fname}
            onChange={(ev) => setFname(ev.target.value)}
            className={myStyle.inputBox}
          />
          <label className={myStyle.errorLabel}></label>
          </div>
          <div style={{display: "flex",flexDirection: "column"}}>
          <label style={{ padding: "5px 0px 5px 10px" }}>นามสกุล : </label>
          <input
            value={lname}
            onChange={(ev) => setLname(ev.target.value)}
            className={myStyle.inputBox}
          />
          <label className={myStyle.errorLabel}></label>
          </div>
        </div>

        <label style={{ padding: "5px 0px 5px 10px" }}>อีเมล : <label className={myStyle.errorLabel}> *ต้องกรอก*</label></label>
        <input
        type="text"
          value={email}
          onChange={(ev) => setEmail(ev.target.value)}
          className={myStyle.inputBox}
        />
        <label className={myStyle.errorLabel}>{emailError}</label>

        <label style={{ padding: "5px 0px 5px 10px" }}>รหัสผ่าน :<label className={myStyle.errorLabel}> *ต้องกรอก*</label> </label>
        <input
          type="password"
          value={password}
          onChange={(ev) => setPassword(ev.target.value)}
          className={myStyle.inputBox}
        />
        <label className={myStyle.errorLabel}>{passwordError}</label>


        <div style={{display: "flex",flexDirection: "row",justifyContent: "space-between", }}>
        <div style={{display: "flex",flexDirection: "column"}}>

        <label style={{ padding: "5px 0px 5px 10px" }}>ที่อยู่ : </label>
        <textarea
          value={address}
          onChange={(ev) => setAddress(ev.target.value)}
          className={myStyle.inputBox}
          style={{height: "70%"}}
        />
        </div>

      <div style={{display: "flex",flexDirection: "column"}}>
        <label style={{ padding: "5px 0px 5px 10px" }}>จังหวัด : </label>
        <input
          value={province}
          onChange={(ev) => setProvince(ev.target.value)}
          className={myStyle.inputBox}
        />
        <label className={myStyle.errorLabel}></label>

        <label style={{ padding: "5px 0px 5px 10px" }}>รหัสไปรษณีย์ : </label>
        <input
          value={zipc}
          onChange={(ev) => setZipc(ev.target.value)}
          className={myStyle.inputBox}
          type="number"
        />
        <label className={myStyle.errorLabel}></label>
        </div>
        
      </div>

        <label style={{ padding: "5px 0px 5px 10px" }}>เบอร์โทรศัพท์ : </label>
        <input
          value={phone}
          onChange={(ev) => setPhone(ev.target.value)}
          className={myStyle.inputBox}
          type="number"
        />
        <label className={myStyle.errorLabel}></label>
      </div>
      

      <input
        className={myStyle.inputButton}
        type="button"
        onClick={onButtonClick}
        value="ลงทะเบียน"
        disabled={password === "" ? true : false}
        style={{
          backgroundColor:
            password === "" || email === "" || username === ""
              ? "lightgray"
              : "",
          width: "20%",
        }}
      />

      <label style={{marginTop: 5}}>
        มีบัญชีอยู่แล้ว? <Link to="/login">คลิกที่นี่เพื่อเข้าสู่ระบบ</Link>
      </label>
    </div>
  );
};

export default Register;