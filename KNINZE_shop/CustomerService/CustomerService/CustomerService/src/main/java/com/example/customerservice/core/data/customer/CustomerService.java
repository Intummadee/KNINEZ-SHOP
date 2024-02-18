package com.example.customerservice.core.data.customer;


import com.example.customerservice.core.data.customer.CustomerEntity;
import com.example.customerservice.core.data.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;


    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public List<CustomerEntity> retrieveAllCustomer() {
        return customerRepository.findAll();
//        repository.findAll() มันจะส่งคืนข้อมูลในรูปแบบของ List<Wizard> เลยใช้ ArrayList ไม่ได้
    }

    public CustomerEntity retrieveCustomerByCustomerId(String email) {
        return customerRepository.findByEmail(email);
    }


    public CustomerEntity updateCustomerEntity(CustomerEntity customerEntity){
        return customerRepository.save(customerEntity);
    }


}
