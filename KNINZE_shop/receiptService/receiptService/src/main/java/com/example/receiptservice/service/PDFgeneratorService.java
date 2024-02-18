package com.example.receiptservice.service;


import com.example.core.commands.Product_Core;
import com.example.receiptservice.controller.CreateOrderRestModel;

import com.example.receiptservice.data.customer.CustomerEntity;
import com.example.receiptservice.data.customer.CustomerRepository;
import com.example.receiptservice.data.customer.CustomerService;
import com.example.receiptservice.data.history.HistoryEntity;
import com.example.receiptservice.data.history.HistoryRepository;
import com.example.receiptservice.data.history.HistoryService;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Tab;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.alignment.HorizontalAlignment;
import com.lowagie.text.alignment.VerticalAlignment;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.StyleConstants;
import java.awt.*;
import java.io.IOException;
import java.util.List;

@Service
public class PDFgeneratorService {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private CustomerService customerService;


    public void export(HttpServletResponse response, String email, CustomerEntity customerEntity, CreateOrderRestModel model) throws IOException {

    // http://localhost:8080/receipt/pdf/generate/64070245@kmitl.ac.th

        CustomerEntity customer = customerService.retrieveCustomerByCustomerId(email);
        List history = historyService.retrieveHistoryByEmail(email);





        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();




//        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
//        fontTitle.setSize(18);
//
//        Paragraph paragraph = new Paragraph("This is a title.", fontTitle);
//        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
//
//        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
//        fontParagraph.setSize(12);
//
//        Paragraph paragraph2 = new Paragraph("This is a paragraph", fontParagraph);
//        paragraph2.setAlignment(Paragraph.ALIGN_LEFT);





        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(20);
        Paragraph paragraph = new Paragraph("INVOICE", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_RIGHT);

        Font fontParagraph2 = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph2.setSize(12);
        Paragraph paragraph2 = new Paragraph("Date : " + model.getOrderDate(), fontParagraph2);
        paragraph2.setAlignment(Paragraph.ALIGN_RIGHT);


        Font fontParagraph6 = FontFactory.getFont(FontFactory.defaultEncoding);
        fontParagraph6.setSize(10);
        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);

        Font fontParagraph7 = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontParagraph6.setSize(16);
        Font fontParagraph38 = FontFactory.getFont(FontFactory.defaultEncoding);
        fontParagraph6.setSize(12);


        Paragraph paragraph5 = new Paragraph("eamil : " + customerEntity.getEmail(), fontParagraph38);
        paragraph5.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph paragraph6 = new Paragraph("phone : " + customerEntity.getPhone(), fontParagraph38);
        paragraph6.setAlignment(Paragraph.ALIGN_LEFT);


        Paragraph blank = new Paragraph("\n", fontParagraph6);
        paragraph6.setAlignment(Paragraph.ALIGN_LEFT);











        float twoCol = 285f;
        float twoCol150=twoCol+150f;
        float twocolumnWidth[] = {twoCol150, twoCol};

        Table table = new Table(new Table(4, model.getOrders().size()*2));

        Cell cel0 = new Cell("item");
        Cell cel00 = new Cell("Product");
        Cell cel01 = new Cell("Price");
        Cell cel02 = new Cell("Amount");
        cel0.setBackgroundColor(Color.LIGHT_GRAY);
        cel00.setBackgroundColor(Color.LIGHT_GRAY);
        cel01.setBackgroundColor(Color.LIGHT_GRAY);
        cel02.setBackgroundColor(Color.LIGHT_GRAY);
        cel0.setWidth(2);
        cel00.setWidth(2);
        cel01.setWidth(2);
        cel02.setWidth(2);
        cel0.setVerticalAlignment(VerticalAlignment.CENTER);
        cel00.setVerticalAlignment(VerticalAlignment.CENTER);
        cel01.setVerticalAlignment(VerticalAlignment.CENTER);
        cel02.setVerticalAlignment(VerticalAlignment.CENTER);
        cel0.setVerticalAlignment(Element.ALIGN_CENTER);
        cel00.setVerticalAlignment(Element.ALIGN_CENTER);
        cel01.setVerticalAlignment(Element.ALIGN_CENTER);
        cel02.setVerticalAlignment(Element.ALIGN_CENTER);



        cel0.setHorizontalAlignment(HorizontalAlignment.CENTER);
        cel00.setHorizontalAlignment(HorizontalAlignment.CENTER);
        cel01.setHorizontalAlignment(HorizontalAlignment.CENTER);
        cel02.setHorizontalAlignment(HorizontalAlignment.CENTER);
        cel0.setBorder(0);
        cel00.setBorder(0);
        cel01.setBorder(0);
        cel02.setBorder(0);
        Cell celllll1 = new Cell("กดห");
        Cell celllll2 = new Cell("หกด");
        Cell celllll3 = new Cell("หกด");
        Cell celllll4 = new Cell("หกด");
        celllll1.setBorder(0);
        celllll2.setBorder(0);
        celllll3.setBorder(0);
        celllll4.setBorder(0);


