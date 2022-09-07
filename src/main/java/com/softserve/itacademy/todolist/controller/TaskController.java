package com.softserve.itacademy.todolist.controller;

import com.softserve.itacademy.todolist.dto.task.TaskCreateRequest;
import com.softserve.itacademy.todolist.dto.task.TaskResponse;
import com.softserve.itacademy.todolist.dto.task.TaskUpdateRequest;
import com.softserve.itacademy.todolist.mappers.TaskMapper;
import com.softserve.itacademy.todolist.model.Task;
import com.softserve.itacademy.todolist.service.TaskService;
import com.softserve.itacademy.todolist.service.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/{u_id}/todos/{t_id}/tasks")
public class TaskController {

    private final ToDoService toDoService;
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @PreAuthorize("""
            (hasAuthority('ADMIN') or authentication.principal == #userId or
            @toDoServiceImpl.isUserCollaborator(#toDoId, authentication.principal)) and
            @relationshipServiceImpl.isRelatedToDoForUser(#toDoId, #userId)""")
    @GetMapping
    List<TaskResponse> getAll(@PathVariable("u_id") long userId, @PathVariable("t_id") long toDoId) {
        return taskMapper.toTaskResponseList(taskService.getByTodoId(toDoId));
    }

    @PreAuthorize("""
            (hasAuthority('ADMIN') or authentication.principal == #userId or
            @toDoServiceImpl.isUserCollaborator(#toDoId, authentication.principal)) and
            @relationshipServiceImpl.isRelatedToDoForUser(#toDoId, #userId)""")
    @GetMapping("/{id}")
    TaskResponse read(@PathVariable long id,
                      @PathVariable("u_id") long userId,
                      @PathVariable("t_id") long toDoId) {
        return taskMapper.toTaskResponse(taskService.readById(id));
    }

    @PreAuthorize("""
            (hasAuthority('ADMIN') or authentication.principal == #userId) and
            @relationshipServiceImpl.isRelatedToDoForUser(#toDoId, #userId)""")
    @PostMapping
    ResponseEntity<?> create(@PathVariable("u_id") long userId, @PathVariable("t_id") long toDoId,
                             @RequestBody @Valid TaskCreateRequest taskCreateRequest) {
        Task task = taskMapper.toTaskFromCreate(taskCreateRequest);
        task.setTodo(toDoService.readById(toDoId));
        Task createdTask = taskService.create(task);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdTask.getId())
                .toUri();
        return ResponseEntity
                .created(location)
                .body(taskMapper.toTaskResponse(createdTask));
    }

    @PreAuthorize("""
            (hasAuthority('ADMIN') or authentication.principal == #userId) and
            @relationshipServiceImpl.isRelatedToDoForUser(#toDoId, #userId)""")
    @PatchMapping("/{id}")
    TaskResponse patch(@PathVariable long id,
                       @PathVariable("u_id") long userId,
                       @PathVariable("t_id") long toDoId,
                       @RequestBody @Valid TaskUpdateRequest taskUpdateRequest) {
        Task task = taskService.readById(id);
        taskMapper.patchTaskModel(task, taskUpdateRequest);
        return taskMapper.toTaskResponse(taskService.update(task));
    }

    @PreAuthorize("""
            (hasAuthority('ADMIN') or authentication.principal == #userId) and
            @relationshipServiceImpl.isRelatedToDoForUser(#toDoId, #userId)""")
    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable long id,
                             @PathVariable("u_id") long userId,
                             @PathVariable("t_id") long toDoId) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
