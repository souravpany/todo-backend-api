package com.todo.services;

import java.util.List;

import com.todo.dto.TodoDto;

public interface TodosService {

	List<TodoDto> getAllTodo();

	List<TodoDto> getAllTodoByUser(String user);

	TodoDto getAllTodoById(int id);

	TodoDto addTodo(TodoDto todo);

	TodoDto updateTodo(String name, int id, TodoDto todo);

	boolean deleteTodo(int id);

}
