package com.example.receiptservice.data.customer;


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

    public CustomerEntity retrieveCustomerByF_Name(String customerF_Name) {
        return customerRepository.findByf_Name(customerF_Name);
    }

    public CustomerEntity retrieveCustomerByCustomerId(String customerEmail) {
        return customerRepository.findCustomerIdByEmail(customerEmail);
    }










}
