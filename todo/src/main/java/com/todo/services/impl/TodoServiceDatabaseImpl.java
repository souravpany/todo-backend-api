package com.todo.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.dto.TodoDto;
import com.todo.dto.TodoDtoMapper;
import com.todo.model.Todo;
import com.todo.repositories.TodosRepository;
import com.todo.services.TodosService;

@Service
public class TodoServiceDatabaseImpl implements TodosService {

	@Autowired
	private TodosRepository todosRepository;

	@Override
	public List<TodoDto> getAllTodo() {

		return TodoDtoMapper.convertListEntityToDto(todosRepository.findAll());
	}

	@Override
	public List<TodoDto> getAllTodoByUser(String user) {

		return TodoDtoMapper.convertListEntityToDto(todosRepository.findByUser(user));
	}

	@Override
	public TodoDto getAllTodoById(int id) {
		Optional<Todo> todo = todosRepository.findById(id);
		if (todo.isPresent()) {
			return TodoDtoMapper.convertEntitytoDto(todo.get());
		}
		return null;
	}

	@Override
	public TodoDto addTodo(TodoDto todoDto) {

		Todo newCreateTodo = todosRepository.save(TodoDtoMapper.convertDtotoEntity(todoDto));
		return TodoDtoMapper.convertEntitytoDto(newCreateTodo);
	}

	@Override
	public TodoDto updateTodo(String name, int id, TodoDto todoDto) {
		Optional<Todo> existingTodo = todosRepository.findById(id);

		if (existingTodo != null && existingTodo.get().getUser().equals(name)) {

			Todo updateTodo = existingTodo.get();

			updateTodo.setDescription(todoDto.getDescription());
			updateTodo.setTargetDate(todoDto.getTargetDate());
			if (todoDto.getStatus() != null && todoDto.getStatus().equals("Completed")) {
				updateTodo.setDone(true);
			} else {
				updateTodo.setDone(false);
			}

			// save/push to db
			Todo updatedTodo = todosRepository.save(updateTodo);
			return TodoDtoMapper.convertEntitytoDto(updatedTodo);

		}

		return null;
	}

	@Override
	public boolean deleteTodo(int id) {
		Optional<Todo> existingTodo = todosRepository.findById(id);

		if (existingTodo.isPresent()) {
			todosRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
