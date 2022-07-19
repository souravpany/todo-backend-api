package com.todo.dto;

import java.util.ArrayList;
import java.util.List;

import com.todo.model.Todo;

public class TodoDtoMapper {

	public static Todo convertDtotoEntity(TodoDto todoDto) {
		Todo todo = new Todo();
		todo.setDescription(todoDto.getDescription());
		todo.setUser(todoDto.getUser());
		todo.setTargetDate(todoDto.getTargetDate());
		if (todoDto.getStatus() != null && todoDto.getStatus().equals("Completed")) {
			todo.setDone(true);
		} else {
			todo.setDone(false);
		}

		return todo;
	}

	public static TodoDto convertEntitytoDto(Todo todo) {
		TodoDto todoDto = new TodoDto();
		todoDto.setId(todo.getId());
		todoDto.setDescription(todo.getDescription());
		todoDto.setUser(todo.getUser());
		todoDto.setTargetDate(todo.getTargetDate());
		if (todo.isDone() == true) {
			todoDto.setStatus("Completed");
		} else {
			todoDto.setStatus("Not Completed");
		}

		return todoDto;
	}

	public static List<TodoDto> convertListEntityToDto(List<Todo> todos) {

		List<TodoDto> todoDtos = new ArrayList<>();

		for (Todo todo : todos) {
			TodoDto todoDto = convertEntitytoDto(todo);

			todoDtos.add(todoDto);
		}

		return todoDtos;
	}

}
