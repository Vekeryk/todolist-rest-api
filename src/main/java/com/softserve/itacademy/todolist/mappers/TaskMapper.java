package com.softserve.itacademy.todolist.mappers;


import com.softserve.itacademy.todolist.dto.task.TaskCreateRequest;
import com.softserve.itacademy.todolist.dto.task.TaskResponse;
import com.softserve.itacademy.todolist.dto.task.TaskUpdateRequest;
import com.softserve.itacademy.todolist.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TaskMapper {
    TaskResponse toTaskResponse(Task task);

    List<TaskResponse> toTaskResponseList(List<Task> tasks);

    Task toTaskFromCreate(TaskCreateRequest taskCreateRequest);

    void patchTaskModel(@MappingTarget Task task, TaskUpdateRequest taskUpdateRequest);
}
