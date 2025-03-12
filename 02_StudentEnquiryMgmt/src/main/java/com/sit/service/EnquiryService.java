 package com.sit.service;

import java.util.List;

import com.sit.binding.DashBoardResponse;
import com.sit.binding.EnquiryForm;
import com.sit.binding.EnquirySerachCriteria;
import com.sit.entity.StudentEnqEntity;
import com.sit.repo.StudentEnqrepo;

public interface EnquiryService {
	
	public DashBoardResponse getDashboardData(Integer userId);

	public List<String> getCourseName();
	
	public List<String> getenqStatus();
	
	public boolean addEnquiry(EnquiryForm form);
	
	public boolean editEnquiry( StudentEnqEntity form);
	
	public List<StudentEnqEntity> getEnquiry(Integer eqqID);
	
	public  List<StudentEnqEntity> getFilteredEnquiries(EnquirySerachCriteria criteria,Integer userID);
	
	public StudentEnqEntity getStuEnquiry(Integer eqqID);
	
	
} 