        table.addCell(cel0);
        table.addCell(cel00);
        table.addCell(cel01);
        table.addCell(cel02);
        table.addCell(celllll1);
        table.addCell(celllll2);
        table.addCell(celllll3);
        table.addCell(celllll4);

        int i = 1;
        for (Product_Core item : model.getOrders()){

            System.out.println(item.getNameEng());
            Cell cel1 = new Cell(i+"");
            Cell cel2 = new Cell(String.valueOf(item.getNameEng()));
            Cell cel3 = new Cell(String.valueOf(item.getCost()));
            Cell cel4 = new Cell(String.valueOf(item.getAmount()));
            Cell cel11111 = new Cell("กดห");
            Cell cel22222 = new Cell("หกด");
            Cell cel33333 = new Cell("หกด");
            Cell cel44444 = new Cell("หกด");

            cel4.setHorizontalAlignment(HorizontalAlignment.CENTER);
            cel1.setHorizontalAlignment(HorizontalAlignment.CENTER);
            cel2.setHorizontalAlignment(HorizontalAlignment.CENTER);
            cel3.setHorizontalAlignment(Element.ALIGN_CENTER);


            cel1.setBorder(0);
            cel2.setBorder(0);
            cel3.setBorder(0);
            cel4.setBorder(0);
            cel11111.setBorder(0);
            cel22222.setBorder(0);
            cel33333.setBorder(0);
            cel44444.setBorder(0);

            table.addCell(cel1);
            table.addCell(cel2);
            table.addCell(cel3);
            table.addCell(cel4);
            table.addCell(cel11111);
            table.addCell(cel22222);
            table.addCell(cel33333);
            table.addCell(cel44444);
            ++i;
        }

        table.setBorder(1);


        Table table1 = new Table(new Table(4, 4));

        Cell celtable1 = new Cell("Package Discount ");
        Cell celtable2 = new Cell("- "+model.getPromotion());
//        celtable1.setBackgroundColor(Color.ORANGE);
//        celtable2.setBackgroundColor(Color.ORANGE);

        celtable1.setHorizontalAlignment(HorizontalAlignment.CENTER);
        celtable2.setHorizontalAlignment(HorizontalAlignment.CENTER);



        celtable1.setBorder(0);
        celtable2.setBorder(0);
        Cell asf = new Cell("หกด");
        Cell fgsfd = new Cell("หกด");
        asf.setBorder(0);
        fgsfd.setBorder(0);







        Cell celtable1111 = new Cell("Total ");
        Cell celtable2222 = new Cell(model.getTotalPrice()+"");
        celtable1111.setBackgroundColor(Color.ORANGE);
        celtable2222.setBackgroundColor(Color.ORANGE);
        celtable1111.setHorizontalAlignment(HorizontalAlignment.CENTER);
        celtable2222.setHorizontalAlignment(HorizontalAlignment.CENTER);
        celtable1111.setBorder(0);
        celtable2222.setBorder(0);
        Cell dsge = new Cell("หกด");
        Cell fgrteuyusfd = new Cell("หกด");
        dsge.setBorder(0);
        fgrteuyusfd.setBorder(0);

        Cell dfsg = new Cell("กดห");
        Cell celgsdgdllll2 = new Cell("หกด");
        Cell celggfdgllll3 = new Cell("หกด");
        Cell cesdfdslllll4 = new Cell("หกด");
        dfsg.setBorder(0);
        celgsdgdllll2.setBorder(0);
        celggfdgllll3.setBorder(0);
        cesdfdslllll4.setBorder(0);


        table1.addCell(asf);
        table1.addCell(fgsfd);
        table1.addCell(celtable1);
        table1.addCell(celtable2);

        table1.addCell(dfsg);
        table1.addCell(celgsdgdllll2);
        table1.addCell(celggfdgllll3);
        table1.addCell(cesdfdslllll4);


        table1.addCell(dsge);
        table1.addCell(fgrteuyusfd);
        table1.addCell(celtable1111);
        table1.addCell(celtable2222);


        table1.setBorder(0);
        table1.setBorderWidthTop(1);



//        Font fontParagraph7 = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
//        fontParagraph6.setSize(16);

        Paragraph paragraph7 = new Paragraph("KNIZE SHOP", fontParagraph7 );
        paragraph6.setAlignment(Paragraph.ALIGN_LEFT);

        Font fontParagraph8 = FontFactory.getFont(FontFactory.defaultEncoding);
        fontParagraph8.setSize(13);

        Paragraph paragraph8 = new Paragraph("Call +123-456-7890", fontParagraph8 );
        paragraph8.setAlignment(Paragraph.ALIGN_LEFT);






        document.add(paragraph);
        document.add(paragraph2);

        document.add(blank);

        document.add(paragraph5);
        document.add(paragraph6);
        document.add(blank);


        document.add(table);
        document.add(table1);



        document.add(blank);

        document.add(paragraph7);
        document.add(paragraph8);
        document.close();



    }

}
