package com.edveloso.repository;

import org.springframework.data.repository.CrudRepository;

import com.edveloso.model.Todo;

public interface TodoRepository extends CrudRepository<Todo, Integer> {

}
