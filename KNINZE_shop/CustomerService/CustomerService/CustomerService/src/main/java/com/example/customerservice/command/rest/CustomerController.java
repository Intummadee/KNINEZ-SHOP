package com.example.customerservice.command.rest;


import com.example.customerservice.core.data.customer.CustomerEntity;
import com.example.customerservice.core.data.customer.CustomerService;
import com.example.customerservice.core.data.history.HistoryEntity;
import com.example.customerservice.core.data.history.HistoryService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;


@RequestMapping("/customer")
@RestController
public class CustomerController {
    @Autowired
    private HistoryService historyService;

    @Autowired
    private CustomerService customerService;




//    http://localhost:8082/customer-service/customer/customer
//    หา customer ทั้งหมดด
    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    public ResponseEntity<?> getCustomer() {
        List<CustomerEntity> customer = customerService.retrieveAllCustomer();
        return ResponseEntity.ok(customer);
    }

//    หา history ทั้งหมด  http://localhost:8082/customer-service/customer/history
    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public ResponseEntity<?> getHistory() {
        List<HistoryEntity> history = historyService.retrieveAllHistory();
        return ResponseEntity.ok(history);
    }

    // หาข้อมูล user ผ่าน email
    @RequestMapping(value = "/customer/{email}", method = RequestMethod.GET)
    public ResponseEntity<?> getCustomer(@PathVariable("email") String email) {
        CustomerEntity customer = customerService.retrieveCustomerByCustomerId(email);
        return ResponseEntity.ok(customer);
    }



//    เอาออกมาทั้งประวัติ customer ทั้ง History ของ customer
    @GetMapping("/findcustomerByF_name/{email}") // http://localhost:8082/customer-service/customer/findcustomerByF_name/64070245@kmitl.ac.th
    public ResponseEntity<?> findcustomerByF_name(@PathVariable("email") String email) {
        CustomerEntity customerEntities = customerService.retrieveCustomerByCustomerId(email);
        HistoryEntity history = historyService.retrieveHistoryEntityByEmail(email);
        ArrayList list1 = new ArrayList();
        list1.add(customerEntities);
        list1.add(history);
        return ResponseEntity.ok(list1);
    };


    // หา history จาก email จะได้เป็น array history ของลูกค้าออกมา
    @GetMapping("/findOnlyHistory/{email}")
    public List findOnlyHistory(@PathVariable("email") String email) {
        HistoryEntity history = historyService.retrieveHistoryEntityByEmail(email);
        return history.getOrders();
    };
        //  http://localhost:8082/customer-service/customer/findHistory/64070245@kmitl.ac.th



    // Update Data Customer
    @PostMapping("/updateCustomer/{email}")
    public ResponseEntity<?> updateProfile(@RequestBody CustomerEntity model) {
        CustomerEntity customerEntities = customerService.retrieveCustomerByCustomerId(model.getEmail());
        customerService.updateCustomerEntity(new CustomerEntity(customerEntities.get_id(), model.getPic(), model.getUsername(), model.getF_name(), model.getL_name(), model.getAddressDetail(), model.getEmail() , customerEntities.getPassword(), model.getPhone(), "user"));
        return ResponseEntity.ok(customerEntities);

    }



    // เอาไว้ test   http://localhost:8082/customer-service/customer/helloWorld
    @GetMapping(value = "/helloWorld")
    public String helloWorld() {
        return "Hello World";
    }

}


