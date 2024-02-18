package com.example.customerservice.core.data.history;


import com.example.customerservice.core.data.history.HistoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface HistoryRepository extends MongoRepository<HistoryEntity, String> {

    @Query(value="{email:'?0'}")
    public HistoryEntity findByEmail(String email);


    @Query(value="{userName:'?0'}", fields = "{'orders':1, '_id':0}")
    public List findOrdersByUserName(String userName);




}
