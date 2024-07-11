package dev.jlkeesh.httpserver.todo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TodoDAOImplTest {
    TodoDAOImpl todoDAO;

    @BeforeEach
    void setUp() {
        todoDAO = new TodoDAOImpl();
    }

    @Test
    void findById() {
        Long notExistingID = 12L;
        Optional<Todo> todoOptional = todoDAO.findById(notExistingID);
        assertThat(todoOptional).isEmpty();
    }

    @Test
    void save() {
        Todo todo = new Todo();
        todo.setTitle("Title");
        todo.setDescription("Description");
        todo.setUserId(12L);
        todo.setPriority(Priority.MEDIUM);
        todoDAO.save(todo);
        assertThat(todoDAO.findAll()).hasSizeGreaterThanOrEqualTo(1);
    }

    @Test
    void deleteById() {
    }

    @Test
    void findAll() {
    }
}