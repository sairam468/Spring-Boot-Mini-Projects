package com.sit.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import com.sit.entity.CitizenPlan;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class Excelgenerator {

	public void generateExcel(HttpServletResponse res,List<CitizenPlan> records,File f) throws IOException {
		Workbook workBook= new HSSFWorkbook();
        Sheet sheet=workBook.createSheet("Plans-Data");
		Row headerRow=sheet.createRow(0);
		
		headerRow.createCell(0).setCellValue("ID");
		headerRow.createCell(1).setCellValue("CITIZEN NAME");
		headerRow.createCell(2).setCellValue("PLAN NAME");
		headerRow.createCell(3).setCellValue("PLAN STATUS");
		headerRow.createCell(4).setCellValue("PLAN START DATE");
		headerRow.createCell(5).setCellValue("PLAN END DATE");
		headerRow.createCell(6).setCellValue("BENEFIT AMT");
		
		int dataRowIndex=1;
		
		for(CitizenPlan plan: records) {
			Row dataRow=sheet.createRow(dataRowIndex);
			dataRow.createCell(0).setCellValue(plan.getId());
			dataRow.createCell(1).setCellValue(plan.getName());
			dataRow.createCell(2).setCellValue(plan.getPlanName());
			dataRow.createCell(3).setCellValue(plan.getPlanStatus());
			if (null != plan.getPlanStartDate()) {
				dataRow.createCell(4).setCellValue(plan.getPlanStartDate()+"");
			} else {
			    dataRow.createCell(4).setCellValue("N/A");  
			}
			if (null != plan.getPlanEndDate()) {
				dataRow.createCell(5).setCellValue(plan.getPlanEndDate()+"");
			} else {
			    dataRow.createCell(5).setCellValue("N/A");  
			}
			if (null != plan.getBenefitAmt()) {
			    dataRow.createCell(6).setCellValue(plan.getBenefitAmt());
			} else {
			    dataRow.createCell(6).setCellValue("N/A");  // Set string value for null
			}

			
			dataRowIndex++;
		}
		FileOutputStream fos=new FileOutputStream(f);
		workBook.write(fos);	
		fos.close();
		
		ServletOutputStream outputStream = res.getOutputStream();
		workBook.write(outputStream);
		
		workBook.close();
	}
}
