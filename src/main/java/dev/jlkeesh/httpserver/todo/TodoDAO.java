package dev.jlkeesh.httpserver.todo;

import java.util.List;
import java.util.Optional;

public interface TodoDAO {
    Optional<Todo> findById(Long id);

    Todo save(Todo todo);

    void deleteById(Long id);

    List<Todo> findAll();
}
