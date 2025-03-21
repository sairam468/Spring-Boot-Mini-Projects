package com.sit.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="COMMENT_TBL")
@Setter
@Getter
public class Comment {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String eMail;

	@Lob
	private String content;

	@CreationTimestamp
	private LocalDate createdOn;

	@ManyToOne
	@JoinColumn(name="post_id")
	private Post post;

	// Establish Many-to-One relationship with User
	@ManyToOne
	@JoinColumn(name = "user_id") // Foreign key column in COMMENT_TBL
	private User user;



}
