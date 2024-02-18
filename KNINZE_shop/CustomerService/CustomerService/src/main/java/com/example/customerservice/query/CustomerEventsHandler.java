package com.example.customerservice.query;


import com.example.core.query.AddHistoryCustomerQuery;
import com.example.core.query.HistoryCore;
import com.example.customerservice.core.data.customer.CustomerEntity;
import com.example.customerservice.core.data.customer.CustomerService;
import com.example.customerservice.core.data.history.HistoryEntity;
import com.example.customerservice.core.data.history.HistoryService;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomerEventsHandler {

    private final CustomerService customerService;
    private final HistoryService historyService;

    public CustomerEventsHandler(HistoryService historyService, CustomerService customerService){
        this.historyService = historyService;
        this.customerService = customerService;
    }

    @QueryHandler
    public HistoryCore updateHistory(AddHistoryCustomerQuery query){
        System.out.println("AddHistoryCustomerQuery ใน Customer Service");

        System.out.println("query.getEmail() "+query.getEmail());

        CustomerEntity customerEntity = customerService.retrieveCustomerByCustomerId(query.getEmail());
        HistoryEntity historyByName = historyService.retrieveHistoryEntityByEmail(query.getEmail());

        System.out.println(historyByName.get_id() +"  "+ historyByName.getUserName());


//        orderที่customerสั่งมาใน ProductCommand
        ArrayList newOrders = new ArrayList();

        for(Object item : historyByName.getOrders()){
//            System.out.println("array : " + item);
            newOrders.add(item);
        }


        if(historyByName != null) {
            newOrders.add(query.getOrders());
            historyService.updateHistory(new HistoryEntity(historyByName.get_id(), query.getUserName(), newOrders, query.getEmail()));
        }

        HistoryEntity historyByName1 = historyService.retrieveHistoryEntityByEmail(query.getEmail());
        HistoryCore transmitToSaga = HistoryCore.builder()
                ._id(historyByName1.get_id())
                .userName(historyByName1.getUserName())
                .orders(historyByName1.getOrders())
                .orderId(query.getOrderId())
                .email(query.getEmail())
                .build();
        return transmitToSaga;
    }





}
