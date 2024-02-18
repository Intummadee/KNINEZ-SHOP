// OrderConfirmationModal.js
import React from "react";
import Modal from "@mui/material/Modal";
import { Button } from "@mui/material";
import { useNavigate } from "react-router-dom";

const OrderConfirmationModal = ({ isOpen, onClose, onConfirm }) => {
    
  const navigate = useNavigate();

  return (
    <Modal open={isOpen} onClose={onClose}>
      <div
        style={{
          position: "absolute",
          top: "20%",
          left: "30%",
          width: "40vw",
          height: "60vh",
          padding: "30px",
          backgroundColor: "white",
          boxShadow: 24,
          display: "flex",
          justifyContent: 'space-evenly',
          alignContent: 'center',
          p: 4,
          flexDirection: 'column',
        }}
      >
        <div style={{
          display: "flex",
          justifyContent: 'center',
          flexDirection: 'column', }}>
        <h2 style={{textAlign: 'center', fontSize: 40}}>ยืนยันคำสั่งซื้อ</h2>
        <p style={{textAlign: 'center', fontSize: 20}}>ขอบพระคุณที่ใช้บริการ KNINEZ SHOP</p>
        </div>

        <div style={{
          display: "flex",
          justifyContent: 'center',
          alignContent: 'center',
          flexDirection: 'row',
        }}>
            <Button variant="contained" onClick={onConfirm} style={{ marginRight: 10, fontSize: 20 }}>
            ปริ้นท์ใบเสร็จ
          </Button>
          <Button variant="outlined" style={{ marginLeft: 10, fontSize: 20 }} onClick={() => {
            navigate("/");
            // localStorage.clear();
          }}>กลับสู่หน้าหลัก</Button>
        </div>

      </div>
    </Modal>
  );
};

export default OrderConfirmationModal;