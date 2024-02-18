import React, { useEffect, useState } from "react";
import Navbar from "../navigation/Navbar";
import myStyle from "../components/Basket.module.css";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Button from "@mui/material/Button";
import ButtonGroup from "@mui/material/ButtonGroup";
import { BiSolidEdit } from "react-icons/bi";
import { Link, useNavigate } from "react-router-dom";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import axios from "axios";
import OrderConfirmationModal from "./OrderConfirmationModal";

function OrderBasket() {
  const [promolist, setPromoList] = useState([]);
  const [cart, setCart] = useState([]);
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [province, setProvince] = useState("");
  const [address, setAddress] = useState("");
  const [zipCode, setZipCode] = useState("");
  const emailByToken = localStorage.getItem('emailToken')
  
  const navigate = useNavigate();

  const [isModalOpen, setIsModalOpen] = useState(false);
  const openModal = () => {
    setIsModalOpen(true);
  };
  
  const closeModal = () => {
    setIsModalOpen(false);
  };
  
  const handleConfirmOrder = () => {
    // Add logic to handle the order confirmation
    // For now, just close the modal
    closeModal();
  };

  useEffect(() => {
    const fetchPromo = async () => {
      try {
        const response = await axios.get("http://localhost:5000/api");
        setPromoList(response.data);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    // Fetch cart data from local storage
    const storedCart = JSON.parse(localStorage.getItem("cart")) || [];
    setCart(storedCart);

    fetchPromo();
    fetchUser();
  }, []);

  const fetchUser = async () => {

    try {
            
        const response = await axios.get(`http://localhost:8081/customer-service/customer/customer/${emailByToken}`)

        
        setFirstName(response.data.f_name)
        setLastName(response.data.l_name)
        setAddress(response.data.addressDetail.address)
        setProvince(response.data.addressDetail.province)
        setZipCode(response.data.addressDetail.zipcode)

    } catch (error) {
        console.error(error)
    }

  }

  const amountMore = (cartItem, index) => {
    // Check if the increase is within the cartItem's quantity limit
    if (cartItem.have < cartItem.amount) {
      const updatedCart = cart.map((item) =>
        item.name === cartItem.name ? { ...item, have: item.have + 1 } : item
      );

      setCart(updatedCart);
    } else {
      alert(`คุณได้เลือกสินค้าชนิดนี้ถึงจำนวนสูงสุดแล้ว`);
    }
  };

  const amountLess = (cartItem, index) => {
    // Check if the decrease is within the limit (e.g., not negative)
    if (cartItem.have > 0) {
      const updatedCart = cart.map((item) =>
        item.name === cartItem.name ? { ...item, have: item.have - 1 } : item
      );

      setCart(updatedCart);
    }
  };

  const [promo, setPromo] = useState(0);

  const promoChange = (event) => {
    setPromo((parseFloat(event.target.value) * getTotalCost()) / 100);
  };

  const getTotalCost = () => {
    // Calculate the total cost based on each item in the cart
    return cart.reduce((total, item) => {
      const itemPrice = parseFloat(item.cost) || 0;
      const itemAmount = parseInt(item.have, 10) || 0;

      // Add only if both price and amount are valid numbers
      if (!isNaN(itemPrice) && !isNaN(itemAmount)) {
        return total + itemPrice * itemAmount;
      }

      return total;
    }, 0);
  };

  const getFinalPrice = () => {
    return getTotalCost() - promo;
  };

  return (
    <>
      <Navbar className={myStyle.topNav} />

      <div className={myStyle.mainContainer}>
        <Card className={myStyle.scrollable}>
          <CardContent>
            <div className={myStyle.iOrdered}>
              <label
                style={{ fontSize: 32, paddingBottom: "40px" }}
                gutterBottom
              >
                สรุปรายการสั่งซื้อ
              </label>
              {cart.map((cartItem, index) => (
                <div key={index} className={myStyle.box_order}>
                  <div
                    style={{
                      display: "flex",
                      flexDirection: "row",
                      justifyContent: "flex-start",
                    }}
                  >
                    <img
                      alt=""
                      src={cartItem.url}
                    />
                    <div
                      style={{
                        display: "flex",
                        flexDirection: "column",
                        justifyContent: "flex-start",
                        paddingLeft: "10px",
                      }}
                    >
                      <label
                        style={{
                          fontSize: 20,
                          paddingTop: "40px",
                          paddingBottom: "10px",
                        }}
                      >
                        {cartItem.name}
                      </label>
                      <label>฿{cartItem.cost}</label>
                    </div>
                  </div>

                  <ButtonGroup
                    style={{
                      height: "40px",
                      backgroundColor: "white",
                      color: "black",
                      marginRight: "10px",
                      marginTop: "100px",
                    }}
                    value={cartItem.have}
                    exclusive
                  >
                    <Button
                      style={{ fontSize: 20 }}
                      onClick={() => amountLess(cartItem, index)}
                    >
                      -
                    </Button>
                    <Button style={{ backgroundColor: "white", fontSize: 20 }}>
                      {cartItem.have}
                    </Button>
                    <Button
                      style={{ fontSize: 20 }}
                      onClick={() => amountMore(cartItem, index)}
                    >
                      +
                    </Button>
                  </ButtonGroup>
                </div>
              ))}
            </div>
          </CardContent>
        </Card>

        <div className={myStyle.stickyCard}>
          <Card className={myStyle.verifyOrder}>
            <CardContent>
              <label style={{ fontSize: 32 }} gutterBottom>
                ยอดการสั่งซื้อ
              </label>
              <div
                style={{
                  display: "flex",
                  flexDirection: "row",
                  justifyContent: "space-between",
                }}
              >
                <label style={{ fontSize: 20 }} gutterBottom>
                  ราคาสินค้า
                </label>
                <span>฿ {getTotalCost()}</span>
              </div>

              <div
                style={{
                  display: "flex",
                  flexDirection: "row",
                  justifyContent: "space-between",
                }}
              >
                <label style={{ fontSize: 20 }} gutterBottom>
                  ส่วนลด
                </label>
                <span>฿ {promo}</span>
              </div>
              <div
                style={{
                  display: "flex",
                  flexDirection: "row",
                  justifyContent: "space-between",
                }}
              >
                <label style={{ fontSize: 20 }} gutterBottom>
                  ยอดรวม
                </label>
                <span>฿ {getFinalPrice()}</span>
              </div>

              <br />
              <label style={{ fontSize: 20 }} gutterBottom>
                โปรโมชั่น
              </label>
              <FormControl sx={{ m: 1, width: "100%" }} size="small">
                <InputLabel id="demo-select-small-label">โปรโมชั่น</InputLabel>
                <Select
                  labelId="demo-select-small-label"
                  id="demo-select-small"
                  value={promo}
                  label="Promotion"
                  onChange={promoChange}
                >
                  <MenuItem value="0">
                    <em>None</em>
                  </MenuItem>
                  {promolist.map((pro, index) => (
                    <MenuItem key={index} value={pro.discount}>
                      <em>{pro.name}</em>
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </CardContent>
          </Card>

          <Card className={myStyle.verifyOrder}>
            <div>
              <CardContent>
                <div className={myStyle.addressForm}>
                  <div
                    style={{
                      display: "flex",
                      flexDirection: "row",
                      justifyContent: "flex-start",
                    }}
                  >
                    <label style={{ fontSize: 32 }} gutterBottom>
                      ที่อยู่จัดส่ง
                    </label>
                    <Link
                      to="/profile"
                      style={{ color: "black", margin: "5px 0px 0px 5px" }}
                    >
                      <BiSolidEdit />
                    </Link>
                  </div>

                  <label style={{ fontSize: 20 }} gutterBottom>
                     {firstName} {lastName} 
                  </label>
                  <br />
                  <label style={{ fontSize: 20 }} gutterBottom>
                     {address} 
                  </label>
                  <br />
                  <label style={{ fontSize: 20 }} gutterBottom>
                     {province}  
                  </label>
                  <br />
                  <label style={{ fontSize: 20 }} gutterBottom>
                     {zipCode} 
                  </label>
                </div>
              </CardContent>
            </div>
          </Card>

          <div
            style={{
              display: "flex",
              flexDirection: "row",
              justifyContent: "space-between",
            }}
          >
            <Button
              variant="contained"
              style={{
                background: "var(--green)",
                color: "white",
                padding: "10px",
                margin: "8px",
                fontSize: "20px",
                borderRadius: "8px",
              }}
              onClick={() => {
                
                navigate("/");
                localStorage.removeItem("cart");
              }}
            >
              ยกเลิกคำสั่งซื้อ
            </Button>
            <Button
    variant="contained"
    style={{
      background: "var(--green)",
      color: "white",
      padding: "10px",
      margin: "8px",
      fontSize: "20px",
      borderRadius: "8px",
    }}
    onClick={() => openModal()}
  >
    ยืนยันคำสั่งซื้อ
  </Button>

  <OrderConfirmationModal
    isOpen={isModalOpen}
    onClose={closeModal}
    onConfirm={handleConfirmOrder}
    style={{
      content: {
        backgroundColor: 'white', // Change the background color of the modal content
        // Add other styles as needed
      },
    }}
  />

          </div>
        </div>
      </div>
    </>
  );
}

export default OrderBasket;