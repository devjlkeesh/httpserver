package dev.jlkeesh.httpserver.todo.dto;

public record TodoUpdateDto(Long id, String title, String description, Boolean completed) {
}
