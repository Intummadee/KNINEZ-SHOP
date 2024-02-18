import React, { useEffect, useState, useRef } from "react";
import Navbar from "../../navigation/Navbar";
import NavbarHome from "../../navigation/NavbarHome";
import {
  Box,
  Button,
  Card,
  CardActions,
  CardContent,
  CardMedia,
  FormControl,
  InputLabel,
  MenuItem,
  Typography,
  Select,
  Divider,
} from "@mui/material";
import myStyle from "../../components/Product.module.css";
import axios from "axios";

function Drinks() {
  const [data, setData] = useState([]);
  const [rawD, setRawD] = useState([]);
  const [cate, setCate] = useState([]);
  const [filteredData, setFilteredData] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState('');
  const categoryRefs = useRef([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8083/online-service/product/products"
        );
        setRawD(response.data);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchData();
  }, []);

  useEffect(() => {
    setData(rawD.filter((sub) => sub.category === "เครื่องดื่ม"));
  }, [rawD]);

  useEffect(() => {
    setCate([...new Set(data.map((item) => item.subcategory))]);
  }, [data]);

  useEffect(() => {
    if (cate.length > 0) {
      setFilteredData(
        cate.map((subcategory) =>
          data.filter((sub) => sub.subcategory === subcategory)
        )
      );
    }
  }, [cate, data]);

  const handleChange = (event) => {
    const selectedCategory = event.target.value;
    setSelectedCategory(selectedCategory);

    // Find the index of the selected category
    const selectedIndex = cate.indexOf(selectedCategory);

    // Scroll to the corresponding label using ref
    if (selectedIndex !== -1 && categoryRefs.current[selectedIndex]) {
      categoryRefs.current[selectedIndex].scrollIntoView({
        behavior: "smooth",
      });
    }
  };

  //send after add basket
  const handleAddToCart = (productName, cost, amount, url) => {
    // Retrieve existing cart data from local storage
    const existingCartData = JSON.parse(localStorage.getItem('cart')) || [];

    // Create new item for the cart
    const newItem = {
      name: productName,
      cost: cost,
      amount: amount,
      have: 1,
      url: url  // You may need to adjust this based on your logic
      // Add more properties as needed
    };
    // Add the new item to the cart
    existingCartData.push(newItem);

    // Save the updated cart data back to local storage
    localStorage.setItem('cart', JSON.stringify(existingCartData));

    // Optionally, you can show a confirmation or perform other actions
    alert(`${productName} added to cart!`); };
  

  return (
    <div>
    
    
    
    <div className={myStyle.mainContainer}>
      <div className={myStyle.topNav}>
        
        <div className={myStyle.findProduct}>
            <Navbar />
    <NavbarHome />
          <Box style={{ maxWidth: "65%", paddingLeft: "35%" }}>
            <FormControl fullWidth>
              <InputLabel id="demo-simple-select-label">
                Drinks Category
              </InputLabel>
              <Select
                labelId="demo-simple-select-label"
                id="demo-simple-select"
                value={selectedCategory}
                label="Drinks Category"
                onChange={handleChange}
              >
                {data.length > 0 &&
                  cate.map((subcategory, index) => (
                    <MenuItem key={index} href={`#${index}`} value={subcategory}>
  {subcategory}
</MenuItem>

                  ))}
              </Select>
            </FormControl>
          </Box>
        </div>
      </div>

      <div className={myStyle.allProducts}>
        {filteredData.map((subcategoryData, i) => (
          <React.Fragment key={i}>
            <label ref={(ref) => (categoryRefs.current[i] = ref)} id={i} className={myStyle.catLabel}>
              {cate[i]}
            </label>
            {subcategoryData.map((product, index) =>
              index % 2 === 0 && subcategoryData[index + 1] ? (
                <div key={index} className={myStyle.eachRow}>
                  <div className={myStyle.eachProduct}>
                    <Card sx={{ maxWidth: 345 }}>
                      <CardMedia
                        sx={{ height: 150, backgroundColor: "var(--orange)" }}
                        image={product.url}
                        title=""
                      />
                      <CardContent>
                        <Typography
                          gutterBottom
                          variant="h5"
                          component="div"
                        >
                          {product.name}
                        </Typography>
                        <Typography
                          variant="body2"
                          color="text.secondary"
                        >
                          ฿{product.cost}
                        </Typography>
                      </CardContent>
                      <CardActions
                        style={{ justifyContent: "center" }}
                      >
                        <Button
                          variant="contained"
                          style={{
                            color: "white",
                            background: "var(--orange)",
                            zIndex: "1",
                            fontSize: 16
                          }}
                          size="small"
                          
                          onClick={() => handleAddToCart(product.name, product.cost, product.amount, product.url)}
                        >
                          เพิ่มลงตะกร้า
                        </Button>
                      </CardActions>
                    </Card>
                  </div>

                  <div className={myStyle.eachProduct}>
                    <Card sx={{ maxWidth: 345 }}>
                      <CardMedia
                        sx={{ height: 150, backgroundColor: "var(--orange)" }}
                        image={subcategoryData[index + 1].url}
                        title=""
                      />
                      <CardContent>
                        <Typography
                          gutterBottom
                          variant="h5"
                          component="div"
                        >
                          {subcategoryData[index + 1].name}
                        </Typography>
                        <Typography
                          variant="body2"
                          color="text.secondary"
                        >
                          ฿{subcategoryData[index + 1].cost}
                        </Typography>
                      </CardContent>
                      <CardActions
                        style={{ justifyContent: "center" }}
                      >
                        <Button
                          variant="contained"
                          style={{
                            color: "white",
                            background: "var(--orange)",
                            zIndex: "1",
                            fontSize: 16
                          }}
                          size="small"
                          onClick={() => handleAddToCart(subcategoryData[index + 1].name, subcategoryData[index + 1].cost, subcategoryData[index + 1].amount, subcategoryData[index + 1].url)}
                        >
                          เพิ่มลงตะกร้า
                        </Button>
                      </CardActions>
                    </Card>
                  </div>
                </div>
              ) : index === subcategoryData.length - 1 &&
                subcategoryData.length % 2 !== 0 ? (
                <div key={index} className={myStyle.eachRow}>
                  <div className={myStyle.eachProduct}>
                    <Card sx={{ maxWidth: 345 }}>
                      <CardMedia
                        sx={{ height: 150, backgroundColor: "var(--orange)" }}
                        image={product.url}
                        title=""
                      />
                      <CardContent>
                        <Typography
                          gutterBottom
                          variant="h5"
                          component="div"
                        >
                          {product.name}
                        </Typography>
                        <Typography
                          variant="body2"
                          color="text.secondary"
                        >
                          ฿{product.cost}
                        </Typography>
                      </CardContent>
                      <CardActions
                        style={{ justifyContent: "center" }}
                      >
                        <Button
                          variant="contained"
                          style={{
                            color: "white",
                            background: "var(--orange)",
                            zIndex: "1",
                            fontSize: 16
                          }}
                          size="small"
                          onClick={() => handleAddToCart(product.name, product.cost, product.amount, product.url)}
                        >
                          เพิ่มลงตะกร้า
                        </Button>
                      </CardActions>
                    </Card>
                  </div>
                </div>
              ) : null
            )}
            <Divider></Divider>
          </React.Fragment>
        ))}
      </div>
    </div>
    </div>
  );
};

export default Drinks;
