package com.example.onlineservice.core.data;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.util.List;


@Repository // บอกว่าคลาสนี้เป็น Repository ที่ใช้ในการเชื่อมต่อและจัดการข้อมูลในฐานข้อมูล MongoDB.
public interface ProductRepository extends MongoRepository<ProductEntity, String> {

    @Query(value="{category:'?0'}")
    public ProductEntity findByCategory(String category);

    @Query(value="{category:'?0'}")
    public ProductEntity findByName(String name);


    @Query(value="{category:'?0'}", count = true)
    public List<ProductEntity> findCategory(String category);


}
