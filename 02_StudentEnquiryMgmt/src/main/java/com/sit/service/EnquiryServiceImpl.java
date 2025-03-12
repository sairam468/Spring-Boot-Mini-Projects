package com.sit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sit.binding.DashBoardResponse;
import com.sit.binding.EnquiryForm;
import com.sit.binding.EnquirySerachCriteria;
import com.sit.entity.CourseEntity;
import com.sit.entity.EnqStatusEntity;
import com.sit.entity.StudentEnqEntity;
import com.sit.entity.UserDetailsEntity;
import com.sit.repo.CourseRepo;
import com.sit.repo.EnqStatusRepo;
import com.sit.repo.StudentEnqrepo;
import com.sit.repo.UserDetailsRepo;

import jakarta.servlet.http.HttpSession;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private UserDetailsRepo repo;

	@Autowired
	private CourseRepo courseRepo;

	@Autowired
	private EnqStatusRepo statusRepo;


	@Autowired
	private StudentEnqrepo stuRepo;

	@Autowired
	private HttpSession ses;


	@Override
	public DashBoardResponse getDashboardData(Integer userId) {

		DashBoardResponse res=new DashBoardResponse();

		Optional<UserDetailsEntity> findById = repo.findById(userId);

		if(findById.isPresent()) {
			UserDetailsEntity userDetailsEntity = findById.get();
			List<StudentEnqEntity> enquries = userDetailsEntity.getEnquries();
			Integer total = enquries.size();

			List<StudentEnqEntity> enrolled = enquries.stream()
					.filter(e->e.getEnquiryStatus().equalsIgnoreCase("ENROLLED"))
					.collect(Collectors.toList());
			Integer enrolledSize=enrolled.size();

//			System.out.println("statuss");
//			enquries.stream().forEach(e->System.out.println(e.getEnquiryStatus()));

			List<StudentEnqEntity> lost = enquries.stream()
					.filter(e->e.getEnquiryStatus().equalsIgnoreCase("LOST"))
					.collect(Collectors.toList());
			Integer lostSize=lost.size();

			res.setTotEnqCnt(total);
			res.setEnrolledCnt(enrolledSize);
			res.setLostCnt(lostSize);
		}

		return res;
	}


	@Override
	public List<String> getCourseName() {
		List<CourseEntity> all = courseRepo.findAll();
		List<String> names=new ArrayList<String>();
		for(CourseEntity c:all) {
			names.add(c.getCourseName());
		}

		return names;
	}

	@Override
	public List<String> getenqStatus() {
		System.out.println("EnquiryServiceImpl.getenqStatus()");
		List<EnqStatusEntity> all = statusRepo.findAll();
		List<String> names=new ArrayList<String>();
		for(EnqStatusEntity c:all) {
			names.add(c.getStatus());
		}
		return names;
	}

	@Override
	public List<StudentEnqEntity> getEnquiry(Integer eqqID) {
		Optional<UserDetailsEntity> byId = repo.findById(eqqID);
		

		if(byId.isPresent()) {
			UserDetailsEntity e=byId.get();
			List<StudentEnqEntity> enquires=e.getEnquries();
			return enquires;
		}

		return null;
	}
	
	@Override
	public StudentEnqEntity getStuEnquiry(Integer eqqID) {
		Optional<StudentEnqEntity> byId = stuRepo.findById(eqqID);
		System.out.println("EnquiryServiceImpl.getStuEnquiry()");
		
		if(byId.isPresent()) {
			StudentEnqEntity e=byId.get();
			return e;
		}

		return null;
	}

	@Override
	public boolean addEnquiry(EnquiryForm form) {

		StudentEnqEntity entity=new StudentEnqEntity();


		System.out.println("Number :: ======"+form.getStudentPhNo());

		Integer userId=(Integer) ses.getAttribute("userId");

		UserDetailsEntity userDetailsEntity = repo.findById(userId).get();

		System.out.println(form.getStuName()+" "+form.getEnqStatus());

		entity.setClassMode(form.getClassMode());

		entity.setCourseName(form.getCourseName());

		entity.setStudentName(form.getStuName());

		entity.setEnquiryStatus(form.getEnqStatus());

		entity.setPhoneNumber(form.getStudentPhNo());

		entity.setUser(userDetailsEntity);

		stuRepo.save(entity);

		return true;
	}

	@Override
	public boolean editEnquiry(StudentEnqEntity form) {
		
		System.out.println("stu form in service :: ======"+form);

		Integer userId=(Integer) ses.getAttribute("userId");

		UserDetailsEntity userDetailsEntity = repo.findById(userId).get();

//		System.out.println(form.getStuName()+" "+form.getEnqStatus());
//
//		entity.setClassMode(form.getClassMode());
//
//		entity.setCourseName(form.getCourseName());
//
//		entity.setStudentName(form.getStuName());
//
//		entity.setEnquiryStatus(form.getEnqStatus());
//
//		entity.setPhoneNumber(form.getStudentPhNo());
//
//		entity.setUser(userDetailsEntity);
//		System.out.println(entity);

		stuRepo.save(form);

		return true;
	}
	
	@Override
	public List<StudentEnqEntity> getFilteredEnquiries(EnquirySerachCriteria criteria,Integer userId) {
		//	        // Use a Specification (if using JPA) or custom filtering logic
		//	        return stuRepo.findAll((root, query, criteriaBuilder) -> {
		//	            List<Predicate> predicates = new ArrayList<>();
		//
		//	            if (criteria.getCourseName() != null && !criteria.getCourseName().isEmpty()) {
		//	                predicates.add(criteriaBuilder.equal(root.get("courseName"), criteria.getCourseName()));
		//	            }
		//	            if (criteria.getClassMode() != null && !criteria.getClassMode().isEmpty()) {
		//	                predicates.add(criteriaBuilder.equal(root.get("classMode"), criteria.getClassMode()));
		//	            }
		//	            if (criteria.getEnqStatus() != null && !criteria.getEnqStatus().isEmpty()) {
		//	                predicates.add(criteriaBuilder.equal(root.get("enquiryStatus"), criteria.getEnqStatus()));
		//	            }
		//
		//	            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		//	        });
		Optional<UserDetailsEntity> byId = repo.findById(userId);

		if(byId.isPresent()) {
			UserDetailsEntity ent=byId.get();
			List<StudentEnqEntity> enquires=ent.getEnquries();

			//filter logic
			if(null !=criteria.getCourseName() && !"".equals(criteria.getCourseName())) {

				enquires = enquires.stream().filter(e->e.getCourseName().equalsIgnoreCase(criteria.getCourseName())).collect(Collectors.toList());
			}
			if(null !=criteria.getEnqStatus() && !"".equals(criteria.getEnqStatus())) {

				enquires = enquires.stream().filter(e->e.getEnquiryStatus().equalsIgnoreCase(criteria.getEnqStatus())).collect(Collectors.toList());
			}
			
			if(null !=criteria.getClassMode() && !"".equals(criteria.getClassMode())) {

				enquires = enquires.stream().filter(e->e.getClassMode().equalsIgnoreCase(criteria.getClassMode())).collect(Collectors.toList());
			}
			return enquires;
		}

		return null;




	}



	

}
