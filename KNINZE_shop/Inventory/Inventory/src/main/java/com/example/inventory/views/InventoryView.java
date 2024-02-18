package com.example.inventory.views;

import com.example.inventory.core.inventory.InventoryEntity;
import com.example.inventory.core.inventory.InventoryService;
import com.vaadin.flow.component.ScrollOptions;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@PageTitle("Inventory Management")
@Route(value = "inventory", layout = MainLayout.class)
//@Uses(Icon.class)
public class InventoryView extends VerticalLayout {

    // http://localhost:8082/inventory/my-view3


    private InventoryService inventoryService;
    HorizontalLayout filterRow, gridAndFormRow, categoryRow, buttonRow ;
    VerticalLayout formColumn;
    TextField nameField, amountField, nameForm,amountForm,  priceForm, nameEngForm , urlForm;

    ComboBox<String> category, subCategory;

//    Button search, cancleButton, saveButton,  deleteButton;
    Button  cancleButton, saveButton,  deleteButton;

    Grid<InventoryEntity> inventoryGrid;

    ComboBox<String> categoryForm , subCategoryForm;



    @Autowired
    public InventoryView(InventoryService inventoryService) {

        this.inventoryService = inventoryService;

        Page<InventoryEntity> page = inventoryService.list(PageRequest.of(0, 500));
        List<InventoryEntity> items = page.getContent();


        HashMap<String, Object> data = new HashMap<>();
        data.put("อาหาร", new String[]{"ข้าว", "ขนมปัง", "ไส้กรอก"});
        data.put("ของทานเล่น", new String[]{"ของหวาน", "ขนมคบเคี้ยว", "เบเกอรี่"});
        data.put("เครื่องดื่ม", new String[]{"น้ำเปล่า", "ชา", "กาแฟ"});
        data.put("สุขภาพและความงาม", new String[]{"ยา", "อาหารเสริม", "เครื่องสำอาง"});
        data.put("ของใช้", new String[]{"ห้องน้ำ", "ซักผ้า", "ของใช้ทั่วไป"});

        List<String> subCateGoryList = new ArrayList<>();
        List<String> CateGoryList = new ArrayList<>();


        for (String key : data.keySet()) {
            Object value = data.get(key);
            CateGoryList.add(key);
            if (value instanceof String[]) {
                String[] items1 = (String[]) value;
                for (String item : items1) {
                    System.out.println("item in String " + item);
                    subCateGoryList.add(item);
                }
            }
        }

        for (String item : CateGoryList) {
            System.out.println("CateGoryList " + item);
        }

        System.out.println(subCateGoryList);
        System.out.println(CateGoryList);



//        List<String> categories = inventoryService.retrieveCategoryOnly();
//        List<String> subcategories = inventoryService.getSubcategories();


        filterRow = new HorizontalLayout();
        nameField = new TextField();
        amountField = new TextField();
        category = new ComboBox<>();
        subCategory = new ComboBox<>();
//        search = new Button();
        gridAndFormRow = new HorizontalLayout();
        inventoryGrid = new Grid<>();
        formColumn = new VerticalLayout();
        nameEngForm = new TextField();
        nameForm = new TextField();
        urlForm = new TextField();
        amountForm = new TextField();
        priceForm = new TextField();
        categoryRow = new HorizontalLayout();
        categoryForm = new ComboBox<>();
        subCategoryForm = new ComboBox<>();
        buttonRow = new HorizontalLayout();
        cancleButton = new Button();
        saveButton = new Button();
        deleteButton = new Button();


        this.setWidth("100%");
        this.setHeight("100%");
        this.getStyle().set("flex-grow", "1");
        filterRow.setWidthFull();
        this.setFlexGrow(1.0, filterRow);
        filterRow.addClassName(Gap.LARGE);
        //filterRow.addClassName(Padding.MEDIUM);
        filterRow.setWidth("min-content");
        filterRow.setHeight("min-content");

        nameField.setLabel("ชื่อสินค้า");
        nameField.setWidth("min-content");

        amountField.setLabel("Text field");
        amountField.setWidth("min-content");

        category.setLabel("หมวดหมู่สินค้า");
        categoryForm.setWidth("min-content");
        //setCatBox(comboBox);

        subCategory.setLabel("หมวดหมู่ย่อย");
        subCategory.setWidth("min-content");
        //setSubCatBox(comboBox2);
//        search.setText("search");
//        filterRow.setAlignSelf(FlexComponent.Alignment.END, search);
//        search.setWidth("90px");
//        search.setHeight("36px");
//        search.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        gridAndFormRow.setWidthFull();
        this.setFlexGrow(1.0, gridAndFormRow);
        gridAndFormRow.addClassName(Gap.MEDIUM);
        gridAndFormRow.setWidth("100%");
        gridAndFormRow.getStyle().set("flex-grow", "1");
        inventoryGrid.setWidth("100%");
        inventoryGrid.getStyle().set("flex-grow", "0");
        inventoryGrid.setHeightFull();
        inventoryGrid.asSingleSelect().addValueChangeListener(event -> {
            InventoryEntity selectedEntity = event.getValue();
            if (selectedEntity != null) {
                populateFormFields(selectedEntity);
            } else {
                clearFormFields();
            }
        });

        //setGridSampleData(basicGrid);

        formColumn.setHeight("100%");
        formColumn.setWidth("30%");
        gridAndFormRow.setFlexGrow(1, inventoryGrid);
        //gridAndFormRow.addClassName(Padding.XLARGE);
        gridAndFormRow.getStyle().set("flex-grow", "1");
        gridAndFormRow.setHeight("100%");
        gridAndFormRow.setJustifyContentMode(JustifyContentMode.START);
        gridAndFormRow.setAlignItems(Alignment.END);

        nameForm.setLabel("ชื่อสินค้า");
        nameForm.setWidth("100%");

        nameEngForm.setLabel("ชื่อภาษาอังกฤษ");
        nameEngForm.setWidth("100%");
        urlForm.setLabel("ลิงค์รูปภาพ");
        urlForm.setWidth("100%");


//        amountForm.setHeight("57px");
        amountForm.setLabel("จำนวนสินค้า");
        amountForm.setWidth("100%");
        priceForm.setLabel("ราคาสินค้า");
        priceForm.setWidth("100%");

        categoryRow.setWidthFull();
        formColumn.setFlexGrow(1.0, categoryRow);
        categoryRow.addClassName(Gap.MEDIUM);
        categoryRow.setWidth("100%");
        categoryRow.setHeight("min-content");
        categoryForm.setLabel("หมวดหมู่สินค้า");
        categoryForm.setWidth("100%");
        categoryForm.setItems(CateGoryList);
        //setComboBoxSampleData(comboBox3);
        subCategoryForm.setLabel("หมวดหมู่ย่อย");
        subCategoryForm.setWidth("100%");
        subCategoryForm.setItems(subCateGoryList);
        //setComboBoxSampleData(comboBox4);


        formColumn.setFlexGrow(1.0, buttonRow);
        buttonRow.addClassName(Gap.MEDIUM);
        buttonRow.setWidth("100%");
        //buttonRow.setHeight("50%");
        cancleButton.setText("ยกเลิก");
        cancleButton.getStyle().set("flex-grow", "1");
        cancleButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.setText("บันทึกข้อมูล");
        saveButton.getStyle().set("flex-grow", "1");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteButton.setText("ลบข้อมูล");
        deleteButton.getStyle().set("flex-grow", "1");
        deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        this.add(filterRow);
//        filterRow.add(nameField);
//        filterRow.add(amountField);
//        filterRow.add(category);
//        filterRow.add(subCategory);
//        filterRow.add(search);

        this.add(gridAndFormRow);
        //gridAndFormRow.add(inventoryGrid);
        setupGrid(items, inventoryGrid);
        gridAndFormRow.add(inventoryGrid,formColumn);
        formColumn.add(nameForm);
        formColumn.add(amountForm);
        formColumn.add(priceForm);
        formColumn.add(categoryRow);
        formColumn.add(nameEngForm);
        formColumn.add(urlForm);


        categoryRow.add(categoryForm);
        categoryRow.add(subCategoryForm);
        formColumn.add(buttonRow);
        buttonRow.add(cancleButton);
        buttonRow.add(saveButton);
        buttonRow.add(deleteButton);

        saveButton.addClickListener(event -> {
            if (validateForm()) {
                InventoryEntity entityToUpdate = inventoryGrid.asSingleSelect().getValue();
                System.out.println("saveButton" + entityToUpdate);
                if (entityToUpdate != null) {
                    // Update existing entity
                    updateEntityFromForm(entityToUpdate);
                    inventoryService.updateItem(entityToUpdate.get_id(), entityToUpdate);
                } else {
                    // Add new entity
                    InventoryEntity newEntity = createEntityFromForm();
                    System.out.println("saveButton Add new entity" + newEntity);
                    inventoryService.addItem(newEntity);
                    clearFormFields();
                    setupGrid(inventoryService.list(PageRequest.of(0, 500)).getContent(), inventoryGrid);
                }

                refreshGrid();
            }
        });

        deleteButton.addClickListener(event -> {
            InventoryEntity entityToDelete = inventoryGrid.asSingleSelect().getValue();
            System.out.println("delete" + entityToDelete);
            if (entityToDelete != null) {
                inventoryService.deleteItem(entityToDelete.get_id());
                // Refresh the UI
                refreshGrid();
            }
        });

        cancleButton.addClickListener(event -> {
            clearFormFields();
        });

//        search.addClickListener(event -> {
//            String nameFilter = nameField.getValue().trim();
////            String amountFilter = amountField.getValue().trim();
////            String categoryFilter = category.getValue();
////            String subCategoryFilter = subCategory.getValue();
//
//            // Fetch filtered data from the service
////            List<InventoryEntity> filteredData = inventoryService.filterData(nameFilter, amountFilter, categoryFilter, subCategoryFilter);
//            List<InventoryEntity> filteredData = inventoryService.filterDataBYName(category.getValue());
//
//
//
//
//            // Update the Grid with filtered data
//            setupGrid(filteredData, inventoryGrid);
//
//            // Show 'not found' notification if no matching data
//            if (filteredData.isEmpty()) {
//                Notification.show("ไม่พบข้อมูล", 3000, Notification.Position.TOP_CENTER);
//            }
//        });



    }

