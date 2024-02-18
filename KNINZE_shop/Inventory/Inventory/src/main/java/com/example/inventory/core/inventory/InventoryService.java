package com.example.inventory.core.inventory;


import com.example.core.commands.Product_Core;
import com.example.inventory.query.command.ReserveProductRestQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository){
        this.inventoryRepository = inventoryRepository;
    }

    //เอา isInventoryAvailable มาใช้เช็คกับ command ลูกค้า ถ้าตรงตามเงื่อไขก็ update
    public boolean processOrder(ReserveProductRestQuery reserveProductRestQuery) {
        if (isInventoryAvailable(reserveProductRestQuery.getOrders())) {
            updateInventory(reserveProductRestQuery.getOrders());
            return true; // สินค้าพอ
        } else {

            return false; // สินค้าไม่พอ
        }
    }


    //check db
    private boolean isInventoryAvailable(List<Product_Core> orderItems) {
        System.out.println("isInventoryAvailable" + orderItems.get(0));
        for (int i = 0; i < orderItems.size(); i++) {
            System.out.println(orderItems.get(i));
            Product_Core orderItem = orderItems.get(i);
            InventoryEntity inventoryItem = inventoryRepository.findByCategoryAndSubcategoryAndName(
                    orderItem.getCategory(), orderItem.getSubcategory(), orderItem.getName());
            System.out.println("InventoryItem: " + inventoryItem);
            if (inventoryItem == null || inventoryItem.getAmount() < orderItem.getAmount()) {
                return false; // สินค้าไม่พอ
            }
        }
        return true; // สินค้าพอ
    }


    //update db
    private void updateInventory(List<Product_Core> orderItems) {
        for (int i = 0; i < orderItems.size(); i++) {
            Product_Core orderItem = orderItems.get(i);
            InventoryEntity inventoryItem = inventoryRepository.findByCategoryAndSubcategoryAndName(
                    orderItem.getCategory(), orderItem.getSubcategory(), orderItem.getName());
            if (inventoryItem != null) {
                inventoryItem.decreaseQuantity(orderItem.getAmount());
                inventoryRepository.save(inventoryItem);
            }
        }
    }

    public List<InventoryEntity> retrieveProducts() {
        return inventoryRepository.findAll();
    }


    // ดึงออกมาทแค่ category เพื่อไปใช้ในcomboBox Vaadin
    public List<String> retrieveCategoryOnly(){
        ArrayList<String> categories = new ArrayList();
        for (InventoryEntity item : inventoryRepository.findAll()){
            String category = item.getCategory();
            if (!categories.contains(category)) {
                categories.add(category);
            }
        }
        return categories;
    }




//    เพิ่มเข้าครั้ง สอง
    public boolean addItem(InventoryEntity newItem) {
        // Implement logic to add a new item to the inventory
        inventoryRepository.save(newItem);
        return true;
    }

    public boolean updateItem(String itemId, InventoryEntity updatedItem) {
        // Implement logic to update an existing item in the inventory
        Optional<InventoryEntity> existingItem = inventoryRepository.findById(itemId);

        if (existingItem.isPresent()) {
            // Update the existing item with the new data
            InventoryEntity currentItem = existingItem.get();
            currentItem.setCategory(updatedItem.getCategory());
            currentItem.setSubcategory(updatedItem.getSubcategory());
            currentItem.setName(updatedItem.getName());
            currentItem.setCost(updatedItem.getCost());
            currentItem.setAmount(updatedItem.getAmount());
            currentItem.setUrl(updatedItem.getUrl());
            currentItem.setNameEng(updatedItem.getNameEng());

            inventoryRepository.save(currentItem);
            return true;
        } else {
            return false; // Item not found
        }
    }

    public boolean deleteItem(String itemId) {
        // Implement logic to delete an item from the inventory
        Optional<InventoryEntity> existingItem = inventoryRepository.findById(itemId);

        if (existingItem.isPresent()) {
            inventoryRepository.deleteById(itemId);
            return true;
        } else {
            return false; // Item not found
        }
    }


    public Page<InventoryEntity> list(Pageable pageable) {
        return inventoryRepository.findAll(pageable);
    }

    public List<String> getCategories() {
        return inventoryRepository.findDistinctCategories();
    }

    public List<String> getSubcategories() {
        return inventoryRepository.findDistinctSubcategories();
    }

    public List<InventoryEntity> filterData(String nameFilter, String amountFilter, String categoryFilter, String subCategoryFilter) {
        // Implement the logic to filter data based on the provided criteria
        // You can use your repository methods or customize the query as needed
        return inventoryRepository.findByFilters(nameFilter, amountFilter, categoryFilter, subCategoryFilter);
    }


    public List<InventoryEntity> filterDataBYName(String category){

        return inventoryRepository.findByFiltersByName(category);
    }


    public List<InventoryEntity> retrieveProductByName(String name){
        return inventoryRepository.findProductByName(name);
    }

    public InventoryEntity undoAmountProduct(InventoryEntity productCore){
        return inventoryRepository.save(productCore);
    }



}
