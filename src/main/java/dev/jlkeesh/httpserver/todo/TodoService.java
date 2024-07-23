package dev.jlkeesh.httpserver.todo;

import dev.jlkeesh.httpserver.exception.NotFoundException;
import dev.jlkeesh.httpserver.todo.dto.TodoCreateDto;
import dev.jlkeesh.httpserver.todo.dto.TodoUpdateDto;

import java.util.List;
import java.util.Objects;

public class TodoService {
    private final TodoDAO todoDAO;

    public TodoService(TodoDAO todoDAO) {
        this.todoDAO = todoDAO;
    }

    public Todo create(TodoCreateDto dto) {
        Todo todo = new Todo();
        todo.setTitle(dto.title());
        todo.setDescription(dto.description());
        todo.setUserId(dto.userId());
        todo.setPriority(dto.priority());
        return todoDAO.save(todo);
    }

    public Todo update(TodoUpdateDto dto) {
        Todo todo = todoDAO.findById(dto.id())
                .orElseThrow(() -> new NotFoundException("todo not found: " + dto.id()));
        if (Objects.nonNull(dto.title())) {
            todo.setTitle(dto.title());
        }
        if (Objects.nonNull(dto.description())) {
            todo.setDescription(dto.description());
        }
        if (Objects.nonNull(dto.priority())) {
            todo.setPriority(dto.priority());
        }
        if (Objects.nonNull(dto.completed())) {
            todo.setDone(dto.completed());
        }
        return todoDAO.save(todo);
    }

    public Todo getById(Long id) {
        return todoDAO.findById(id)
                .orElseThrow(() -> new NotFoundException("todo not found: " + id));
    }

    public List<Todo> getAll() {
        return todoDAO.findAll();
    }

    public void deleteById(Long id) {
        todoDAO.deleteById(id);
    }
}
