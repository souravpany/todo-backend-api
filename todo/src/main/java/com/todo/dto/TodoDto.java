package com.todo.dto;

import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;



@Data
public class TodoDto extends RepresentationModel<TodoDto>{

	private int id;

	private String user;

	private String description;

	private Date targetDate;
	
	private String status;
	

}
