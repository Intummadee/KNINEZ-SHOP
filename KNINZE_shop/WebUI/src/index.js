import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
// import App from './App';
// import reportWebVitals from './reportWebVitals';

import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom";


// หน้าต่าง ๆ
import Home from "./screens/Home.js";
import Login from "./screens/Login.js";
import MyProfile from "./screens/MyProfile.js";
import Notification from "./screens/Notification.js";
import OrderBasket from "./screens/OrderBasket.js";
import OrderHistory from "./screens/OrderHistory.js";
import Register from "./screens/Register.js";
import Bakery from './screens/productScreen/Bakery.js';
import Cosmetics from './screens/productScreen/Cosmetics.js';
import Drinks from './screens/productScreen/Drinks.js';
import Foods from './screens/productScreen/Foods.js';
import Medicine from './screens/productScreen/Medicine.js';


const myRouter = createBrowserRouter([
    {
        path: '/',
        element:<Home />
    },
    {
        path: 'login',
        element:<Login />
    },
    {
        path: 'register',
        element:<Register />
    },
    {
        path: 'profile',
        element:<MyProfile />
    },
    {
        path: 'basket',
        element:<OrderBasket />
    },
    {
        path: 'history',
        element:<OrderHistory />
    },
    {
        path: 'noti',
        element:<Notification />
    },
    {
        path: 'bakery',
        element:<Bakery />
    },
    {
        path: 'cosmetics',
        element:<Cosmetics />
    },
    {
        path: 'drinks',
        element:<Drinks />
    },
    {
        path: 'foods',
        element:<Foods />
    },
    {
        path: 'medicine',
        element:<Medicine />
    },
   
    
])

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <RouterProvider  router={myRouter} />
    </React.StrictMode>
);


// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
// reportWebVitals();
