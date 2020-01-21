package com.edveloso.controller;
import java.net.URI;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.edveloso.model.Todo;
import com.edveloso.repository.TodoRepository;

@RestController
@RequestMapping("/todos")
@CrossOrigin(origins = "")
public class TodoResource {

	private TodoRepository todoRepository;

	public TodoResource(TodoRepository todoRepository) {
		super();
		this.todoRepository = todoRepository;
	}
	
	@GetMapping
	public ResponseEntity<Iterable<Todo>> allTodos(){
		return ResponseEntity.ok(todoRepository.findAll());
	}
	
	
	@GetMapping("{id}")
	@ResponseBody
	public ResponseEntity<?> getTodoById(@PathVariable String id){
		Optional<Todo> todo = todoRepository.findById(Integer.valueOf(id));
		if(!todo.isPresent()) {
			return ResponseEntity.notFound()
					.build();
		}
		
		return   ResponseEntity.ok(todo.get());
	}
	
	
	@RequestMapping(value= "{id}", method=  RequestMethod.PUT)
	public ResponseEntity<?> editTodo(@Valid @RequestBody Todo todo, Errors errors){
		if(errors.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}
		Todo result = todoRepository.save(todo);
		return ResponseEntity.ok(result);
	}
	
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteTodo(@PathVariable String id){
		if(null == id) {
			return ResponseEntity.badRequest().build();
		}
		Optional<Todo> result = todoRepository.findById(Integer.valueOf(id));
		todoRepository.delete(result.get());
		
		return ResponseEntity.notFound().build();
	}
	
	
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT} )
	public ResponseEntity<?> createTodo(@Valid @RequestBody Todo todo, Errors errors  ){
		if(errors.hasErrors()) {
			return ResponseEntity.badRequest().body("Validation failed");
		}
		
		Todo result = todoRepository.save(todo);
		URI location =   ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
		.buildAndExpand(result.getId()).toUri();
		
		
		return  ResponseEntity.created(location).build();
	}

	
	
	
	
	
}





