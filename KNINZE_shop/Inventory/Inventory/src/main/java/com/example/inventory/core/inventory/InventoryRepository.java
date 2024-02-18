package com.example.inventory.core.inventory;


import com.example.core.commands.Product_Core;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends MongoRepository<InventoryEntity, String> {

    @Query("{category: ?0, subcategory: ?1, name: ?2}")
    InventoryEntity findByCategoryAndSubcategoryAndName(String category, String subcategory, String name);




    Page<InventoryEntity> findAll(Pageable pageable);

    @Query(value = "{}", fields = "{ 'category': 1, '_id': 0 }")
    List<String> findDistinctCategories();


    @Query("SELECT DISTINCT subcategory FROM Product")
    List<String> findDistinctSubcategories();

    @Query("{ $and: [ { $regex: ?0, $options: 'i' }, { $regex: ?1, $options: 'i' }, { $regex: ?2, $options: 'i' }, { $regex: ?3, $options: 'i' } ] }")
    List<InventoryEntity> findByFilters(String nameFilter, String amountFilter, String categoryFilter, String subCategoryFilter);




    @Query(value="{category:'?0'}", count = true)
    public List<InventoryEntity> findCategoryByname(String category);

    @Query(value="{name:'?0'}", count = true)
    public List<InventoryEntity> findByFiltersByName(String name);


    @Query(value="{name:'?0'}")
    public List<InventoryEntity> findProductByName(String nameOfOrders);






}
