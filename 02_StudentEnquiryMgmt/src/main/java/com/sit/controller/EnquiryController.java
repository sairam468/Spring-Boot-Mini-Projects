package com.sit.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sit.binding.DashBoardResponse;
import com.sit.binding.EnquiryForm;
import com.sit.binding.EnquirySerachCriteria;
import com.sit.entity.StudentEnqEntity;
import com.sit.service.EnquiryService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {

	@Autowired
	private EnquiryService service;

	@Autowired
	private HttpSession session;


	@GetMapping("/dashboard")
	public String dashBoardPage(Map<String,Object> m) {

		Integer id=(Integer)session.getAttribute("userId");

		//System.out.println("User id :"+id);

		DashBoardResponse dashboardData = service.getDashboardData(id);

		m.put("dashboardData", dashboardData);

		return "dashboard";
	}

	@GetMapping("/enquiry")
	public String addEnquiryPage(Model m) {

		//1.Get Courses for dropdown
		List<String> courseName = service.getCourseName();

		//2.Get Enq Status for dropdown
		List<String> getenqStatus = service.getenqStatus();

		//3.Create Binding Class obj
		EnquiryForm form =new EnquiryForm();

		//4.Set data in model obj
		m.addAttribute("courses", courseName);
		m.addAttribute("enqstatus", getenqStatus);
		m.addAttribute("form", form);

		return "add-enquiry";
	}

	@GetMapping("/edit-enquiry")
	public String editEnquiryPage(@RequestParam(value = "id", required = false) Integer id, Model m) {
		System.out.println("EnquiryController.editEnquiryPage()-Start");

		// Get Courses and Enquiry Status for dropdowns
		List<String> courseName = service.getCourseName();
		List<String> getenqStatus = service.getenqStatus();

		m.addAttribute("courses", courseName);
		m.addAttribute("enqstatus", getenqStatus);

		if (id != null) {
			StudentEnqEntity enquiry = service.getStuEnquiry(id);
			if (enquiry != null) {
				m.addAttribute("enquiry", enquiry);
				m.addAttribute("id", id);
				System.out.println("id : "+id+" enquiry : "+enquiry);
				return "edit-enquiry"; 
			} else {
				m.addAttribute("error", "No enquiry found!");
			}
		}

		return "add-enquiry";
	}

	@PostMapping("/edit-enquiry")
	public String editEnquiry(@RequestParam(value = "id", required = false) Integer id,
	                          @ModelAttribute StudentEnqEntity form, RedirectAttributes redirectAttributes) {
	    System.out.println("EnquiryController.editEnquiry()-start");
	    System.out.println("Received Form Data: " + form);

	    if (id != null) {
	        // Fetch the existing record from the database
	        StudentEnqEntity existingEnquiry = service.getStuEnquiry(id);
	        if (existingEnquiry != null) {
	            // Update only the modified fields
	            existingEnquiry.setStudentName(form.getStudentName());
	            existingEnquiry.setPhoneNumber(form.getPhoneNumber());
	            existingEnquiry.setClassMode(form.getClassMode());
	            existingEnquiry.setCourseName(form.getCourseName());
	            existingEnquiry.setEnquiryStatus(form.getEnquiryStatus());

	            System.out.println("Existing Enquiry: " + existingEnquiry);

	            // Save the updated entity
	            boolean status = service.editEnquiry(existingEnquiry);
	            if (status) {
	                redirectAttributes.addFlashAttribute("succMsg", "Enquiry updated successfully!");
	            } else {
	                redirectAttributes.addFlashAttribute("errMsg", "Problem Occurred");
	            }
	            return "redirect:/dashboard"; // Redirect with Flash Attributes
	        }
	    }
	    return "redirect:/edit-enquiry?id=" + id;
	}


	@PostMapping("/addEnq")
	public String addEnquiry(@ModelAttribute  EnquiryForm form, Model m) {

		System.out.println("EnquiryController.addEnquiry()-Start");

		System.out.println("Received Form Data: " + form);

		boolean status = service.addEnquiry(form); // Call update method instead of add
		if (status) {
			m.addAttribute("succMsg", "Enquiry Updated Successfully");
		} else {
			m.addAttribute("errMsg", "Failed to Update Enquiry");
		}
		System.out.println("EnquiryController.addEnquiry()-End");
		return "redirect:/dashboard";
	}

	private void initForm(Model m) {
		List<String> courseName = service.getCourseName();

		List<String> getenqStatus = service.getenqStatus();

		EnquiryForm form=new EnquiryForm();

		m.addAttribute("courseNames",courseName);
		m.addAttribute("statusNames",getenqStatus);
		m.addAttribute("formObj",form);
	}


	@GetMapping("/enquires") 
	public String viewEnquiryPage(EnquirySerachCriteria criteria,Model m) {

		initForm(m);
		Integer id=(Integer)session.getAttribute("userId");

		List<StudentEnqEntity> enquiry = service.getEnquiry(id);
		//System.out.println("eee"+enquiry);
		m.addAttribute("enquiry",enquiry);
		//m.addAttribute("searchForm",new EnquirySerachCriteria());

		return "view-enquiry";
	}

	@GetMapping("/filter-enquiries")
	public String getFilteredEnqs(@RequestParam String cname,@RequestParam String status,@RequestParam String mode,Model m) {
		EnquirySerachCriteria criteria =new EnquirySerachCriteria();

		criteria.setCourseName(cname);
		criteria.setClassMode(mode);
		criteria.setEnqStatus(status);

		//System.out.println(criteria);
		Integer id=(Integer)session.getAttribute("userId");


		List<StudentEnqEntity> filteredEnquiries = service.getFilteredEnquiries(criteria,id);
		m.addAttribute("enquiries", filteredEnquiries);
		System.out.println("<<<<<"+filteredEnquiries);
		return "filteredtable";
	}



	@GetMapping("logout")
	public String logout() {
		session.invalidate();
		return "index";
	}
}
