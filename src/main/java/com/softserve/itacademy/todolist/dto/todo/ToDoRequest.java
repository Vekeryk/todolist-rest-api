package com.softserve.itacademy.todolist.dto.todo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class ToDoRequest {
    @NotBlank(message = "The 'title' cannot be empty")
    String title;
}
