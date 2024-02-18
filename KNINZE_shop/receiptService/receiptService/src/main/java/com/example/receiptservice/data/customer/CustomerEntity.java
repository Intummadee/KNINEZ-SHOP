package com.example.receiptservice.data.customer;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "customer")
public class CustomerEntity {

    @Id
    private String _id;
    private String userName;
    private String f_name;
    private String l_name;
    private AddressDetail addressDetail;
    private String email;
    private String password;
    private String pic;
    private String phone;

    public CustomerEntity() {}
    public CustomerEntity(String _id, String pic, String userName, String f_name, String l_name, AddressDetail addressDetail, String email, String password, String phone) {
        this._id = _id;
        this.pic = pic;
        this.phone = phone;
        this.userName = userName;
        this.f_name = f_name;
        this.l_name = l_name;
        this.addressDetail = addressDetail;
        this.email = email;
        this.password = password;
    }


}
