package com.example.receiptservice;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;

@SpringBootApplication
public class ReceiptServiceApplication {

	public static void main(String[] args) {











//		String path="invoice.pdf";
//		PdfWriter pdfWriter = new PdfWriter(path);
//		PdfDocument pdfDocument = new PdfDocument(pdfWriter);
//		pdfDocument.setDefaultPageSize(PageSize.A4);
//		com.itextpdf.layout.Document document = new Document(pdfDocument);
//
//
//		document.add(new Paragraph("Hello world"));
//		document.close();


		SpringApplication.run(ReceiptServiceApplication.class, args);
		System.out.println("Pdf_Start");

	}

}
