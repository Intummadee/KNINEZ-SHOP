package com.example.receiptservice.data.history;


import com.example.receiptservice.data.history.HistoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends MongoRepository<HistoryEntity, String> {

    @Query(value="{email:'?0'}")
    public HistoryEntity findByUserName(String email);


    @Query(value="{email:'?0'}")
    public HistoryEntity findHistoryOfEmail(String email);


    @Query(value="{email:'?0'}", fields = "{'orders':1, '_id':0}")
    public List findOrdersByUserName(String email);



    @Query(value="{email:'?0'}")
    public HistoryEntity findByEmail(String email);


}
