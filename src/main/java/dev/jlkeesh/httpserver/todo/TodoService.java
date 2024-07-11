package dev.jlkeesh.httpserver.todo;

import dev.jlkeesh.httpserver.todo.dto.TodoCreateDto;
import dev.jlkeesh.httpserver.todo.dto.TodoUpdateDto;

import java.util.List;

public class TodoService {
    private final TodoDAO todoDAO;

    public TodoService() {
        this.todoDAO = new TodoDAO();
    }

    public Todo create(TodoCreateDto dto) {
        Todo todo = new Todo();
        todo.setTitle(dto.title());
        todo.setDescription(dto.description());
        todo.setUserId(dto.userId());
        todo.setPriority(dto.priority());
        return todoDAO.save(todo);
    }

    public void update(TodoUpdateDto dto) {
        Todo todo = todoDAO.findById(dto.id())
                .orElseThrow(() -> new IllegalStateException("todo not found:" + dto.id()));
        // update logic;
    }

    public Todo getById(Long id) {
        return todoDAO.findById(id)
                .orElseThrow(() -> new IllegalStateException("todo not found:" + id));
    }

    public List<Todo> getAll() {
        return todoDAO.findAll();
    }
}
