package com.sit.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name="AIT_USER_DTLS")
@Data
@ToString(exclude = "enquries") // Prevents infinite loop in toString()
public class UserDetailsEntity {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Integer userId;

	private String name;
	
	private String email;
	
	private String pwd;
	
	private Long phno;
	
	private String accStatus;
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<StudentEnqEntity> enquries;
	
	
	@Override
	public String toString() {
	    return "UserDetailsEntity{" +
	            "userId=" + userId +
	            ", name='" + name + '\'' +
	            ", email='" + email + '\'' +
	            ", phno=" + phno +
	            ", accStatus='" + accStatus + '\'' +
	            '}';
	}

	
}
