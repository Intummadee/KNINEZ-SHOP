package com.example.inventory.core;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Admin")

public class AdminEntity {
    @Id
    private String _id;
    private String f_name;
    private String s_name;
    private String username;
    private String email;
    private String password;


    public AdminEntity(String _id, String f_name, String s_name, String username, String email, String password) {
        this._id = _id;
        this.f_name = f_name;
        this.s_name = s_name;
        this.username = username;
        this.email = email;
        this.password = password;


    }
}

