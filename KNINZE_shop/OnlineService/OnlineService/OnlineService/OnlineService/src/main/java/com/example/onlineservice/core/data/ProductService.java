package com.example.onlineservice.core.data;


import com.example.onlineservice.command.rest.CategoryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<ProductEntity> retrieveProducts() {
        return productRepository.findAll();
//        repository.findAll() มันจะส่งคืนข้อมูลในรูปแบบของ List<Wizard> เลยใช้ ArrayList ไม่ได้
    }



    public List<ProductEntity> retrieveProductsByCategory(String category) {
        System.out.println("category : " + category);
        List<ProductEntity> product = productRepository.findAll();
        List<ProductEntity> test1 = new ArrayList<>();
        for(ProductEntity item : product){
            if(item.getCategory().equals(category)){
                test1.add(item);
            }
        }
        return test1;
    }

    public List<ProductEntity> retrieveProductsBySubCategory(String category, String subCategory) {
        List<ProductEntity> product = productRepository.findAll();
        List<ProductEntity> test1 = new ArrayList<>();
        for(ProductEntity item : product){
            if(item.getCategory().equals(category) && item.getSubcategory().equals(subCategory)){
                test1.add(item);
            }
        }
        return test1;
    }




    public List<ProductEntity> retrieveBookByName(String name) {
        return productRepository.findCategory(name);
    }



}
