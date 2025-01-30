package com.sit.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.sit.entity.CitizenPlan;
import com.sit.request.SearchRequest;
import com.sit.service.ReportService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ReportController {

	@Autowired
	private ReportService service;
	
	@GetMapping("/excel")
	public void excelExport(HttpServletResponse res) throws IOException {
		
		res.setContentType("application/octet-stream");
		
		res.addHeader("Content-Disposition", "attachment;filename=plans.xls");
		
		service.exportExcel(res);
	}
	
	@GetMapping("/pdf")
	public void pdfExport(HttpServletResponse res) throws Exception {
		
		res.setContentType("application/pdf");
		
		res.addHeader("Content-Disposition", "attachment;filename=plans.pdf");
		
		service.exportPdf(res);
	}
	
	@PostMapping("/search")
	public String handleSerach(@ModelAttribute("search")SearchRequest req,Model model) {
		
		List<CitizenPlan> plans=service.search(req);
		model.addAttribute("plans",plans);
		
		System.out.println(req);
		init(model);
		return "index";
	}
	
	@GetMapping("/")
	public String indexPage(Model model) {
		model.addAttribute("search",new SearchRequest());
		
		init(model);
		
		return "index";
	}
//	// New GET method to reset the form
//    @GetMapping("/reset")
//    public String resetForm(Model model) {
//        model.addAttribute("search", new SearchRequest());  // Resetting the form by initializing it with an empty object
//        init(model);
//        return "index";
//    }

	private void init(Model model) {
		model.addAttribute("names",service.getPlanNames());
		model.addAttribute("status",service.getPlanStatus());
		//model.addAttribute("search",new SearchRequest());
	}
}
