package com.sit.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.sit.entity.CitizenPlan;
import com.sit.repo.CitizenPlanRepository;
import com.sit.request.SearchRequest;
import com.sit.util.EmailUtil;
import com.sit.util.Excelgenerator;
import com.sit.util.PdfGenerator;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class ReportServiceImpl implements ReportService{

	@Autowired
	private CitizenPlanRepository repo;
	
	@Autowired
	private Excelgenerator excelGenerator;
	
	@Autowired
	private PdfGenerator pdfGenerator;

	@Autowired
	private EmailUtil emailUtils;
	
	@Override
	public List<String> getPlanNames() {
		List<String> l=repo.getPlanNames();
		//		System.out.println("---------------------------------------------------");
		//		for (String status : l) {
		//	        System.out.println(status);  // Print each element on a new line
		//	    }
		//		System.out.println("---------------------------------------------------");
		return l;
	}

	@Override
	public List<String> getPlanStatus() {
		List<String> l = repo.getPlanStatus();
		//	    System.out.println("---------------------------------------------------");
		//	    for (String status : l) {
		//	        System.out.println(status);  // Print each element on a new line
		//	    }
		//	    System.out.println("---------------------------------------------------");
		return l;
	}


	@Override
	public List<CitizenPlan> search(SearchRequest req) {
	  
		CitizenPlan e = new CitizenPlan();

	    // Set only non-empty fields
	    if (null!=req.getPlanName() && !"".equals(req.getPlanName())) {
	        e.setPlanName(req.getPlanName());
	    }
	    if (null!=req.getPlanStatus() && !"".equals(req.getPlanStatus())) {
	        e.setPlanStatus(req.getPlanStatus());
	    }
	    if (null!=req.getGender()&& !"".equals(req.getGender())) {
	        e.setGender(req.getGender());
	    }
	    if (null!=req.getStartDate() && !"".equals(req.getStartDate())) {
	    	String sd=req.getStartDate();
	    	DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    	//String to LocalDate
	    	LocalDate lDate=LocalDate.parse(sd,formatter);
	        e.setPlanStartDate(lDate);
	    }
	    if (null!=req.getEndDate() && !"".equals(req.getEndDate())) {
	    	String ed=req.getEndDate();
	    	DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    	//String to LocalDate
	    	LocalDate lDate=LocalDate.parse(ed,formatter);
	        e.setPlanEndDate(lDate);
	    }

	    return repo.findAll(Example.of(e));
	}


	@Override
	public boolean exportExcel(HttpServletResponse res) throws IOException {
   
		//File f=new File("Plans.xls");
		File f = new File(System.getProperty("user.home") + "/Downloads/Plans.xls"); 

		
		List<CitizenPlan> plan=repo.findAll();
		excelGenerator.generateExcel(res, plan,f);
		
		String sub="Spring Boot App Test Mail";
		String body="<h1>Reports Data</h1><br> Here is the Reports Data that is added in the below attachment";
		String to="sairamganta1@gmail.com";
		
		emailUtils.sendEmail(sub, body, to,f);
		f.delete();
		
		return true;
	}
	
	@Override
	public boolean exportPdf(HttpServletResponse res) throws Exception {
		
		File f = new File(System.getProperty("user.home") + "/Downloads/Plans.pdf"); 
		List<CitizenPlan> plan=repo.findAll();
	    pdfGenerator.generetePdf(res, plan,f);
	    

		String sub="Spring Boot App Test Mail";
		String body="<h1>Reports Data</h1><br> Here is the Reports Data that is added in the below attachment";
		String to="sairamganta1@gmail.com";
		
		emailUtils.sendEmail(sub, body, to,f);
		f.delete();
		
	    
	    return true;
	}



}
