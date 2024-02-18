package com.example.customerservice.core.data.customer;


import com.example.customerservice.core.data.customer.CustomerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository // บอกว่าคลาสนี้เป็น Repository ที่ใช้ในการเชื่อมต่อและจัดการข้อมูลในฐานข้อมูล MongoDB.
public interface CustomerRepository extends MongoRepository<CustomerEntity, String> {

    @Query(value="{email:'?0'}")
    public CustomerEntity findByEmail(String name);





}
