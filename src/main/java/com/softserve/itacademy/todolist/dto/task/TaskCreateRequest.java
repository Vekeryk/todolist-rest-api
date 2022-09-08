package com.softserve.itacademy.todolist.dto.task;


import com.softserve.itacademy.todolist.model.Priority;
import com.softserve.itacademy.todolist.validator.constraint.Title;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class TaskCreateRequest {
    @Title
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Priority is required")
    @Enumerated(EnumType.STRING)
    private Priority priority;
}
