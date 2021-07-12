package com.khauminhduy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.khauminhduy.data.QuoteItem;

public class Quotation {
	
	public void generateQuotation(List<QuoteItem> quoteData, boolean isDomestic) {
		try (XSSFWorkbook workbook = new XSSFWorkbook();
				OutputStream outputStream = new FileOutputStream("src/main/resources/quotaion.xlsx");){
			XSSFSheet sheet = workbook.createSheet("Sheet 1");
			addCompanyDetails(workbook, sheet);
			workbook.write(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void addCompanyDetails(XSSFWorkbook workbook, XSSFSheet sheet) {
		XSSFRow titleRow = sheet.createRow(0);
		XSSFCell titleCell = titleRow.createCell(4);
		titleCell.setCellValue("Product Quote");
		
		XSSFRow detailRow1 = sheet.createRow(1);
		XSSFCell contactNO = detailRow1.createCell(2);
		contactNO.setCellValue("Contact NO: XXXXXXXXXX");
		XSSFCell companyName = detailRow1.createCell(10);
		companyName.setCellValue("The Furniture Store");
		
		XSSFRow detailRow2 = sheet.createRow(2);
		XSSFCell email = detailRow2.createCell(2);
		email.setCellValue("Email: contact@furniture.com");
		
	}

	private void addQuoteDetails(XSSFWorkbook workbook, XSSFSheet sheet, List<QuoteItem> quoteData) {

	}

	private void addInfo(XSSFSheet sheet, boolean isDomestic) {

	}
	
	public void readQuotation() {
		try (InputStream inputStream = new FileInputStream("src/main/resources/quotaion.xlsx");
				XSSFWorkbook workbook = new XSSFWorkbook(inputStream);){
			XSSFSheet sheet = workbook.getSheet("Sheet 1");
			XSSFRow row = sheet.getRow(0);
			XSSFCell cell = row.getCell(4);
			
			Iterator<Cell> cellIterator = row.cellIterator();
			cellIterator.forEachRemaining(e -> {
				System.out.println(e);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
