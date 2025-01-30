package com.sit.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.sit.entity.CitizenPlan;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class PdfGenerator {

	public void generetePdf(HttpServletResponse res,List<CitizenPlan> records,File f) throws Exception, IOException {
		// Create a new Document with A4 page size
	    Document doc = new Document(PageSize.A4);

	    // Create a PdfWriter instance to write the document to the response output stream
	    PdfWriter.getInstance(doc, res.getOutputStream());

	    PdfWriter.getInstance(doc, new FileOutputStream(f));
	    // Open the document to add content
	    doc.open();

	    // Set a custom font for the document
	    Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
	    Font tableHeaderFont = new Font(Font.HELVETICA, 12, Font.BOLD);
	    Font tableCellFont = new Font(Font.HELVETICA, 10);

	    // Add a title paragraph to the document with custom font
	    Paragraph p = new Paragraph("Plans Info", titleFont);
	    p.setAlignment(Element.ALIGN_CENTER);  // Center align the title
	    doc.add(p);

	    // Add some spacing
	    doc.add(new Paragraph(" "));
	    
	    // Create a PdfPTable with 6 columns
	    PdfPTable table = new PdfPTable(6);

	    // Set table width percentages (for proper column distribution)
	    table.setWidths(new float[]{1f, 2f, 2f, 2f, 2f, 2f});
	    
	    // Add table headers with custom font
	    table.addCell(new PdfPCell(new Phrase("ID", tableHeaderFont)));
	    table.addCell(new PdfPCell(new Phrase("Name", tableHeaderFont)));
	    table.addCell(new PdfPCell(new Phrase("Plan Name", tableHeaderFont)));
	    table.addCell(new PdfPCell(new Phrase("Plan Status", tableHeaderFont)));
	    table.addCell(new PdfPCell(new Phrase("Start Date", tableHeaderFont)));
	    table.addCell(new PdfPCell(new Phrase("End Date", tableHeaderFont)));

	    for (CitizenPlan plan : records) {
	        // Add table rows for each record with custom font for cells
	        table.addCell(new PdfPCell(new Phrase(String.valueOf(plan.getId()), tableCellFont)));
	        table.addCell(new PdfPCell(new Phrase(plan.getName(), tableCellFont)));
	        table.addCell(new PdfPCell(new Phrase(plan.getPlanName(), tableCellFont)));
	        table.addCell(new PdfPCell(new Phrase(plan.getPlanStatus(), tableCellFont)));
	        table.addCell(new PdfPCell(new Phrase(plan.getPlanStartDate() != null ? plan.getPlanStartDate().toString() : "N/A", tableCellFont)));
	        table.addCell(new PdfPCell(new Phrase(plan.getPlanEndDate() != null ? plan.getPlanEndDate().toString() : "N/A", tableCellFont)));
	    }

	    // Add the table to the document
	    doc.add(table);

	    // Add some more spacing after the table
	    doc.add(new Paragraph(" "));
	    
	    // Close the document after adding content
	    doc.close();

	}
}
