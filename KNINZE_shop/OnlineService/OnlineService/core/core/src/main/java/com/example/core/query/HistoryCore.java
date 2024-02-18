package com.example.core.query;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;


@Data
@Builder
public class HistoryCore {
    private String _id;
    private String userName;
    private ArrayList orders;
    private String orderId;
    private String email;
}
