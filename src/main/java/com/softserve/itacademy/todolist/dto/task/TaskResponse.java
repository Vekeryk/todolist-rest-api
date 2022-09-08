package com.softserve.itacademy.todolist.dto.task;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskResponse {
    Long id;
    String name;
    String priority;
    String state;
}
