package dev.jlkeesh.httpserver.todo;

import dev.jlkeesh.httpserver.annotations.Domain;
import lombok.*;
import lombok.extern.java.Log;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Domain
public class Todo {
    private Long id;
    private String title;
    private String description;
    private Long userId;
    private boolean done;
    private Priority priority;
    private Date createdAt;
}
