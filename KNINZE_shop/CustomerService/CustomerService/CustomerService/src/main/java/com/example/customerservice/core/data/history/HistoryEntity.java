package com.example.customerservice.core.data.history;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@Document(collection = "history")
public class HistoryEntity implements Serializable {
    @Id
    private String _id;
    private String userName;
    private ArrayList<ArrayList> orders;
    private String email;
//  Not sure -->   private String pic;

    public HistoryEntity() {}
    public HistoryEntity(String _id, String userName, ArrayList orders, String email) {
        this._id = _id;
        this.userName = userName;
        this.orders = orders;
        this.email = email;
    }

}
