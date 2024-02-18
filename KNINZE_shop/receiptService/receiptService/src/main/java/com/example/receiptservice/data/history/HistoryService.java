package com.example.receiptservice.data.history;


import com.example.receiptservice.data.Order_core;
import com.example.receiptservice.data.history.HistoryEntity;
import com.example.receiptservice.data.history.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }


    public HistoryEntity retrieveAllHistory(String email) {
        return historyRepository.findHistoryOfEmail(email);
    }

    public Object retriveOrdersByUserName(String userName){
        return historyRepository.findOrdersByUserName(userName);
    }

    public List retrivePriceAllByUserName(String userName){
//        Object history = historyRepository.findOrdersByUserName(userName);
        HistoryEntity test = historyRepository.findByUserName(userName);

        List test1 = new ArrayList<>();
        for(Object item : test.getOrders()){
            test1.add(item);
            System.out.println(item);
        }

        for (Object product : test1){
            System.out.println(product); // [{category=เครื่องดื่ม, name=แป๊ปซี่, subcategory=นํ้าอัดลม, cost=15.0, amount=1, totalPrice=0.0}, {category=อาหาร, name=แกงเขียวหวาน, subcategory=ของคาว, cost=15.0, amount=1, totalPrice=0.0}]
        }
        return test1;
    }



    public HistoryEntity updateHistory(HistoryEntity historyEntity) {
        return historyRepository.save(historyEntity);
    }
//    65452490f0ac5f5c654e0d8c

    public ArrayList retrieveHistoryByEmail(String email){
        HistoryEntity historyEntity1 = historyRepository.findByEmail(email);
        ArrayList list = new ArrayList();
        for(ArrayList item: historyEntity1.getOrders()){
            for (Object itrem1 : item) {
                if (itrem1 instanceof LinkedHashMap) {
                    LinkedHashMap<String, Object> orderMap = (LinkedHashMap<String, Object>) itrem1;
                    String category = String.valueOf(orderMap.get("category"));
                    String name = String.valueOf(orderMap.get("name"));
                    String subcategory = String.valueOf(orderMap.get("subcategory"));
                    String cost = String.valueOf(orderMap.get("cost"));
                    String amount = String.valueOf(orderMap.get("amount"));
                    String totalPrice = String.valueOf(orderMap.get("totalPrice"));
                    String date = String.valueOf(orderMap.get("date"));
                    Order_core orderCore = new Order_core(category, name, subcategory, cost, amount, totalPrice, date);
                    list.add(orderCore);
                }
            }
        }
        return list;
    }


    public List retrieveAllByEmail(String email){
        return historyRepository.findOrdersByUserName(email);
    }






}
