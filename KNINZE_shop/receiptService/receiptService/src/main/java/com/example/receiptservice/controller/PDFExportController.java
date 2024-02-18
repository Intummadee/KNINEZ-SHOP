package com.example.receiptservice.controller;


import com.example.receiptservice.data.customer.CustomerEntity;
import com.example.receiptservice.data.customer.CustomerService;
import com.example.receiptservice.data.history.HistoryEntity;
import com.example.receiptservice.data.history.HistoryService;
import com.example.receiptservice.service.PDFgeneratorService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RequestMapping("/receipt")
@RestController
public class PDFExportController {


    @Autowired
    private HistoryService historyService;

    @Autowired
    private CustomerService customerService;


    private final PDFgeneratorService pdFgeneratorService;


    public PDFExportController(PDFgeneratorService pdFgeneratorService){
        this.pdFgeneratorService = pdFgeneratorService;
    }


    @GetMapping("/pdf/generate/{email}") // http://localhost:8080/receipt/pdf/generate/64070245@kmitl.ac.th
    public void generatePdf(HttpServletResponse response, @PathVariable("email") String email, @RequestBody CreateOrderRestModel model) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        CustomerEntity customer = customerService.retrieveCustomerByCustomerId(email);

        this.pdFgeneratorService.export(response , email, customer, model);
    }


    @GetMapping("/test") // http://localhost:8080/receipt/test
    public String helloWorld() {
        return "Hello World";
    }


    // หา customer จาก email
    @GetMapping("/findCustomer/{email}")
    public ResponseEntity<?> getCustomer(@PathVariable("email") String email) {
        CustomerEntity customer = customerService.retrieveCustomerByCustomerId(email);
        return ResponseEntity.ok(customer);
        //  http://localhost:8080/receipt/findCustomer/64070245@kmitl.ac.th
    }


    // หา history จาก email จะได้เป็น array history ของลูกค้าออกมา
    @GetMapping("/findHistory/{email}")
    public ResponseEntity<?> getHistory(@PathVariable("email") String email) {
        List history = historyService.retrieveHistoryByEmail(email);
        return ResponseEntity.ok(history);
        //  http://localhost:8080/receipt/findHistory/64070245@kmitl.ac.th
    }











}