    private void refreshGrid() {
        List<InventoryEntity> updatedItems = inventoryService.list(PageRequest.of(0, 500)).getContent();
        ListDataProvider<InventoryEntity> dataProvider = DataProvider.ofCollection(updatedItems);
        inventoryGrid.setDataProvider(dataProvider);
    }


    private void setupGrid(List<InventoryEntity> items, Grid<InventoryEntity> inventoryGrid) {
        // Create a Grid and set its data provider
//        ListDataProvider<InventoryEntity> dataProvider = new ListDataProvider<>(items);
//        inventoryGrid.setDataProvider(dataProvider);
        inventoryGrid.setItems(items);
        inventoryGrid.addColumn(InventoryEntity::getName).setHeader("ชื่อสินค้า");
        inventoryGrid.addColumn(InventoryEntity::getCategory).setHeader("หมวดหมู่");
        inventoryGrid.addColumn(InventoryEntity::getSubcategory).setHeader("หมวดหมู่ย่อย");
        inventoryGrid.addColumn(InventoryEntity::getAmount).setHeader("จำนวนสินค้าคงเหลือ");
        inventoryGrid.addColumn(InventoryEntity::getCost).setHeader("ราคาสินค้า");
        inventoryGrid.addColumn(InventoryEntity::getUrl).setHeader("ลิงค์รูปภาพ");
        inventoryGrid.addColumn(InventoryEntity::getNameEng).setHeader("ชื่อสินค้าภาษาอังกฤษ");

    }

