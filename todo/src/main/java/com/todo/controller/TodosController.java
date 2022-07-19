package com.todo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.todo.dto.TodoDto;
import com.todo.exceptions.ResourceNotFoundException;
import com.todo.services.TodosService;

/*
 * After adding developer tool dependency in each save the server auto restart
 */

@RestController
//this will append before with all the below url (like-  https://localhost:8080/api/todos/user)
//for different version of controller with same type we can create url (like -https://localhost:8080/api/v1/todos/user )
@RequestMapping("/api/todos")
public class TodosController {

	@Autowired
	private TodosService todosService;

	// GET Method to get all todos
	// https://localhost:8080/todos
	@GetMapping()
	public List<TodoDto> getAllTodo() {
		List<TodoDto> allTodos = todosService.getAllTodo();

		for (TodoDto todo : allTodos) {
			int todoId = todo.getId();

			Link selfLink = WebMvcLinkBuilder.linkTo(TodosController.class).slash(todoId).withSelfRel(); // create the
			todo.add(selfLink); // add function is coming from Hateoas (Richardson maturity level)
		}
		return allTodos;
	}

	// GET Method to get all todos related to particular user name
	// https://localhost:8080/todos/user/{name}
	@GetMapping("/user/{name}")
	public List<TodoDto> getAllTodoByUser(@PathVariable String name) {

		List<TodoDto> userTodos = todosService.getAllTodoByUser(name);

		if (userTodos.isEmpty()) {
			// throw new ResourceNotFoundException("Resource Not Found");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo Not Found",
					new ResourceNotFoundException("Resource Not Found"));

			// throw new ResponseStatusException(HttpStatus.CONFLICT, "Todo Conflict");
		}

		return userTodos;
	}

	// GET Method to get all todos based on id
	// https://localhost:8080/todos/{id}
	@GetMapping("/{id}")
	public TodoDto getAllTodoById(@PathVariable int id) {
		TodoDto todo = todosService.getAllTodoById(id);

		if (todo == null) {
			throw new ResourceNotFoundException("Developer tool enable!"); // chnaged the mesage due to test the dev
																			// tool feature (check depends and search
																			// for developer tool)
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo Not Found",
//					new ResourceNotFoundException("Resource Not Found")); // diff way of giving error handling..
		}

		return todo;
	}

	// POST Method to create a new todo
	// if successful creation should return 201 HTTP created
	// location should be shared in the response header
	@PostMapping("/addtodo")
	public ResponseEntity<TodoDto> addTodo(@Valid @RequestBody TodoDto todo) {
		TodoDto newTodo = todosService.addTodo(todo); // default will be 200

		if (newTodo == null) {
			return ResponseEntity.noContent().build(); // it will return specific to data not not able to create: error
														// code 204
		}

//		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newTodo.getId())
//				.toUri(); // this way in Header
		// location will come as get service URL which can re-direct
		// after successful creation of new user

		// return ResponseEntity.created(location).build(); // (It is just a wrapper) It
		// will return what ever object you
		// want and also HttpStatus
		return new ResponseEntity<TodoDto>(newTodo, HttpStatus.CREATED); // 201
	}

	// PUT - update the existing todos based on the user name shared in the path
	// variable
	// URL - localhost:8080/todos/user/{name}/{id}
	// if the name and the todo is not related it should throw 404 http status code
	@PutMapping("/user/{name}/{id}")
	public ResponseEntity<TodoDto> updateTodo(@PathVariable String name, @PathVariable int id,
			@Valid @RequestBody TodoDto todo) {

		TodoDto updateTodo = todosService.updateTodo(name, id, todo);
		if (updateTodo == null) {
			// throw new ResourceNotFoundException("No todo for given Name and id");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo Not Found"); // diff way of error
																						// handling..
		}

		return new ResponseEntity<TodoDto>(updateTodo, HttpStatus.OK);// default (OK) will be 200
	}

	// DELETE - delete the resource based on id
	// url - localhost:8080/todos/{id}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTodo(@PathVariable int id) {

		boolean result = todosService.deleteTodo(id);

		if (!result) {
			throw new ResourceNotFoundException("Resource not found for given id " + id);
		}

		return new ResponseEntity<String>("Successfully Deleted Todo", HttpStatus.OK);
	}

	/*
	 * Custom message if any HttpStatus.BAD_REQUEST comes in any service
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<String, String>();

		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		return errors;
	}
}
