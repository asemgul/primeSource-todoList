package com.example.todo.repos;

import com.example.todo.domain.Todo;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepo extends CrudRepository<Todo, Integer> {
}
