package com.example.demo.data;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.io.Serializable;

@Data
@Document(collection = "User")
public class User implements Serializable {

    @Id
    private String _id;
    private String username;
    private String f_name;
    private String l_name;
    private String email;
    private String password;
    private Integer phone;
    private String role;

    @Field(targetType = FieldType.STRING, name = "_class")
    private String className;

    private AddressDetail addressDetail;

    @Data
    public static class AddressDetail implements Serializable {
        private String address;
        private String province;
        private String zipcode;

    }

}