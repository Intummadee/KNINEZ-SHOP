package com.example.onlineservice.command.rest;


import com.example.onlineservice.command.CreateOrderCommand;
import com.example.onlineservice.core.data.ProductEntity;
import com.example.onlineservice.core.data.ProductService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/product")
@RestController
public class ProductController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ProductService productService ;

    private final CommandGateway commandGateway;


//    มี getProduct อาจจะต้องแยกเป็น getProduct หน้าPromotion , getProductAll ,

//  run ApiGateway , discovery -->   http://localhost:8082/online-service/products

    @Autowired
    public ProductController(CommandGateway commandGateway){
        this.commandGateway = commandGateway;
    }

    // ดูข้อมูล product ทั้งหมด
    @GetMapping(value = "/products")
    public ResponseEntity<?> getProducts () {
        List<ProductEntity> products = productService.retrieveProducts();
        return ResponseEntity.ok(products);
    }

    // หา list ที่ตรงแค่กับหมวดหมู่
    @PostMapping("/category/{category}/{subCategory}")
    public ResponseEntity<?> findByCategoryRest(@PathVariable("category") String category ) {
        System.out.println("category : " + category);
        List<ProductEntity> products = productService.retrieveProductsByCategory(category);
        return ResponseEntity.ok(products);
    };

    // หา list ที่ตรงแค่กับหมวดหมู่
    @PostMapping("/subCategory/{category}/{subCategory}")
    public ResponseEntity<?> findBySubCategoryRest(@PathVariable("category") String category,  @PathVariable("subCategory") String subCategory) {
        List<ProductEntity> products = productService.retrieveProductsBySubCategory(category, subCategory);
        return ResponseEntity.ok(products);
    };














//    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
//    public String createOrder(@RequestBody CreateOrderRestModel model){
//        CreateOrderCommand command = CreateOrderCommand.builder()
//                .orderId(UUID.randomUUID().toString())
//                .customerId(model.getCustomerId())
//                .orders(model.getOrders())
//                .orderDate(model.getOrderDate())
//                .totalPrice(model.getTotalPrice())
//                .build();
//
//        String result;
//        try {
//            result = commandGateway.sendAndWait(command);
//            System.out.println("result : " + result);
//        }
//        catch (Exception e){
//            result = e.getLocalizedMessage();
//            System.out.println("error : " + result);
//        }
//        return result;
//    }




    //   http://localhost:8082/online-service/product/helloWorld
    @GetMapping(value = "/helloWorld")
    public String helloWorld() {
        return "Hello World";
    }





}
