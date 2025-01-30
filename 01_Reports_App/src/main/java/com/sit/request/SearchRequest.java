package com.sit.request;

import lombok.Data;

@Data
public class SearchRequest {

	private String planName;
	private String planStatus;
	private String gender;
	//As HTML Format is "dd-mm-yyyy" and LocalDate Formate is "yyyy-mm-dd"
	//@DateTimeFormat(pattern="DD-MM-YYYY")
	private String startDate;
	//@DateTimeFormat(pattern="DD-MM-YYYY") 
	private String endDate;
}
