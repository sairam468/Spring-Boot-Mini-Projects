package com.sit.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="USER_TBL")
@Setter
@Getter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String fName;

	private String lName;

	private String email;

	private String pwd;

	@OneToMany(mappedBy ="user")
	private List<Post> posts;


	// Establish One-to-Many relationship with Comment
	@OneToMany(mappedBy = "user")
	private List<Comment> comments;

}
