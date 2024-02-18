package com.example.manage_inventory_service.command.rest;



import com.example.manage_inventory_service.command.CreateOrderCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;



@RestController
public class OrderController {

    private final CommandGateway commandGateway;

    @Autowired
    public OrderController(CommandGateway commandGateway){
        this.commandGateway = commandGateway;
    }



//    http://localhost:8082/manage-inventory-service/createOrder
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public String createOrder(@RequestBody CreateOrderRestModel model){
        CreateOrderCommand command = CreateOrderCommand.builder()
                .orderId(UUID.randomUUID().toString())
                .userName(model.getUserName())
                .orders(model.getOrders())
                .orderDate(model.getOrderDate())
                .totalPrice(model.getTotalPrice())
                .email(model.getEmail())
                .build();

//        System.out.println(command.getOrders().get(0)); --> Product(category=เครื่องดื่ม, name=แป๊ปซี่, subcategory=นํ้าอัดลม, cost=15.0, amount=1)

        String result;
        try {
            result = commandGateway.sendAndWait(command);
            System.out.println("result : " + result);
        }
        catch (Exception e){
            result = e.getLocalizedMessage();
            System.out.println("error : " + result);
        }
        return result;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String helloWorld() {
        return "Hello World Test";
    }
//    http://localhost:8082/manage-inventory-service/test

}
