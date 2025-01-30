package com.sit.service;

import java.io.IOException;
import java.util.List;

import com.sit.entity.CitizenPlan;
import com.sit.request.SearchRequest;

import jakarta.servlet.http.HttpServletResponse;

public interface ReportService{

	public List<String> getPlanNames();
	
	public List<String> getPlanStatus();
	
	public List<CitizenPlan> search(SearchRequest req);
	
	public boolean exportExcel(HttpServletResponse res) throws IOException;
	
	public boolean exportPdf(HttpServletResponse res) throws Exception;
}
