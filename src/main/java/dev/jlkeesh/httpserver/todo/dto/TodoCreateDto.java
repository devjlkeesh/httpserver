package dev.jlkeesh.httpserver.todo.dto;

import dev.jlkeesh.httpserver.todo.Priority;

public record TodoCreateDto(String title, String description, Long userId, Priority priority) {
}
