package com.example.customerservice.core.data.customer;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Data
@Document(collection = "User")
public class CustomerEntity implements Serializable {

    @Id
    private String _id;
    private String username;
    private String f_name;
    private String l_name;
    private AddressDetail addressDetail;
    private String email;
    private String password;
    private String pic;
    private String phone;
    private String role;

    public CustomerEntity() {}
    public CustomerEntity(String _id, String pic, String username, String f_name, String l_name, AddressDetail addressDetail, String email, String password, String phone, String role) {
        this._id = _id;
        this.pic = pic;
        this.username = username;
        this.f_name = f_name;
        this.l_name = l_name;
        this.addressDetail = addressDetail;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }

}
