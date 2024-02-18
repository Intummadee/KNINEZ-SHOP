import React, { useState } from "react";
import {Link, useNavigate } from "react-router-dom";
import myStyle from "../components/Login.module.css";
import axios from "axios";
import { jwtDecode } from "jwt-decode";

const Login = (props) => {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [emailError, setEmailError] = useState("");
  const [passwordError, setPasswordError] = useState("");


  const onButtonClick = async () => {
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
      const response = await axios.post('http://localhost:8082/auth-service/login', {
        email: email,
        password: password
      })

      if (response.status === 200) {
        alert('เข้าสู่ระบบเรียบร้อยแล้วค่ะ/ครับ')

        const decodeEmail = jwtDecode(response.data.accessToken);
        localStorage.setItem('accessToken', response.data.accessToken)
        localStorage.setItem('emailToken', decodeEmail.sub)
        navigate("/")
      }
    } catch (error) {
      alert("อีเมลล์หรือรหัสผ่านไม่ถูกต้อง")
    }


  };

  return (
    <div className={myStyle.mainContainer}>
      <div className={myStyle.title}>เข้าสู่ระบบ</div>
      <div className={myStyle.inputContainer}>
        <input
          value={email}
          placeholder="โปรดระบุอีเมลล์"
          onChange={(ev) => setEmail(ev.target.value)}
          className={myStyle.inputBox}
        />
        <label className={myStyle.errorLabel}>{emailError}</label>

        <input
          type="password"
          value={password}
          placeholder="โปรดระบุรหัสผ่าน"
          onChange={(ev) => setPassword(ev.target.value)}
          className={myStyle.inputBox}
        />
        <label className={myStyle.errorLabel}>{passwordError}</label>
      <input
        className={myStyle.inputButton}
        type="button"
        onClick={onButtonClick}
        value="เข้าสู่ระบบ" disabled = {password==="" ? true: false}
        style={{ backgroundColor: (password === "" || email ==="") ? "lightgray" : "" }}
      />
      </div>
      <label>
        ยังไม่มีบัญชี? <Link to="/register">คลิกที่นี่เพื่อเข้าลงทะเบียน</Link>
      </label>
    </div>
  );
};

export default Login;
