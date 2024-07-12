package dev.jlkeesh.httpserver.todo.dto;

import dev.jlkeesh.httpserver.todo.Priority;

public record TodoUpdateDto(Long id, String title, String description, Priority priority, Boolean completed) {
}
