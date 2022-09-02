package com.softserve.itacademy.todolist.mappers;

import com.softserve.itacademy.todolist.dto.todo.ToDoRequest;
import com.softserve.itacademy.todolist.dto.todo.ToDoResponse;
import com.softserve.itacademy.todolist.model.ToDo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ToDoMapper {
    ToDoResponse toToDoResponse(ToDo toDo);

    List<ToDoResponse> toToDoResponseList(List<ToDo> toDos);

    ToDo toToDo(ToDoRequest toDoRequest);

    void patchToDoModel(@MappingTarget ToDo toDo, ToDoRequest toDoRequest);

}
