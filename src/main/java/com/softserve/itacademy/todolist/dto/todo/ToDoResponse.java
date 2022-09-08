package com.softserve.itacademy.todolist.dto.todo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ToDoResponse {
    Long id;
    String title;
    LocalDateTime createdAt;
}
