package com.example.core.query;


import com.example.core.commands.Product_Core;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@Builder
public class FetchUserPaymentDetailsQuery {
//    หา Detail ของ Customer ออกมา โดยให้ใน CustomerService จะเป็นคนฟัง Handler Query อันนี้ แล้วพอฟังเสร็จ ก็จะได้ข้อมูลของ Customer ออกมาใน 25 ของ Lab11
    private String userName;
    private ArrayList<Product_Core> orders;
    private String orderId;

}
