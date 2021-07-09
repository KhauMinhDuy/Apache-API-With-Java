package com.khauminhduy;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

public class ProductCatalog {

	public void generateCatalog() {
		try(PDDocument document = new PDDocument();) {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			document.save("src/main/resources/product_catalog.pdf");
			System.out.println("Product Catalog create successfully");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
