package com.todo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity // need to add after installing JPA
@Table(name = "todos") // Specifying the table name as todo
public class Todo  { // For Hateoas compatible(richardson maturity level-3) extending
														// from RepresentationModel
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // for id auto increment
	private int id;

	@NotBlank(message = "user is required") // validation annotation
	@Column(name = "username", nullable = false)
	private String user;
	@Size(min = 9, max = 20, message = "Length should be more than 9 and less than 20 character")
	@Column(nullable = false) 
	private String description;
	//@JsonFormat(pattern="dd-MM-yyyy") // validation annotation
	@Column
	private Date targetDate;
	@Column
	private boolean isDone;


}
