package com.todo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.model.Todo;

// JpaRepository<Todo, Integer> - here Todo is bean and Integer is type of primary key
public interface TodosRepository extends JpaRepository<Todo, Integer> {
	// use the functionality those which are not given by repo directly like save
	// findById etc...

	List<Todo> findByUser(String user); // findBy(Field name, it can be case sensitive like findByuser)

	List<Todo> findByDescriptionAndUser(String user, String description);
}
