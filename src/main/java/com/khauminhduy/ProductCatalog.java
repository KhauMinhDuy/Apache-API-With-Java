package com.khauminhduy;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;

import com.khauminhduy.data.Product;

public class ProductCatalog {

	public void generateCatalog(List<Product> products) {
		try(PDDocument document = new PDDocument();) {
			PDPage firstPage = new PDPage(PDRectangle.A4);
			document.addPage(firstPage);
			
			try(PDPageContentStream content = new PDPageContentStream(document, firstPage, AppendMode.APPEND, false);) {
				content.beginText();
				content.setFont(PDType1Font.HELVETICA, 16);
				content.newLineAtOffset(30, 760);
				content.showText("FURNITURE - CHAIRS");
				content.endText();
				
				PDImageXObject image;
				float imageY = 525;
				float nameY = 725;
				
				content.setLeading(14.5f);
				
				for(Product product : products) {
					image = PDImageXObject.createFromFile(product.getImageFile(), document);
					content.drawImage(image, 50, imageY, image.getWidth(), image.getHeight());
					
					content.beginText();
					
					content.setFont(PDType1Font.HELVETICA, 12);
					content.setNonStrokingColor(Color.blue);
					content.newLineAtOffset(300, nameY);
					content.showText(product.getName());
					content.newLine();
					
					content.setNonStrokingColor(Color.black);
					for(String feature : product.getFeatures()) {
						content.showText(feature);
						content.newLine();
					}
					
					content.endText();
					
					content.addRect(300, imageY + 125, 50, 25);
//					content.setNonStrokingColor(Color.black);
//					content.fill();
					content.setStrokingColor(Color.red);
					content.stroke();
					
					content.beginText();
					
					content.setFont(PDType1Font.HELVETICA, 14);
//					content.setNonStrokingColor(Color.white);
					content.setNonStrokingColor(Color.black);
					content.newLineAtOffset(300 + 10, imageY + 133);
					content.showText("$" + Math.round(product.getPrice()));
					
					content.endText();
					
					imageY -= image.getHeight();
					nameY -= image.getHeight();
				}
				
			}
			
			document.save("src/main/resources/product_catalog.pdf");
			System.out.println("Product Catalog create successfully");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadAndModifyProductCatalog() {
		String path = "src/main/resources/product_catalog.pdf";
		File file = new File(path);
		try (PDDocument document = PDDocument.load(file);){
			document.addPage(new PDPage(PDRectangle.A4));
			
			document.save(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void extractContent() {
		String path = "src/main/resources/product_catalog.pdf";
		File file = new File(path);
		try (PDDocument document = PDDocument.load(file);){
			PDFTextStripper pdfTextStripper = new PDFTextStripper();
			String textContent = pdfTextStripper.getText(document);
			System.out.println(textContent);
			
			PDPage firstPage = document.getPage(0);
			PDResources resources = firstPage.getResources();
			int i = 1;
			for(COSName resource : resources.getXObjectNames()) {
				PDXObject xObject = resources.getXObject(resource);
				if(xObject instanceof PDImageXObject) {
					PDImageXObject image = (PDImageXObject)xObject;
					String pathImage = "src/main/resources/extract_images/" + "extracted_image_" + i + ".jpg";
					ImageIO.write(image.getImage(), "jpg", new File(pathImage));
					i++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void removePage() {
		String path = "src/main/resources/product_catalog.pdf";
		File file = new File(path);
		try (PDDocument document = PDDocument.load(file);){
			document.removePage(1);
			document.save(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
