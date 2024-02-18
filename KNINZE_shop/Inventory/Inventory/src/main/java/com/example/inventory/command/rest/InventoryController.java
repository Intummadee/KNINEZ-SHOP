package com.example.inventory.command.rest;


import com.example.inventory.core.inventory.InventoryEntity;
import com.example.inventory.core.inventory.InventoryService;
import com.example.inventory.query.command.ReserveProductRestQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String helloWorld() {
        return "Hello World Test";
    }
//    http://localhost:8082/inventory/inventory/test



    @PostMapping("/processOrder")
    public ResponseEntity<String> processOrderController(@RequestBody ReserveProductRestQuery reserveProductRestQuery) {
//    reserveProductRestQuery = inventoryId , orderId, userName , orders
        if (inventoryService.processOrder(reserveProductRestQuery)) {
            return ResponseEntity.ok("สั่งซื้อสำเร็จ");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("สินค้าไม่เพียงพอ คำสั่งซื้อถูกปฏิเสธ");
        }
    }

    @PostMapping("/addItem")
    public ResponseEntity<String> addItem(@RequestBody InventoryEntity inventoryItem) {
        if (inventoryService.addItem(inventoryItem)) {
            return ResponseEntity.ok("Item added successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to add item to inventory");
        }
    }

    @PutMapping("/updateItem/{itemId}")
    public ResponseEntity<String> updateItem(
            @PathVariable String itemId, @RequestBody InventoryEntity updatedItem) {
        if (inventoryService.updateItem(itemId, updatedItem)) {
            return ResponseEntity.ok("Item updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to add item to inventory");
        }
    }

    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable String itemId) {
        if (inventoryService.deleteItem(itemId)) {
            return ResponseEntity.ok("Item deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Item not found or delete failed");
        }
    }


    // ดึงออกมาทแค่ category เพื่อไปใช้ในcomboBox Vaadin
    @GetMapping("getOnlyCategory") // http://localhost:8082/inventory/inventory/getOnlyCategory
    public ArrayList<String> getOnlyCategory(){
        ArrayList<String> categories = new ArrayList();
        for (InventoryEntity item : inventoryService.retrieveProducts()){
            String category = item.getCategory();
            if (!categories.contains(category)) {
                categories.add(category);
            }
        }
        return categories;
    }








}
