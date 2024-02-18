package com.example.receiptservice.data.customer;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<CustomerEntity, String> {

    @Query(value="{f_name:'?0'}")
    public CustomerEntity findByf_Name(String name);

    @Query(value="{_id:'?0'}")
    public CustomerEntity findByCustomerId(String name);


    @Query(value="{email:'?0'}")
    public CustomerEntity findCustomerIdByEmail(String email);

}
