package com.softserve.itacademy.todolist.dto.task;

import com.softserve.itacademy.todolist.model.Priority;
import com.softserve.itacademy.todolist.model.State;
import com.softserve.itacademy.todolist.validator.constraint.Title;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
public class TaskUpdateRequest {
    @Title
    private String name;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private State state;
}