    private void populateFormFields(InventoryEntity entity) {
        nameForm.setValue(entity.getName());
        amountForm.setValue(String.valueOf(entity.getAmount()));
        priceForm.setValue(String.valueOf(entity.getCost()));
        categoryForm.setValue(entity.getCategory());
        subCategoryForm.setValue(entity.getSubcategory());
        urlForm.setValue(entity.getUrl());
        nameEngForm.setValue(entity.getNameEng());
    }

    private void clearFormFields() {
        nameForm.clear();
        amountForm.clear();
        priceForm.clear();
        categoryForm.clear();
        subCategoryForm.clear();
        urlForm.clear();
        nameEngForm.clear();
    }

    private boolean validateForm() {
        if (nameForm.isEmpty() || amountForm.isEmpty() || priceForm.isEmpty()
                || categoryForm.getValue() == null || subCategoryForm.getValue() == null ) {
            // Show notification for incomplete form
            Notification.show("กรุณาป้อนข้อมูลให้ครบ", 3000, Notification.Position.TOP_CENTER);
            return false;
        }

        try {
            Integer.parseInt(amountForm.getValue()); // Check if amount is a valid integer
        } catch (NumberFormatException e) {
            // Show notification for invalid amount
            Notification.show("กรุณากรอกจำนวนสินค้าให้ถูกต้อง", 3000, Notification.Position.TOP_CENTER);
            return false;
        }

        try {
            Double.parseDouble(priceForm.getValue()); // Check if price is a valid double
        } catch (NumberFormatException e) {
            // Show notification for invalid price
            Notification.show("กรุณากรอกราคาให้ถูกต้อง", 3000, Notification.Position.TOP_CENTER);
            return false;
        }
        return true;
    }
    private void updateEntityFromForm(InventoryEntity entity) {
        entity.setName(nameForm.getValue());
        entity.setAmount(Integer.parseInt(amountForm.getValue()));
        entity.setCost(Double.parseDouble(priceForm.getValue()));
        entity.setCategory(categoryForm.getValue());
        entity.setSubcategory(subCategoryForm.getValue());
        entity.setUrl(urlForm.getValue());
        entity.setNameEng(nameEngForm.getValue());
    }

    private InventoryEntity createEntityFromForm() {
        String name = nameForm.getValue();
        int amount = Integer.parseInt(amountForm.getValue());
        double cost = Double.parseDouble(priceForm.getValue());
        String category = categoryForm.getValue();
        String subcategory = subCategoryForm.getValue();
        String nameEng = nameEngForm.getValue();
//        String url = subCategoryForm.getValue();

        return new InventoryEntity(null, category, subcategory, name, cost, amount, "", nameEng);
    }
}
